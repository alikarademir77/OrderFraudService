package ca.bestbuy.orders.fraud.utility;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import ca.bestbuy.orders.fraud.client.WebClientConfig;

/**
 * A utility class for assisting with creating web clients
 */
public final class WebClientUtility {

    // Private constructor so we cannot instantiate this class
    private WebClientUtility() {
    }


    /**
     * Creates a WebServiceTemplate based on the configuration object and the marshallers provided
     */
    public static WebServiceTemplate createWebServiceTemplate(WebClientConfig config, Marshaller marshaller, Unmarshaller unmarshaller) {

        if (config == null) {
            throw new IllegalArgumentException("WebClientConfig provided must not be null");
        }

        // Get timeout config
        TimeoutConfig timeoutConfig = config.getTimeoutConfig();

        // Get SSL enabled configuration
        Boolean sslEnabled = config.sslEnabled() == null ? true : config.sslEnabled();

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(unmarshaller);

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        configureTimeouts(httpClientBuilder, timeoutConfig);

        // Removing http content length header because header is already set, otherwise we get an exception about content length header already existing
        httpClientBuilder.addInterceptorFirst((HttpRequestInterceptor) (httpRequest, httpContext) -> httpRequest.removeHeaders(HTTP.CONTENT_LEN));

        if (sslEnabled) {
            // Get verify host name configuration
            Boolean verifyHostname = config.verifyHostname() == null ? true : config.verifyHostname();

            configureSSL(httpClientBuilder, config.getKeystoreConfig(), config.getTruststoreConfig(), verifyHostname);

        }

        webServiceTemplate.setMessageSender(new HttpComponentsMessageSender(httpClientBuilder.build()));

        return webServiceTemplate;
    }


    /**
     * Creates a RestTemplate based on the configuration object provided
     */
    public static RestTemplate createRestTemplate(WebClientConfig config) {

        if (config == null) {
            throw new IllegalArgumentException("WebClientConfig provided must not be null");
        }

        // Get timeout config
        TimeoutConfig timeoutConfig = config.getTimeoutConfig();

        // Get SSL enabled configuration
        Boolean sslEnabled = config.sslEnabled() == null ? true : config.sslEnabled();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        configureTimeouts(httpClientBuilder, timeoutConfig);

        if (sslEnabled) {
            // Get verify host name configuration
            Boolean verifyHostname = config.verifyHostname() == null ? true : config.verifyHostname();

            configureSSL(httpClientBuilder, config.getKeystoreConfig(), config.getTruststoreConfig(), verifyHostname);

        }

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build()));
    }


    /**
     * Configures timeouts on an HttpClientBuilder as specified in the timeout configuration object provided.
     * <p>
     * NOTE: If your HttpClientBuilder already has a DefaultRequestConfig set, this method will overwrite it
     *
     * @param builder @NotNull HttpClientBuilder to configure timeouts on
     * @param timeoutConfig @Nullable Configuration that contains timeouts
     * @return The input HttpClientBuilder provided with timeouts configured
     */
    public static HttpClientBuilder configureTimeouts(HttpClientBuilder builder, TimeoutConfig timeoutConfig) {

        if (builder == null) {
            throw new IllegalArgumentException("HttpClientBuilder provided must not be null");
        }

        if (timeoutConfig != null) {

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
        }

        return builder;
    }


    /**
     * Configures SSL on an HTTPClientBuilder as specified in the keystore/truststore configuration objects provided
     *
     * @param builder @NotNull HttpClientBuilder to configure SSL on
     * @param keystoreConfig @NotNull Configuration that contains keystore-related configurations
     * @param truststoreConfig @NotNull Configuration that contains truststore-related configurations
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

        if (truststoreConfig == null) {
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
