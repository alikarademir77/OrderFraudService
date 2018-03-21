package ca.bestbuy.orders.fraud.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper;
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper;

@Configuration
public class FraudServiceTASClientConfig {

    protected String url;

    @Value("${client.tas.connection.url}")
    protected void setUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("client.tas.connection.url cannot be null");
        }
        this.url = url;
    }


    protected String fraudCheckOperation;

    @Value("${client.tas.connection.fraudCheckOperation}")
    protected void setFraudCheckOperation(String fraudCheckOperation) {
        if (fraudCheckOperation == null) {
            throw new IllegalArgumentException("client.tas.connection.fraudCheckOperation cannot be null");
        }
        this.fraudCheckOperation = fraudCheckOperation;
    }

    protected String hostname;

    @Value("${client.tas.connection.hostname}")
    protected void setHostname(String hostname) {
        if (hostname == null) {
            throw new IllegalArgumentException("client.tas.connection.hostname cannot be null");
        }
        this.hostname = hostname;
    }

    protected int connectionTimeout;

    @Value("${client.tas.connection.connectionTimeout}")
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }


    protected int readTimeout;

    @Value("${client.tas.connection.readTimeout}")
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }


    protected Boolean verifyHostName;

    @Value("${client.tas.connection.ssl.verify-hostname:true}")
    protected void setVerifyHostName(Boolean verifyHostName) {
        if (verifyHostName == null) {
            this.verifyHostName = true;
        } else {
            this.verifyHostName = verifyHostName;
        }
    }

    protected String keyAlias;

    @Value("${client.tas.connection.ssl.key-alias}")
    protected void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    protected String keyPassword;

    @Value("${client.tas.connection.ssl.keystore-password}")
    protected void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    protected Resource keyStore;

    @Value("${client.tas.connection.ssl.keystore-path}")
    protected void setKeyStore(Resource keyStore) {
        this.keyStore = keyStore;
    }

    protected String keyStorePassword;

    @Value("${client.tas.connection.ssl.keystore-password}")
    protected void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    protected String keyStoreType;

    @Value("${client.tas.connection.ssl.keystore-type}")
    protected void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }

    protected Resource trustStore;

    @Value("${client.tas.connection.ssl.truststore-path}")
    protected void setTrustStore(Resource trustStore) {
        this.trustStore = trustStore;
    }

    protected String trustStorePassword;

    @Value("${client.tas.connection.ssl.truststore-password}")
    protected void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    protected String trustStoreType;

    @Value("${client.tas.connection.ssl.truststore-type}")
    protected void setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

    protected Boolean tlsEnabled;

    @Value("${client.tas.connection.ssl.tls-enabled:true}")
    protected void setTlsEnabled(Boolean tlsEnabled) {
        if (tlsEnabled == null) {
            throw new IllegalArgumentException("client.tas.connection.ssl.tls-enabled cannot be null");
        }
        this.tlsEnabled = tlsEnabled;
    }


    @Bean
    public FraudServiceTASClient fraudServiceTASClient(TASRequestXMLMapper tasRequestXMLMapper, TASResponseXMLMapper tasResponseXMLMapper, WebServiceTemplate webServiceTemplate) {
        FraudServiceTASClient fraudServiceTASClient = new FraudServiceTASClientImpl(tasRequestXMLMapper, tasResponseXMLMapper, webServiceTemplate, fraudCheckOperation);
        return fraudServiceTASClient;
    }


    @Bean
    protected WebServiceTemplate webServiceTemplate(HttpComponentsMessageSender httpComponentsMessageSender, Jaxb2Marshaller marshaller) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setDefaultUri(url);
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
    //Set up HTTPS configurations for 2-way SSL
    protected HttpComponentsMessageSender httpComponentsMessageSender() {

        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setHttpClient(httpClient());
        return httpComponentsMessageSender;
    }


    protected HttpClient httpClient() {

        HttpClientBuilder builder = HttpClientBuilder.create();
        //setting timeouts
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeout).setSocketTimeout(readTimeout).build();
        builder = builder.setDefaultRequestConfig(requestConfig);

        //removing http headers because headers are already set, otherwise we get an exception about content length header already existing
        builder.addInterceptorFirst(new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                httpRequest.removeHeaders(HTTP.CONTENT_LEN);
            }
        });
        if (tlsEnabled) {
            builder = builder.setSSLSocketFactory(sslConnectionSocketFactory());
        }

        return builder.build();

    }

    protected SSLConnectionSocketFactory sslConnectionSocketFactory() {

        if (verifyHostName) {
            return new SSLConnectionSocketFactory(sslContext());
        } else {
            return new SSLConnectionSocketFactory(sslContext(), NoopHostnameVerifier.INSTANCE);
        }

    }

    protected SSLContext sslContext() {

        if (keyStore == null) {
            throw new IllegalArgumentException(
                " The value for 'client.tas.connection.ssl.keystore-path' was found to be null. Please ensure it is set correctly in the application configuration.");
        }

        if (keyStorePassword == null) {
            throw new IllegalArgumentException(
                " The value for 'client.tas.connection.ssl.keystore-password' was found to be null. Please ensure it is set correctly in the application configuration.");
        }

        if (keyStoreType == null || keyStoreType.isEmpty() || !keyStoreType.equals("JKS")) {
            throw new IllegalArgumentException(
                " The value for 'client.tas.connection.ssl.keystore-type' was found to be null, empty, or does not have a value of 'JKS'. Please ensure it is set correctly in "
                    + "the application configuration.");
        }

        if (keyAlias == null || keyAlias.isEmpty()) {
            throw new IllegalArgumentException(
                " The value for 'client.tas.connection.ssl.key-alias' was found to be null or empty. Please ensure it is set correctly in the application configuration.");
        }

        if (keyPassword == null) {
            throw new IllegalArgumentException(
                " The value for 'client.tas.connection.ssl.key-password' was found to be null. Please ensure it is set correctly in the application configuration.");
        }

        if (trustStore == null) {
            throw new IllegalArgumentException(
                " The value for 'client.tas.connection.ssl.truststore-path' was found to be null. Please ensure it is set correctly in the application configuration.");
        }

        if (trustStorePassword == null) {
            throw new IllegalArgumentException(
                " The value for 'client.tas.connection.ssl.truststore-password' was found to be null. Please ensure it is set correctly in the application configuration.");
        }

        try {
            return SSLContextBuilder.create().loadKeyMaterial(keyStore.getFile(), keyStorePassword.toCharArray(), keyPassword.toCharArray(),
                (map, socket) -> keyAlias).loadTrustMaterial(trustStore.getFile(), trustStorePassword.toCharArray()).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException | UnrecoverableKeyException | KeyManagementException e) {
            throw new IllegalStateException("Could not load keystore and/or trust store for TAS Client. Please ensure all relevant application configurations are correctly set.",
                e);
        }
    }


}
