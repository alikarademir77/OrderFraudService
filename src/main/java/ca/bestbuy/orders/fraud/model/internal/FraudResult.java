package ca.bestbuy.orders.fraud.model.internal;

import java.util.List;

public class FraudResult {


    private String actionCode;
    private String errorDescription;
    private String transactionId;
    private String version;
    private String crossReference;
    private String rulesTripped;
    private String totalScore;
    private String recommendationCode;
    private String remarks;
    private String reasonCode;
    private String reasonDescription;
    private List<String> transactionDetails;


    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(String crossReference) {
        this.crossReference = crossReference;
    }

    public String getRulesTripped() {
        return rulesTripped;
    }

    public void setRulesTripped(String rulesTripped) {
        this.rulesTripped = rulesTripped;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getRecommendationCode() {
        return recommendationCode;
    }

    public void setRecommendationCode(String recommendationCode) {
        this.recommendationCode = recommendationCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }

    public List<String> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<String> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }
}
