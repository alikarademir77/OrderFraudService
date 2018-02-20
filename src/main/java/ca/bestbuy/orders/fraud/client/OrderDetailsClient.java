package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.model.internal.Order;

public interface OrderDetailsClient {

    Order getOrderDetails(String fsOrderId);

}
