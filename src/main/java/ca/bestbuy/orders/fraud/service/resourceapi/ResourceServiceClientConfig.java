package ca.bestbuy.orders.fraud.service.resourceapi;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceServiceClientConfig {

    @Value("${client.resource-service.connection.url}")
    @Getter private String serviceUrl;

    @Value("${client.resource-service.connection.endpoint}")
    @Getter private String endpoint;

    @Value("${client.resource-service.connection.keystore-path}")
    @Getter private String keystorePath;

    @Value("${client.resource-service.connection.keystore-password}")
    @Getter private String keystorePassword;

    @Value("${client.resource-service.connection.key-password}")
    @Getter private String keyPassword;

    @Value("${client.resource-service.connection.truststore-path}")
    @Getter private String truststorePath;

    @Value("${client.resource-service.connection.truststore-password}")
    @Getter private String truststorePassword;

    @Value("${client.resource-service.connection.tls-enabled}")
    @Getter private Boolean tlsEnabled;

}
