package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.utility.KeystoreConfig;
import ca.bestbuy.orders.fraud.utility.TimeoutConfig;
import ca.bestbuy.orders.fraud.utility.TruststoreConfig;
import ca.bestbuy.orders.fraud.utility.WebClientUtility;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter @Setter
public class ResourceApiClientConfig implements WebClientConfig {

    @Value("${client.resource-api.connection.url}")
    private String serviceUrl;

    @Value("${client.resource-api.connection.endpoint}")
    private String endpoint;

    @Value("${client.resource-api.connection.keystore}")
    private Resource keystore;

    @Value("${client.resource-api.connection.keystore-password}")
    private String keystorePassword;

    @Value("${client.resource-api.connection.truststore}")
    private Resource truststore;

    @Value("${client.resource-api.connection.truststore-password}")
    private String truststorePassword;

    @Value("${client.resource-api.connection.tls-enabled}")
    private Boolean tlsEnabled;

    @Value("${client.resource-api.connection.verify-host:true}")
    private Boolean verifyHostName;

    @Value("${client.resource-api.connection.timeout.connection}")
    private Integer connectionTimeout;

    @Value("${client.resource-api.connection.timeout.request}")
    private Integer requestTimeout;

    @Override
    public KeystoreConfig getKeystoreConfig() {

        return new KeystoreConfig(getKeystore(), getKeystorePassword());
    }

    @Override
    public TruststoreConfig getTruststoreConfig() {

        return new TruststoreConfig(getTruststore(), getTruststorePassword());
    }

    @Override
    public TimeoutConfig getTimeoutConfig() {
        return new TimeoutConfig(getRequestTimeout(), getConnectionTimeout());
    }

    @Override
    public Boolean verifyHostname() {
        return getVerifyHostName();
    }

    @Override
    public Boolean sslEnabled() {
        return getTlsEnabled();
    }

    @Bean
    protected RestTemplate restTemplate() {
        return WebClientUtility.createRestTemplate(this);
    }

}
