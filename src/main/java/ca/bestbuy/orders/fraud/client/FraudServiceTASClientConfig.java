package ca.bestbuy.orders.fraud.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

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

    @Value("${client.tas.connection.truststore-path}")
    public String trustStore;

    @Value("${client.tas.connection.truststore-password}")
    public String trustStorePassword;

    @Value("${client.tas.connection.truststore-type}")
    public String trustStoreType;

    @Value("${client.tas.connection.truststore-provider}")
    public String trustStoreProvider;

    @Value("${client.tas.connection.tls-enabled}")
    public Boolean tlsEnabled;


    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("ca.bestbuy.orders.fraud.model.client.accertify.wsdl");
        return marshaller;
    }


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
