package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.model.internal.FraudAssesmentResult;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentRequest;


public interface FraudServiceTASClient {

    FraudAssesmentResult doFraudCheck(FraudAssessmentRequest fraudAssessmentRequest);

}
