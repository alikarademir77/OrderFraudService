package com.bbyc.orders.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bbyc.orders.mappers.OrderMapper;
import com.bbyc.orders.model.client.orderdetails.FSOrder;
import com.bbyc.orders.model.internal.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Component
public class OrderDetailsClientImpl implements OrderDetailsClient {

    private String orderDetailsServiceUrl;

    private String getOrderDetailsEndpoint;

    private String keystorePath;

    private String keystoreType;

    private String keyAlias;

    private String keystorePassword;

    private String truststorePath;

    private String truststoreType;

    private String truststorePassword;

    private RestTemplate restTemplate;

    private OrderMapper orderMapper;


    @Autowired
    public OrderDetailsClientImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }


    @Override
    public Order getOrderDetails(String fsOrderId) {

        RestTemplate restTemplate = new RestTemplate();
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

}
