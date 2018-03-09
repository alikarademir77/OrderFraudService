package ca.bestbuy.orders.fraud.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FraudServiceTASClientConfig {


    @Value("${client.tas.connection.key-alias}")
    public String keyAlias;

    @Value("${client.tas.connection.keystore-password}")
    public String keyPassword;

    @Value("${client.tas.connection.keystore-path}")
    public String keyStore;

    @Value("${client.tas.connection.keystore-password}")
    public String keyStorePassword;

    @Value("${client.tas.connection.keystore-type}")
    public String keyStoreType;

    @Value("${client.tas.connection.keystore-provider}")
    public String keyStoreProvider;

    @Value("${client.tas.connection.trustore-path}")
    public String trustStore;

    @Value("${client.tas.connection.trustore-password}")
    public String trustStorePassword;

    @Value("${client.tas.connection.trustore-type}")
    public String trustStoreType;

    @Value("${client.tas.connection.trustore-provider}")
    public String trustStoreProvider;

    @Value("${client.tas.connection.tls-enabled}")
    public Boolean tlsEnabled;

    public String getKeyAlias() {
        return keyAlias;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public String getKeyStore() {
        return keyStore;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public String getKeyStoreType() {
        return keyStoreType;
    }

    public String getKeyStoreProvider() {
        return keyStoreProvider;
    }

    public String getTrustStore() {
        return trustStore;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public String getTrustStoreType() {
        return trustStoreType;
    }

    public String getTrustStoreProvider() {
        return trustStoreProvider;
    }

    public Boolean getTlsEnabled() {
        return tlsEnabled;
    }
}
