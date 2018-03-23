package ca.bestbuy.orders.fraud.utilities;

import ca.bestbuy.orders.fraud.client.OrderDetailsClientConfig;
import ca.bestbuy.orders.fraud.service.resourceapi.ResourceServiceClientConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Created by kundsing on 2018-03-23.
 */
@Component
public class HttpsClientBuilder {

    @Autowired
    private ResourceServiceClientConfig config;
    private final static String FILE_RESOURCE_PREFIX = "file://";
    private final static String CLASSPATH_RESOURCE_PREFIX = "classpath:";


    public RestTemplate getRestTemplate(){

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(getHttpClient()));

    }
    protected HttpClient getHttpClient() {

        boolean tlsEnabled = config.getTlsEnabled();
        String keystorePath = config.getKeystorePath();
        String keystorePassword = config.getKeystorePassword();
        String keyAlias = config.getKeyAlias();
        String keyPassword = config.getKeyPassword();
        String keystoreType = config.getKeystoreType();
        String truststorePath = config.getTruststorePath();
        String truststorePassword = config.getTruststorePassword();
        String truststoreType = config.getTruststoreType();


        if(tlsEnabled) {

            try {

                KeyStore keyStore = KeyStore.getInstance(keystoreType);
                KeyStore trustStore = KeyStore.getInstance(truststoreType);

                keyStore.load(new FileInputStream(makeResource(keystorePath).getFile()), keystorePassword.toCharArray());

                trustStore.load(new FileInputStream(makeResource(truststorePath).getFile()), truststorePassword.toCharArray());

                SSLContext sslContext = SSLContexts.custom()
                        .loadKeyMaterial(keyStore, keyPassword.toCharArray(), (aliases, socket) -> keyAlias)
                        .loadTrustMaterial(trustStore, ((x509Certificates, s) -> false)).build();

                SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, new String[] {"TLSv1.2"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

                return HttpClients.custom().setSSLSocketFactory(socketFactory).build();

            } catch (IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
                throw new IllegalStateException("Could not load key store and/or trust store for Order Details Service", e);
            }

        } else {
            return HttpClients.custom().build();
        }

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
