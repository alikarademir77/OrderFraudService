package ca.bestbuy.orders.fraud.model.internal;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    private String tasRequest; //todo: store the xml request here
    private String tasResponse; //todo: store the xml response here

    public FraudAssessmentResult(){}
    //TODO: SystemError and BANKDOWN codes will be handled via exceptions returned
    public enum FraudResponseStatusCodes{
    	ACCEPTED, PENDING_REVIEW, SOFT_DECLINE, HARD_DECLINE 
    }
}
