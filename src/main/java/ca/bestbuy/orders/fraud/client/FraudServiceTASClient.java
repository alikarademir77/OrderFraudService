package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderRequest;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse;


public interface FraudServiceTASClient {

    ManageOrderResponse getFraudCheckResponse(ManageOrderRequest request);

}
