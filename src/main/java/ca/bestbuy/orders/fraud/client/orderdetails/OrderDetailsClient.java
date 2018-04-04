package ca.bestbuy.orders.fraud.client.orderdetails;

import ca.bestbuy.orders.fraud.model.internal.Order;

public interface OrderDetailsClient {

    Order getOrderDetails(String orderNumber);

}
