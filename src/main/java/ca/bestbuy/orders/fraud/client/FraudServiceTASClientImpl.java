package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper;
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderRequest;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ObjectFactory;
import ca.bestbuy.orders.fraud.model.internal.FraudResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Component
public class FraudServiceTASClientImpl extends WebServiceGatewaySupport implements FraudServiceTASClient {


    private FraudServiceTASClientConfig config;
    private TASResponseXMLMapper tasResponseXMLMapper;

    private final static String FILE_RESOURCE_PREFIX = "file://";
    private final static String CLASSPATH_RESOURCE_PREFIX = "classpath:";

    @Autowired
    public FraudServiceTASClientImpl(TASResponseXMLMapper tasResponseXMLMapper, FraudServiceTASClientConfig config){
        this.tasResponseXMLMapper = tasResponseXMLMapper;
        this.config = config;
    }

    @Override
    public FraudResult getFraudCheckResponse(ManageOrderRequest request) {


        //todo: take in internal request object as parameter, and map that into jaxb manageorderrequest
        boolean tlsEnabled = config.getTlsEnabled();

        String tasURL = "http://localhost:8999/";
        String fraudCheckEndpoint = null;
        ObjectFactory  objectFactory = new ObjectFactory();

        JAXBElement<ManageOrderRequest> jaxbRequest = objectFactory.createManageOrderRequest(request);

        getWebServiceTemplate().setMarshaller(config.marshaller());
        getWebServiceTemplate().setUnmarshaller(config.marshaller());

        if(tlsEnabled) {
            getWebServiceTemplate().setMessageSender(createHttpsUrlConnectionMessageSender());
        }

        JAXBElement<ManageOrderResponse> jaxbResponse = (JAXBElement<ManageOrderResponse>) getWebServiceTemplate().marshalSendAndReceive(
                tasURL,
                jaxbRequest);


        ManageOrderResponse response = jaxbResponse.getValue();
        FraudResult fraudResult = null;
        fraudResult = tasResponseXMLMapper.mapManageOrderResult(response);
        return fraudResult;
    }



    private HttpsUrlConnectionMessageSender createHttpsUrlConnectionMessageSender(){

        String keystorePath = config.getKeyStore();
        String keystorePassword = config.getKeyStorePassword();
        String keyAlias = config.getKeyAlias();
        String keyPassword = config.getKeyPassword();
        String keystoreType = config.getKeyStoreType();
        String truststorePath = config.getTrustStore();
        String truststorePassword = config.getTrustStorePassword();
        String truststoreType = config.getTrustStoreType();
        HttpsUrlConnectionMessageSender messageSender = null;

        try {
            KeyStore keystore = KeyStore.getInstance(keystoreType);
            keystore.load(new FileInputStream(makeResource(keystorePath).getFile()), keystorePassword.toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keystore, keystorePassword.toCharArray());

            KeyStore truststore = KeyStore.getInstance(truststoreType);
            truststore.load(new FileInputStream(makeResource(truststorePath).getFile()), truststorePassword.toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(truststore);

            messageSender = new HttpsUrlConnectionMessageSender();

            messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
            messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());

            //todo: might have to change hostname for this
            messageSender.setHostnameVerifier((hostname, sslSession) -> {
                if (hostname.equals("localhost")) {
                    return true;
                }
                return false;
            });

        }catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }

        return messageSender;
    }

    protected static Resource makeResource(final String path) {
        if (path.startsWith(FILE_RESOURCE_PREFIX)) {
            return new FileSystemResource(new File(path.substring(FILE_RESOURCE_PREFIX.length())));
        } else if (path.startsWith(CLASSPATH_RESOURCE_PREFIX)) {
            return new ClassPathResource(path.substring(CLASSPATH_RESOURCE_PREFIX.length()));
        }

        // We assume any path without a known prefix is a file
        return new FileSystemResource(new File(path));
    }


}
