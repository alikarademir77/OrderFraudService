package ca.bestbuy.orders.fraud.client.tas;

import ca.bestbuy.orders.fraud.model.internal.FraudResult;
import ca.bestbuy.orders.fraud.model.internal.Order;


public interface FraudServiceTASClient {

    FraudResult doFraudCheck(Order order);

}
