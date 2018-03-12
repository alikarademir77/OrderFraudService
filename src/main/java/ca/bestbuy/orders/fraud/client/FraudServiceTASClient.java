package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderRequest;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse;
import ca.bestbuy.orders.fraud.model.internal.FraudResult;


public interface FraudServiceTASClient {

    FraudResult getFraudCheckResponse(ManageOrderRequest request);

}
