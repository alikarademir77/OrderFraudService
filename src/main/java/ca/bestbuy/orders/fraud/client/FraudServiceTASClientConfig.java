package ca.bestbuy.orders.fraud.client;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper;
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper;
import ca.bestbuy.orders.fraud.utility.KeystoreConfig;
import ca.bestbuy.orders.fraud.utility.TimeoutConfig;
import ca.bestbuy.orders.fraud.utility.TruststoreConfig;
import ca.bestbuy.orders.fraud.utility.WebClientUtility;


@Configuration
public class FraudServiceTASClientConfig {

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
    protected WebServiceTemplate webServiceTemplate(HttpComponentsMessageSender httpComponentsMessageSender, Jaxb2Marshaller marshaller) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        webServiceTemplate.setMessageSender(httpComponentsMessageSender);
        return webServiceTemplate;
    }


    @Bean
    protected Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("ca.bestbuy.orders.fraud.model.client.accertify.wsdl");
        return marshaller;
    }


    @Bean
    protected HttpComponentsMessageSender httpComponentsMessageSender() {

        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setHttpClient(createHttpClient());
        return httpComponentsMessageSender;
    }


    protected HttpClient createHttpClient() {

        HttpClientBuilder builder = HttpClientBuilder.create();

        // Set timeouts
        TimeoutConfig timeoutConfig = new TimeoutConfig(connectionTimeout, requestTimeout);
        WebClientUtility.configureTimeouts(builder, timeoutConfig);

        // Removing http content length header because header is already set, otherwise we get an exception about content length header already existing
        builder.addInterceptorFirst((HttpRequestInterceptor) (httpRequest, httpContext) -> httpRequest.removeHeaders(HTTP.CONTENT_LEN));

        if (sslEnabled) {

            // Validate SSL Configurations
            validateSSLConfigurations();

            KeystoreConfig keystoreConfig = new KeystoreConfig(keystore, keystorePassword, keyAlias, keyPassword);
            TruststoreConfig truststoreConfig = new TruststoreConfig(truststore, truststorePassword);
            WebClientUtility.configureSSL(builder, keystoreConfig, truststoreConfig, verifyHostName);
        }

        return builder.build();
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

}
