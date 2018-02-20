package com.bbyc.orders.client;

import com.bbyc.orders.model.internal.Order;

public interface OrderDetailsClient {

    Order getOrderDetails(String fsOrderId);

}
