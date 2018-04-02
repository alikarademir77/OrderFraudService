package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentResult;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentRequest;


public interface FraudServiceTASClient {

    FraudAssessmentResult doFraudCheck(FraudAssessmentRequest fraudAssessmentRequest);

}
