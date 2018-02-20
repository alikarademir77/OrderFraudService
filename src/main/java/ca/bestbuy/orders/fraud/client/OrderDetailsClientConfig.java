package com.bbyc.orders.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderDetailsClientConfig {

    @Value("${client.order-details.connection.url}")
    private String orderDetailsServiceUrl;

    @Value("${client.order-details.connection.getOrderDetailsEndpoint}")
    private String getOrderDetailsEndpoint;

    @Value("${client.order-details.connection.keystore-path}")
    private String keystorePath;

    @Value("${client.order-details.connection.keystore-type}")
    private String keystoreType;

    @Value("${client.order-details.connection.key-alias}")
    private String keyAlias;

    @Value("${client.order-details.connection.keystore-password}")
    private String keystorePassword;

    @Value("${client.order-details.connection.key-password}")
    private String keyPassword;

    @Value("${client.order-details.connection.truststore-path}")
    private String truststorePath;

    @Value("${client.order-details.connection.truststore-type}")
    private String truststoreType;

    @Value("${client.order-details.connection.truststore-password}")
    private String truststorePassword;

    @Value("${client.order-details.connection.tls-enabled}")
    private Boolean tlsEnabled;


    public String getOrderDetailsServiceUrl() {
        return orderDetailsServiceUrl;
    }

    public String getGetOrderDetailsEndpoint() {
        return getOrderDetailsEndpoint;
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
