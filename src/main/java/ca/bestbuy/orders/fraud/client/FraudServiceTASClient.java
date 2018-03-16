package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.model.internal.FraudResult;
import ca.bestbuy.orders.fraud.model.internal.Order;


public interface FraudServiceTASClient {

    FraudResult doFraudCheck(Order order);

}
