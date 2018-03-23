package ca.bestbuy.orders.fraud.service.resourceapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceServiceClientConfig {

    @Value("${client.resource-service.connection.url}")
    private String serviceUrl;

    @Value("${client.resource-service.connection.endpoint}")
    private String endpoint;

    @Value("${client.resource-service.connection.keystore-path}")
    private String keystorePath;

    @Value("${client.resource-service.connection.keystore-type}")
    private String keystoreType;

    @Value("${client.resource-service.connection.key-alias}")
    private String keyAlias;

    @Value("${client.resource-service.connection.keystore-password}")
    private String keystorePassword;

    @Value("${client.resource-service.connection.key-password}")
    private String keyPassword;

    @Value("${client.resource-service.connection.truststore-path}")
    private String truststorePath;

    @Value("${client.resource-service.connection.truststore-type}")
    private String truststoreType;

    @Value("${client.resource-service.connection.truststore-password}")
    private String truststorePassword;

    @Value("${client.resource-service.connection.tls-enabled}")
    private Boolean tlsEnabled;


    public String getServiceUrl() {
        return serviceUrl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public String getKeystoreType() {
        return keystoreType;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public String getTruststorePath() {
        return truststorePath;
    }

    public String getTruststoreType() {
        return truststoreType;
    }

    public String getTruststorePassword() {
        return truststorePassword;
    }

    public Boolean getTlsEnabled() {
        return tlsEnabled;
    }
}
