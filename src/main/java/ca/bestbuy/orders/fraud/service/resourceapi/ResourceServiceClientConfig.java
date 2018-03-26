package ca.bestbuy.orders.fraud.service.resourceapi;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@Getter @Setter
public class ResourceServiceClientConfig {

    @Value("${client.resource-service.connection.url}")
    private String serviceUrl;

    @Value("${client.resource-service.connection.endpoint}")
    private String endpoint;

    @Value("${client.resource-service.connection.keystore-path}")
    private Resource keystorePath;

    @Value("${client.resource-service.connection.keystore-password}")
    private String keystorePassword;

    @Value("${client.resource-service.connection.key-password}")
    private String keyPassword;

    @Value("${client.resource-service.connection.truststore-path}")
    private Resource truststorePath;

    @Value("${client.resource-service.connection.truststore-password}")
    private String truststorePassword;

    @Value("${client.resource-service.connection.tls-enabled}")
    private Boolean tlsEnabled;

}
