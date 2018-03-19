package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper;
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderActionCode;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderRequest;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ObjectFactory;
import ca.bestbuy.orders.fraud.model.internal.FraudResult;
import ca.bestbuy.orders.fraud.model.internal.Order;
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
    private TASRequestXMLMapper tasRequestXMLMapper;
    private TASResponseXMLMapper tasResponseXMLMapper;

    private final static String FILE_RESOURCE_PREFIX = "file://";
    private final static String CLASSPATH_RESOURCE_PREFIX = "classpath:";

    @Autowired
    public FraudServiceTASClientImpl(TASRequestXMLMapper tasRequestXMLMapper, TASResponseXMLMapper tasResponseXMLMapper, FraudServiceTASClientConfig config){
        this.tasRequestXMLMapper = tasRequestXMLMapper;
        this.tasResponseXMLMapper = tasResponseXMLMapper;
        this.config = config;
    }

    @Override
    public FraudResult doFraudCheck(Order order) {


        //Map the request
        ManageOrderRequest request = new ManageOrderRequest();
        request.setIxTranType(ManageOrderActionCode.FRAUDCHECK);
        request.setTransactionData(tasRequestXMLMapper.mapTransactionData(order));

        boolean tlsEnabled = config.getTlsEnabled();

        String tasURL = config.getUrl();
        String fraudCheckEndpoint = config.getFraudCheckEndpoint();
        ObjectFactory  objectFactory = new ObjectFactory();

        JAXBElement<ManageOrderRequest> jaxbRequest = objectFactory.createManageOrderRequest(request);

        getWebServiceTemplate().setMarshaller(config.marshaller());
        getWebServiceTemplate().setUnmarshaller(config.marshaller());


        //Set up 2-way SSL configurations if enabled
        if(tlsEnabled) {
            getWebServiceTemplate().setMessageSender(createHttpsUrlConnectionMessageSender());
        }

        //Send request to TAS and receive response

        JAXBElement<ManageOrderResponse> jaxbResponse = (JAXBElement<ManageOrderResponse>) getWebServiceTemplate().marshalSendAndReceive(
                tasURL+fraudCheckEndpoint,
                jaxbRequest);

        //map response to FraudResult object
        ManageOrderResponse response = jaxbResponse.getValue();
        FraudResult fraudResult = tasResponseXMLMapper.mapManageOrderResult(response);
        return fraudResult;
    }


    //Set up HTTPS configurations for 2-way SSL
    private HttpsUrlConnectionMessageSender createHttpsUrlConnectionMessageSender(){

        String keystorePath = config.getKeyStore();
        String keystorePassword = config.getKeyStorePassword();
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

            messageSender.setHostnameVerifier((hostname, sslSession) -> {
                if (hostname.equals(config.getHostname())) {
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
