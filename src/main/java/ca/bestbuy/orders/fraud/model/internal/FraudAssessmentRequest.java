package ca.bestbuy.orders.fraud.model.internal;


public class FraudAssessmentRequest {

    /**
     * Version id of this fraud assessment request
     */
    private final int requestVersion;


    /**
     *  The order to be sent for fraud assessment
     */
    private final Order order;


    public FraudAssessmentRequest(int requestVersion, Order order) {
        this.requestVersion = requestVersion;
        this.order = order;
    }

    public int getRequestVersion() {
        return requestVersion;
    }

    public Order getOrder() {
        return order;
    }
}
