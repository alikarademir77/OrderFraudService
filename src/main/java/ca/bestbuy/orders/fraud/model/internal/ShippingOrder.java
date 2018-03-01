package ca.bestbuy.orders.fraud.model.internal;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;

/**
 * Shipping order representation
 */
public class ShippingOrder {

    /**
     * Shipping order ID
     */
    private String shippingOrderID;


    /**
     * Shipping order status<br/>
     * <br/>
     * Possible statuses:
     * <ul>
     *     <li>Open: If the shipping order is not shipped or not cancelled</li>
     *     <li>Cancelled: If the shipping order is cancelled</li>
     *     <li>Completed: If the shipping order is shipped</li>
     * </ul>
     */
    private String shippingOrderStatus;

    /**
     * Global contract ID
     */
    private String globalContractID;

    /**
     * Shipping charge
     */
    private BigDecimal shippingCharge;


    /**
     * Tax on shipping charge
     */
    private BigDecimal shippingTax;

    /**
     * Total Shipping Discount
     */
    private BigDecimal shippingDiscount;


    /**
     * Fulfillment partner
     */
    private String fulfillmentPartner;

    /**
     * The shipping method with the following format: {Carrier Code}-{Service Level}
     * <br/><br/>
     * e.g. CPCL-GROUND
     */
    private String shippingMethod;

    /**
     * Shipping deliveryDate
     */
    private DateTime deliveryDate;

    /**
     * Shipping address
     */
    private Address shippingAddress;

    /**
     * List of shipping order lines
     */
    private List<ShippingOrderLine> shippingOrderLines;

    /**
     * List of chargebacks
     */
    private List<Chargeback> chargebacks;


    public String getShippingOrderID() {
        return shippingOrderID;
    }

    public void setShippingOrderID(String shippingOrderID) {
        this.shippingOrderID = shippingOrderID;
    }

    public String getShippingOrderStatus() {
        return shippingOrderStatus;
    }

    public void setShippingOrderStatus(String shippingOrderStatus) {
        this.shippingOrderStatus = shippingOrderStatus;
    }

    public String getGlobalContractID() {
        return globalContractID;
    }

    public void setGlobalContractID(String globalContractID) {
        this.globalContractID = globalContractID;
    }

    public BigDecimal getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(BigDecimal shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public BigDecimal getShippingTax() {
        return shippingTax;
    }

    public void setShippingTax(BigDecimal shippingTax) {
        this.shippingTax = shippingTax;
    }

    public BigDecimal getShippingDiscount() {
        return shippingDiscount;
    }

    public void setShippingDiscount(BigDecimal shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
    }

    public String getFulfillmentPartner() {
        return fulfillmentPartner;
    }

    public void setFulfillmentPartner(String fulfillmentPartner) {
        this.fulfillmentPartner = fulfillmentPartner;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public DateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(DateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<ShippingOrderLine> getShippingOrderLines() {
        return shippingOrderLines;
    }

    public void setShippingOrderLines(List<ShippingOrderLine> shippingOrderLines) {
        this.shippingOrderLines = shippingOrderLines;
    }

    public List<Chargeback> getChargebacks() {
        return chargebacks;
    }

    public void setChargebacks(List<Chargeback> chargebacks) {
        this.chargebacks = chargebacks;
    }

}
