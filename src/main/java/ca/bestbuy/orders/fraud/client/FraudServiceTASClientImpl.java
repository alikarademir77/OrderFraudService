package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.mappers.XMLMapper;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderRequest;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@Component
public class FraudServiceTASClientImpl implements FraudServiceTASClient {


    private XMLMapper xmlMapper;
    private RestTemplate restTemplate;
    private FraudServiceTASClientConfig config;


    @Autowired
    public FraudServiceTASClientImpl() {

        this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(createHttpClient()));

    }

    @Override
    public ManageOrderResponse getFraudCheckResponse(ManageOrderRequest request) {

        String tasURL = null;
        String fraudCheckEndpoint = null;

        ManageOrderResponse response = restTemplate.getForObject(tasURL+fraudCheckEndpoint , ManageOrderResponse.class);

        return null;
    }


    private HttpClient createHttpClient() {

        boolean tlsEnabled = config.getTlsEnabled();
        String keystorePath = config.getKeyStore();
        String keystorePassword = config.getKeyStorePassword();
        String keyAlias = config.getKeyAlias();
        String keyPassword = config.getKeyPassword();
        String keystoreType = config.getKeyStoreType();
        String truststorePath = config.getTrustStore();
        String truststorePassword = config.getTrustStorePassword();
        String truststoreType = config.getTrustStoreType();

        if (tlsEnabled) {
            try {
                final KeyStore trustStore = KeyStore.getInstance(keystoreType);
                final KeyStore keyStore = KeyStore.getInstance(truststoreType);
                final SSLContext sslContext;

                sslContext = SSLContexts.custom()
                        .loadKeyMaterial(keyStore, keystorePassword.toCharArray(), (aliases, socket) -> keyAlias)
                        .loadTrustMaterial(trustStore, ((x509Certificates, s) -> false))
                        .build();


                final SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                        sslContext,
                        new String[]{"TLSv1.2"},
                        null,
                        SSLConnectionSocketFactory.getDefaultHostnameVerifier()
                );

                return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();

            } catch (KeyManagementException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException e) {
                throw new IllegalStateException("Could not load key store and/or trust store for TAS", e);
            }

        } else {
            return HttpClients.createDefault();
        }
    }


}
