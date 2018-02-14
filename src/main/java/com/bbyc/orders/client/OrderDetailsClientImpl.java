package com.bbyc.orders.client;

import org.springframework.stereotype.Component;

import com.bbyc.orders.model.internal.Order;

@Component
public class OrderDetailsClientImpl implements OrderDetailsClient {

    @Override
    public Order getOrderDetails(String fsOrderId) {
        return null;
    }

}
