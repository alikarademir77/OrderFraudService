package ca.bestbuy.orders.fraud.model.internal;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Accessors(chain=true)
@Getter
@Setter
public class FraudAssessmentResult {

    private FraudResponseStatusCodes fraudResponseStatus;
    private String orderNumber;
    private long requestVersion;
    private String totalFraudScore;
    private String recommendationCode;
    private String accertifyUser;
    private Date accertifyUserActionTime;
    private String tasRequest;
    private String tasResponse;
    
    public FraudAssessmentResult(){}
    //TODO: SystemError and BANKDOWN codes will be handled via exceptions returned
    public enum FraudResponseStatusCodes{
    	ACCEPTED, PENDING_REVIEW, SOFT_DECLINE, HARD_DECLINE 
    }
}
