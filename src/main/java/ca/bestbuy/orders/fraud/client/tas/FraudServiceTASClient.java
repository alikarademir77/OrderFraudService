package ca.bestbuy.orders.fraud.client.tas;

import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentResult;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentRequest;


public interface FraudServiceTASClient {

    FraudAssessmentResult doFraudCheck(FraudAssessmentRequest fraudAssessmentRequest);

}
