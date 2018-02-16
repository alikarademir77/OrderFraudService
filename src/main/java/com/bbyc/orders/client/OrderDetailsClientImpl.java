package com.bbyc.orders.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bbyc.orders.mappers.OrderMapper;
import com.bbyc.orders.model.client.orderdetails.FSOrder;
import com.bbyc.orders.model.internal.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;


@Component
public class OrderDetailsClientImpl implements OrderDetailsClient {

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

    private OrderMapper orderMapper;
    private RestTemplate restTemplate;

    private final static String FILE_RESOURCE_PREFIX = "file://";
    private final static String CLASSPATH_RESOURCE_PREFIX = "classpath:";



    @Autowired
    public OrderDetailsClientImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
        this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(getHttpClient()));
    }


    @Override
    public Order getOrderDetails(String fsOrderId) {

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/orderdetails/v1/fsorders/" + fsOrderId, String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        FSOrder orderToMap = null;
        Order mappedOrder = null;
        try {
            orderToMap = mapper.readValue(response.getBody(), FSOrder.class);
            mappedOrder = orderMapper.mapOrder(orderToMap);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mappedOrder;
    }


    protected HttpClient getHttpClient() {

        if(tlsEnabled) {

            try {

                KeyStore keyStore = KeyStore.getInstance(keystoreType);
                KeyStore trustStore = KeyStore.getInstance(truststoreType);

                keyStore.load(new FileInputStream(makeResource(keystorePath).getFile()), keystorePassword.toCharArray());

                trustStore.load(new FileInputStream(makeResource(truststorePath).getFile()), truststorePassword.toCharArray());

                SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, keyPassword.toCharArray(), (aliases, socket) -> keyAlias)
                    .loadTrustMaterial(trustStore, ((x509Certificates, s) -> false)).build();

                SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, new String[] {"TLSv1.2"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

                return HttpClients.custom().setSSLSocketFactory(socketFactory).build();

            } catch (IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
                throw new IllegalStateException("Could not load key store and/or trust store for Order Details Service", e);
            }

        } else {
            return HttpClients.custom().build();
        }

    }


    protected static Resource makeResource(final String path) {
        if (path.startsWith(FILE_RESOURCE_PREFIX)) {
            return new FileSystemResource(new File(path.substring(FILE_RESOURCE_PREFIX.length())));
        } else if (path.startsWith(CLASSPATH_RESOURCE_PREFIX)) {
            return new ClassPathResource(path.substring(CLASSPATH_RESOURCE_PREFIX.length()));
        }

        // We assume any path without a known prefix is a file
        return new FileSystemResource(new File(path));
    }


}
