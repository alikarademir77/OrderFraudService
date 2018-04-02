package ca.bestbuy.orders.fraud.client.tas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import ca.bestbuy.orders.fraud.client.WebClientConfig;
import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper;
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper;
import ca.bestbuy.orders.fraud.utility.KeystoreConfig;
import ca.bestbuy.orders.fraud.utility.TimeoutConfig;
import ca.bestbuy.orders.fraud.utility.TruststoreConfig;
import ca.bestbuy.orders.fraud.utility.WebClientUtility;


@Configuration
public class FraudServiceTASClientConfig implements WebClientConfig {

    private String url;

    @Value("${client.tas.connection.url}")
    protected void setUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("client.tas.connection.url cannot be null");
        }
        this.url = url;
    }


    private String fraudCheckSOAPActionCallback;

    @Value("${client.tas.connection.fraudcheck-soap-action-callback}")
    protected void setFraudCheckSOAPActionCallback(String fraudCheckSOAPActionCallback) {
        if (fraudCheckSOAPActionCallback == null) {
            throw new IllegalArgumentException("client.tas.connection.fraudcheck-soap-action-callback cannot be null");
        }
        this.fraudCheckSOAPActionCallback = fraudCheckSOAPActionCallback;
    }


    private Integer connectionTimeout;

    @Value("${client.tas.connection.timeout.connection}")
    protected void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }


    private Integer requestTimeout;

    @Value("${client.tas.connection.timeout.request}")
    protected void setRequestTimeout(Integer requestTimeout) {
        this.requestTimeout = requestTimeout;
    }


    private Boolean verifyHostName;

    @Value("${client.tas.connection.ssl.verify-hostname:true}")
    protected void setVerifyHostName(Boolean verifyHostName) {
        if (verifyHostName == null) {
            this.verifyHostName = true;
        } else {
            this.verifyHostName = verifyHostName;
        }
    }


    private String keyAlias;

    @Value("${client.tas.connection.ssl.key-alias}")
    protected void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }


    private String keyPassword;

    @Value("${client.tas.connection.ssl.keystore-password}")
    protected void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }


    private Resource keystore;

    @Value("${client.tas.connection.ssl.keystore}")
    protected void setKeystore(Resource keystore) {
        this.keystore = keystore;
    }


    private String keystorePassword;

    @Value("${client.tas.connection.ssl.keystore-password}")
    protected void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }


    private Resource truststore;

    @Value("${client.tas.connection.ssl.truststore}")
    protected void setTruststore(Resource truststore) {
        this.truststore = truststore;
    }


    private String truststorePassword;

    @Value("${client.tas.connection.ssl.truststore-password}")
    protected void setTruststorePassword(String truststorePassword) {
        this.truststorePassword = truststorePassword;
    }


    private Boolean sslEnabled;

    @Value("${client.tas.connection.ssl.enabled:true}")
    protected void setSslEnabled(Boolean sslEnabled) {
        if (sslEnabled == null) {
            throw new IllegalArgumentException("client.tas.connection.ssl.enabled cannot be null");
        }
        this.sslEnabled = sslEnabled;
    }


    @Bean
    public FraudServiceTASClient fraudServiceTASClient(TASRequestXMLMapper tasRequestXMLMapper, TASResponseXMLMapper tasResponseXMLMapper, WebServiceTemplate webServiceTemplate) {
        FraudServiceTASClientImpl fraudServiceTASClient = new FraudServiceTASClientImpl(tasRequestXMLMapper, tasResponseXMLMapper, webServiceTemplate);
        fraudServiceTASClient.setTasBaseUrl(url);
        fraudServiceTASClient.setFraudcheckSOAPActionCallback(fraudCheckSOAPActionCallback);
        return fraudServiceTASClient;
    }


    @Bean
    protected WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate webServiceTemplate = WebClientUtility.createWebServiceTemplate(this, marshaller, marshaller);
        return webServiceTemplate;
    }


    @Bean
    protected Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ca.bestbuy.orders.fraud.model.client.generated.tas.wsdl");
        return marshaller;
    }


    private void validateSSLConfigurations() {

        if(keystore == null) {
            throw new IllegalStateException("Please ensure that the configuration value for 'client.tas.connection.ssl.keystore' is set. If reading from file system, use a prefix of 'file:'. If reading from the classpath, use a prefix of 'classpath:'. Only formats of JKS (.jks) and PKCS#12 (.pfx, .p12) are supported.");
        }

        // UrlResource if using a prefix of 'file:'
        // ClassPathResource if using a prefix of 'classpath:'
        if (!(keystore instanceof UrlResource) && !(keystore instanceof ClassPathResource)) {
            throw new IllegalStateException("Please ensure that the configuration value for 'client.tas.connection.ssl.keystore' is set. If reading from file system, use a prefix of 'file:'. If reading from the classpath, use a prefix of 'classpath:'. Only formats of JKS (.jks) and PKCS#12 (.pfx, .p12) are supported.");
        }

        if(keystorePassword == null) {
            throw new IllegalStateException("Please ensure that the configuration value for 'client.tas.connection.ssl.keystore-password' is set.");
        }

        if(truststore == null) {
            throw new IllegalStateException("Please ensure that the configuration value for 'client.tas.connection.ssl.truststore' is set. If reading from file system, use a prefix of 'file:'. If reading from the classpath, use a prefix of 'classpath:'. Only formats of JKS (.jks) and PKCS#12 (.pfx, .p12) are supported.");
        }

        // UrlResource if using a prefix of 'file:'
        // ClassPathResource if using a prefix of 'classpath:'
        if (!(truststore instanceof UrlResource) && !(truststore instanceof ClassPathResource)) {
            throw new IllegalStateException("Please ensure that the configuration value for 'client.tas.connection.ssl.truststore' is set. If reading from file system, use a prefix of 'file:'. If reading from the classpath, use a prefix of 'classpath:'. Only formats of JKS (.jks) and PKCS#12 (.pfx, .p12) are supported.");
        }

        if(truststorePassword == null) {
            throw new IllegalStateException("Please ensure that the configuration value for 'client.tas.connection.ssl.truststore-password' is set.");
        }

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
        return new TimeoutConfig(connectionTimeout, requestTimeout);
    }

    @Override
    public Boolean verifyHostname() {
        return verifyHostName;
    }

    @Override
    public Boolean sslEnabled() {
        if(sslEnabled) {
            validateSSLConfigurations();
        }

        return sslEnabled;
    }
}
