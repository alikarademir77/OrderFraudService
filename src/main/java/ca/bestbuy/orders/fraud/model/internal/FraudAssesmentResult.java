package ca.bestbuy.orders.fraud.model.internal;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class FraudAssesmentResult {

    private FraudResponseStatusCodes fraudResponseStatus;
    private String orderNumber;
    private long requestVersion;
    private String totalFraudScore;
    private String recommendationCode;
    private String accertifyUser;
    private Date accertifyUserActionTime;
    private String tasRequest;
    private String tasResponse;
    
    public FraudAssesmentResult(){}
    //TODO: SystemError and BANKDOWN codes will be handled via exceptions returned
    public enum FraudResponseStatusCodes{
    	ACCEPTED, PENDING_REVIEW, SOFT_DECLINE, HARD_DECLINE 
    }
}
