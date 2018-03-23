package ca.bestbuy.orders.fraud.utility;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class HttpClientUtility {

    private Resource keystore;
    private String keystorePassword;
    private String keyAlias;
    private String keyPassword;
    private Resource truststore;
    private String truststorePassword;
    private Integer connectionTimeout;
    private Integer requestTimeout;


    public void setKeystore(Resource keystore) {
        this.keystore = keystore;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public void setTruststore(Resource truststore) {
        this.truststore = truststore;
    }

    public void setTruststorePassword(String truststorePassword) {
        this.truststorePassword = truststorePassword;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setRequestTimeout(Integer requestTimeout) {
        this.requestTimeout = requestTimeout;
    }



    public HttpClient createHttpClient(Boolean sslEnabled, Boolean verifyHostname) {

        HttpClientBuilder builder = HttpClientBuilder.create();

        // Setting timeouts if timeouts are set
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if(connectionTimeout != null) {
            requestConfigBuilder.setConnectTimeout(connectionTimeout);
        }
        if(requestTimeout != null) {
            requestConfigBuilder.setSocketTimeout(requestTimeout);
        }
        builder.setDefaultRequestConfig(requestConfigBuilder.build());

        if(sslEnabled) {
            if(verifyHostname) {
                builder.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext()));
            } else {
                builder.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext(), NoopHostnameVerifier.INSTANCE));
            }
        }

        return builder.build();
    }




    private SSLContext sslContext() {

        if (keystore == null) {
            throw new IllegalStateException("Keystore must not be null");
        }

        // UrlResource if using a prefix of 'file:'
        // ClassPathResource if using a prefix of 'classpath:'
        if(!(keystore instanceof UrlResource) && !(keystore instanceof ClassPathResource)) {
            throw new IllegalStateException("Keystore Resource must be an instance of UrlResource or ClassPathResource");
        }

        if (keystorePassword == null) {
            throw new IllegalStateException("Keystore password must not be null");
        }

        if (keyAlias == null || keyAlias.isEmpty()) {
            throw new IllegalStateException("Key alias must not be null");
        }

        if (keyPassword == null) {
            throw new IllegalStateException("Key password must not be null");
        }

        if (truststore == null) {
            throw new IllegalStateException("Truststore must not ne null");
        }

        // UrlResource if using a prefix of 'file:'
        // ClassPathResource if using a prefix of 'classpath:'
        if(!(truststore instanceof UrlResource) && !(truststore instanceof ClassPathResource)) {
            throw new IllegalStateException("Truststore Resource must be an instance of UrlResource or ClassPathResource");
        }

        if (truststorePassword == null) {
            throw new IllegalStateException("Truststore password must not be null");
        }


        try {

            return SSLContextBuilder.create()
                // Load keystore
                .loadKeyMaterial(keystore.getFile(), keystorePassword.toCharArray(), keyPassword.toCharArray(), (map, socket) -> keyAlias)
                // Load trust store
                .loadTrustMaterial(truststore.getFile(), truststorePassword.toCharArray()).build();

        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException | UnrecoverableKeyException | KeyManagementException e) {
            throw new IllegalStateException(
                "Could not load keystore and/or trust store. Please ensure all relevant configurations are correctly set.", e);
        }
    }






}
