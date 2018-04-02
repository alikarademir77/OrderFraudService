package ca.bestbuy.orders.fraud.client.orderdetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.client.RestTemplate;

import ca.bestbuy.orders.fraud.client.WebClientConfig;
import ca.bestbuy.orders.fraud.mappers.OrderMapper;
import ca.bestbuy.orders.fraud.utility.KeystoreConfig;
import ca.bestbuy.orders.fraud.utility.TimeoutConfig;
import ca.bestbuy.orders.fraud.utility.TruststoreConfig;
import ca.bestbuy.orders.fraud.utility.WebClientUtility;

@Configuration
public class OrderDetailsClientConfig implements WebClientConfig {


    private String url;

    @Value("${client.order-details.connection.url}")
    protected void setUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("client.order-details.connection.url cannot be null");
        }
        this.url = url;
    }


    private String getOrderDetailsEndpoint;

    @Value("${client.order-details.connection.getOrderDetailsEndpoint}")
    protected void setGetOrderDetailsEndpoint(String getOrderDetailsEndpoint) {

        if (getOrderDetailsEndpoint == null) {
            throw new IllegalArgumentException("client.order-details.connection.getOrderDetailsEndpoint");
        }

        this.getOrderDetailsEndpoint = getOrderDetailsEndpoint;
    }


    private Resource keystore;

    @Value("${client.order-details.connection.ssl.keystore}")
    protected void setKeystore(Resource keystore) {
        this.keystore = keystore;
    }


    private String keyAlias;

    @Value("${client.order-details.connection.ssl.key-alias}")
    protected void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }


    private String keystorePassword;

    @Value("${client.order-details.connection.ssl.keystore-password}")
    protected void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }


    private String keyPassword;

    @Value("${client.order-details.connection.ssl.key-password}")
    protected void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }


    @Value("${client.order-details.connection.ssl.truststore}")
    private Resource truststore;

    protected void setTruststore(Resource truststore) {
        this.truststore = truststore;
    }


    @Value("${client.order-details.connection.ssl.truststore-password}")
    private String truststorePassword;

    protected void setTruststorePassword(String truststorePassword) {
        this.truststorePassword = truststorePassword;
    }


    @Value("${client.order-details.connection.ssl.enabled}")
    private Boolean sslEnabled;

    protected void setSslEnabled(Boolean sslEnabled) {
        if (sslEnabled == null) {
            throw new IllegalArgumentException("client.order-details.connection.ssl.enabled cannot be null");
        }
        this.sslEnabled = sslEnabled;
    }


    private Boolean verifyHostName;

    @Value("${client.order-details.connection.ssl.verify-hostname:true}")
    protected void setVerifyHostName(Boolean verifyHostName) {
        if (verifyHostName == null) {
            this.verifyHostName = true;
        } else {
            this.verifyHostName = verifyHostName;
        }
    }


    private Integer connectionTimeout;

    @Value("${client.order-details.connection.timeout.connection}")
    protected void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }


    private Integer requestTimeout;

    @Value("${client.order-details.connection.timeout.request}")
    protected void setRequestTimeout(Integer requestTimeout) {
        this.requestTimeout = requestTimeout;
    }


    @Bean
    public OrderDetailsClient orderDetailsClient(OrderMapper orderMapper, RestTemplate restTemplate) {
        OrderDetailsClientImpl orderDetailsClient = new OrderDetailsClientImpl(orderMapper, restTemplate);
        orderDetailsClient.setOrderDetailsServiceBaseUrl(url);
        orderDetailsClient.setGetOrderDetailsEndpoint(getOrderDetailsEndpoint);
        return orderDetailsClient;
    }


    @Bean
    protected RestTemplate restTemplate() {
        if (sslEnabled) {
            // We do some validation here on SSL configurations
            validateSSLConfigurations();
        }
        return WebClientUtility.createRestTemplate(this);
    }


    @Override
    public KeystoreConfig getKeystoreConfig() {
        return new KeystoreConfig(keystore, keystorePassword, keyAlias, keyPassword);
    }

    @Override
    public TruststoreConfig getTruststoreConfig() {
        return new TruststoreConfig(truststore, truststorePassword);
    }

    @Override
    public TimeoutConfig getTimeoutConfig() {
        return new TimeoutConfig(requestTimeout, connectionTimeout);
    }

    @Override
    public Boolean verifyHostname() {
        return verifyHostName;
    }

    @Override
    public Boolean sslEnabled() {
        return sslEnabled;
    }


    private void validateSSLConfigurations() {

        if (keystore == null) {
            throw new IllegalStateException(
                "Please ensure that the configuration value for 'client.order-details.connection.ssl.keystore' is set. If reading from file system, use a prefix of 'file:'. If "
                    + "reading from the classpath, use a prefix of 'classpath:'. Only formats of JKS (.jks) and PKCS#12 (.pfx, .p12) are supported.");
        }

        // UrlResource if using a prefix of 'file:'
        // ClassPathResource if using a prefix of 'classpath:'
        if (!(keystore instanceof UrlResource) && !(keystore instanceof ClassPathResource)) {
            throw new IllegalStateException(
                "Please ensure that the configuration value for 'client.order-details.connection.ssl.keystore' is set. If reading from file system, use a prefix of 'file:'. If "
                    + "reading from the classpath, use a prefix of 'classpath:'. Only formats of JKS (.jks) and PKCS#12 (.pfx, .p12) are supported.");
        }

        if (keystorePassword == null) {
            throw new IllegalStateException("Please ensure that the configuration value for 'client.order-details.connection.ssl.keystore-password' is set.");
        }

        if (truststore == null) {
            throw new IllegalStateException(
                "Please ensure that the configuration value for 'client.order-details.connection.ssl.truststore' is set. If reading from file system, use a prefix of 'file:'. If"
                    + " reading from the classpath, use a prefix of 'classpath:'. Only formats of JKS (.jks) and PKCS#12 (.pfx, .p12) are supported.");
        }

        // UrlResource if using a prefix of 'file:'
        // ClassPathResource if using a prefix of 'classpath:'
        if (!(truststore instanceof UrlResource) && !(truststore instanceof ClassPathResource)) {
            throw new IllegalStateException(
                "Please ensure that the configuration value for 'client.order-details.connection.ssl.truststore' is set. If reading from file system, use a prefix of 'file:'. If"
                    + " reading from the classpath, use a prefix of 'classpath:'. Only formats of JKS (.jks) and PKCS#12 (.pfx, .p12) are supported.");
        }

        if (truststorePassword == null) {
            throw new IllegalStateException("Please ensure that the configuration value for 'client.order-details.connection.ssl.truststore-password' is set.");
        }

    }

}
