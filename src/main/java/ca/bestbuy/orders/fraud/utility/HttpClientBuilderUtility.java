package ca.bestbuy.orders.fraud.utility;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * A utility class for configuring HttpClientBuilder
 */
public final class HttpClientBuilderUtility {

    // Private constructor so we cannot instantiate this class
    private HttpClientBuilderUtility() {
    }


    /**
     * Configures timeouts on an HttpClientBuilder as specified in the timeout configuration object provided.
     * <p>
     * NOTE: If your HttpClientBuilder already has a DefaultRequestConfig set, this method will overwrite it
     *
     * @param builder HttpClientBuilder to configure timeouts on
     * @param timeoutConfig Configuration that contains timeouts
     * @return The input HttpClientBuilder provided with timeouts configured
     */
    public static HttpClientBuilder configureTimeouts(HttpClientBuilder builder, TimeoutConfig timeoutConfig) {

        if (builder == null) {
            throw new IllegalArgumentException("HttpClientBuilder provided must not be null");
        }

        if (timeoutConfig == null) {
            throw new IllegalArgumentException("TimeoutConfig provided must not be null");
        }

        // Get timeouts from config
        Integer connectionTimeout = timeoutConfig.getConnectionTimeout();
        Integer requestTimeout = timeoutConfig.getRequestTimeout();

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

        // Set connection timeout if set
        if (connectionTimeout != null) {
            requestConfigBuilder.setConnectTimeout(connectionTimeout);
        }

        // Set request timeout if set
        if (requestTimeout != null) {
            requestConfigBuilder.setSocketTimeout(requestTimeout);
        }

        // Add to builder
        builder.setDefaultRequestConfig(requestConfigBuilder.build());

        return builder;
    }


    /**
     * Configures SSL on an HTTPClientBuilder as specified in the keystore/truststore configuration objects provided
     *
     * @param builder HttpClientBuilder to configure SSL on
     * @param keystoreConfig Configuration that contains keystore-related configurations
     * @param truststoreConfig Configuration that contains truststore-related configurations
     * @param verifyHostname Flag to indicate if hostname verification should be enabled
     * @return The input HttpClientBuilder provided with SSL configured
     */
    public static HttpClientBuilder configureSSL(HttpClientBuilder builder, KeystoreConfig keystoreConfig, TruststoreConfig truststoreConfig, boolean verifyHostname) {

        if (builder == null) {
            throw new IllegalArgumentException("HttpClientBuilder provided must not be null");
        }

        validateKeystoreConfig(keystoreConfig);
        validateTruststoreConfig(truststoreConfig);

        try {

            SSLContextBuilder sslContextBuilder = SSLContextBuilder.create();

            Resource keystore = keystoreConfig.getKeystore();
            String keystorePassword = keystoreConfig.getKeystorePassword();
            // Assume that if a key password is not set, it will be the same as the keystore password
            String keyPassword = keystoreConfig.getKeyPassword() == null ? keystorePassword : keystoreConfig.getKeyPassword();
            String keyAlias = keystoreConfig.getKeyAlias();
            Resource truststore = truststoreConfig.getTruststore();
            String truststorePassword = truststoreConfig.getTruststorePassword();

            // Load key material
            if (keyAlias == null) {
                sslContextBuilder.loadKeyMaterial(keystore.getFile(), keystorePassword.toCharArray(), keyPassword.toCharArray());
            } else {
                sslContextBuilder.loadKeyMaterial(keystore.getFile(), keystorePassword.toCharArray(), keyPassword.toCharArray(), (map, socket) -> keyAlias);
            }

            // Load trust material
            sslContextBuilder.loadTrustMaterial(truststore.getFile(), truststorePassword.toCharArray());

            // Build SSLContext
            SSLContext sslContext = sslContextBuilder.build();

            // Set SSLSocketFactory in HttpClientBuilder and pass in SSLContext
            if (verifyHostname) {
                builder.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext));
            } else {
                builder.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE));
            }

            return builder;

        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException | UnrecoverableKeyException | KeyManagementException e) {
            throw new IllegalStateException("Could not load keystore and/or trust store. " + "Please ensure all relevant configurations are correctly set. "
                + "Additionally, please ensure that the physical keystore/truststore file resources you provide have their file extensions set (either .jks or .pfx).", e);
        }
    }


    /**
     * Validates a KeystoreConfig object
     *
     * @param config KeystoreConfig to validate
     */
    private static void validateKeystoreConfig(KeystoreConfig config) {

        if (config == null) {
            throw new IllegalArgumentException("KeystoreConfig must not be null");
        }

        if (config.getKeystore() == null) {
            throw new IllegalStateException("KeystoreConfig.keystore must not be null");
        }

        // UrlResource if using a prefix of 'file:'
        // ClassPathResource if using a prefix of 'classpath:'
        if (!(config.getKeystore() instanceof UrlResource) && !(config.getKeystore() instanceof ClassPathResource)) {
            throw new IllegalStateException("KeystoreConfig.keystore must be an instance of UrlResource or ClassPathResource");
        }

        if (config.getKeystorePassword() == null) {
            throw new IllegalStateException("KeystoreConfig.keystorePassword must not be null");
        }

    }


    /**
     * Validates a TruststoreConfig object
     *
     * @param truststoreConfig TruststoreConfig to validate
     */
    private static void validateTruststoreConfig(TruststoreConfig truststoreConfig) {

        if(truststoreConfig == null) {
            throw new IllegalArgumentException("TruststoreConfig must not be null");
        }

        if (truststoreConfig.getTruststore() == null) {
            throw new IllegalStateException("TruststoreConfig.truststore must not ne null");
        }

        // UrlResource if using a prefix of 'file:'
        // ClassPathResource if using a prefix of 'classpath:'
        if (!(truststoreConfig.getTruststore() instanceof UrlResource) && !(truststoreConfig.getTruststore() instanceof ClassPathResource)) {
            throw new IllegalStateException("TruststoreConfig.truststore must be an instance of UrlResource or ClassPathResource");
        }

        if (truststoreConfig.getTruststorePassword() == null) {
            throw new IllegalStateException("TruststoreConfig.truststorePassword must not be null");
        }

    }


}
