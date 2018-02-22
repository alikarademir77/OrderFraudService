/*
 * Order Details Service REST API
 * REST API for rertrieving details of various order types.
 *
 * OpenAPI spec version: 1
 * Contact: eCommCAOrders@bestbuy.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package ca.bestbuy.orders.fraud.model.client.orderdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ShippingOrderLine
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-21T17:00:44.098-08:00")
public class ShippingOrderLine {
  @JsonProperty("availability")
  private String availability = null;

  @JsonProperty("cancellable")
  private Boolean cancellable = null;

  @JsonProperty("etaDate")
  private DateTime etaDate = null;

  @JsonProperty("fsoLineRefId")
  private String fsoLineRefId = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("inventoryReservationRefId")
  private String inventoryReservationRefId = null;

  @JsonProperty("qtyCancelled")
  private Integer qtyCancelled = null;

  @JsonProperty("qtyOrdered")
  private Integer qtyOrdered = null;

  @JsonProperty("qtyShipped")
  private Integer qtyShipped = null;

  @JsonProperty("shippingCharges")
  private List<ShippingCharge> shippingCharges = null;

  @JsonProperty("status")
  private Status status = null;

  @JsonProperty("surcharges")
  private List<Surcharge> surcharges = null;

  @JsonProperty("unitPrice")
  private String unitPrice = null;

  public ShippingOrderLine availability(String availability) {
    this.availability = availability;
    return this;
  }

   /**
   * Get availability
   * @return availability
  **/
  @ApiModelProperty(value = "")
  public String getAvailability() {
    return availability;
  }

  public void setAvailability(String availability) {
    this.availability = availability;
  }

  public ShippingOrderLine cancellable(Boolean cancellable) {
    this.cancellable = cancellable;
    return this;
  }

   /**
   * Get cancellable
   * @return cancellable
  **/
  @ApiModelProperty(example = "false", value = "")
  public Boolean getCancellable() {
    return cancellable;
  }

  public void setCancellable(Boolean cancellable) {
    this.cancellable = cancellable;
  }

  public ShippingOrderLine etaDate(DateTime etaDate) {
    this.etaDate = etaDate;
    return this;
  }

   /**
   * Get etaDate
   * @return etaDate
  **/
  @ApiModelProperty(value = "")
  public DateTime getEtaDate() {
    return etaDate;
  }

  public void setEtaDate(DateTime etaDate) {
    this.etaDate = etaDate;
  }

  public ShippingOrderLine fsoLineRefId(String fsoLineRefId) {
    this.fsoLineRefId = fsoLineRefId;
    return this;
  }

   /**
   * Get fsoLineRefId
   * @return fsoLineRefId
  **/
  @ApiModelProperty(value = "")
  public String getFsoLineRefId() {
    return fsoLineRefId;
  }

  public void setFsoLineRefId(String fsoLineRefId) {
    this.fsoLineRefId = fsoLineRefId;
  }

  public ShippingOrderLine id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ShippingOrderLine inventoryReservationRefId(String inventoryReservationRefId) {
    this.inventoryReservationRefId = inventoryReservationRefId;
    return this;
  }

   /**
   * Get inventoryReservationRefId
   * @return inventoryReservationRefId
  **/
  @ApiModelProperty(value = "")
  public String getInventoryReservationRefId() {
    return inventoryReservationRefId;
  }

  public void setInventoryReservationRefId(String inventoryReservationRefId) {
    this.inventoryReservationRefId = inventoryReservationRefId;
  }

  public ShippingOrderLine qtyCancelled(Integer qtyCancelled) {
    this.qtyCancelled = qtyCancelled;
    return this;
  }

   /**
   * Get qtyCancelled
   * @return qtyCancelled
  **/
  @ApiModelProperty(value = "")
  public Integer getQtyCancelled() {
    return qtyCancelled;
  }

  public void setQtyCancelled(Integer qtyCancelled) {
    this.qtyCancelled = qtyCancelled;
  }

  public ShippingOrderLine qtyOrdered(Integer qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
    return this;
  }

   /**
   * Get qtyOrdered
   * @return qtyOrdered
  **/
  @ApiModelProperty(value = "")
  public Integer getQtyOrdered() {
    return qtyOrdered;
  }

  public void setQtyOrdered(Integer qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
  }

  public ShippingOrderLine qtyShipped(Integer qtyShipped) {
    this.qtyShipped = qtyShipped;
    return this;
  }

   /**
   * Get qtyShipped
   * @return qtyShipped
  **/
  @ApiModelProperty(value = "")
  public Integer getQtyShipped() {
    return qtyShipped;
  }

  public void setQtyShipped(Integer qtyShipped) {
    this.qtyShipped = qtyShipped;
  }

  public ShippingOrderLine shippingCharges(List<ShippingCharge> shippingCharges) {
    this.shippingCharges = shippingCharges;
    return this;
  }

  public ShippingOrderLine addShippingChargesItem(ShippingCharge shippingChargesItem) {
    if (this.shippingCharges == null) {
      this.shippingCharges = new ArrayList<ShippingCharge>();
    }
    this.shippingCharges.add(shippingChargesItem);
    return this;
  }

   /**
   * Get shippingCharges
   * @return shippingCharges
  **/
  @ApiModelProperty(value = "")
  public List<ShippingCharge> getShippingCharges() {
    return shippingCharges;
  }

  public void setShippingCharges(List<ShippingCharge> shippingCharges) {
    this.shippingCharges = shippingCharges;
  }

  public ShippingOrderLine status(Status status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")
  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public ShippingOrderLine surcharges(List<Surcharge> surcharges) {
    this.surcharges = surcharges;
    return this;
  }

  public ShippingOrderLine addSurchargesItem(Surcharge surchargesItem) {
    if (this.surcharges == null) {
      this.surcharges = new ArrayList<Surcharge>();
    }
    this.surcharges.add(surchargesItem);
    return this;
  }

   /**
   * Get surcharges
   * @return surcharges
  **/
  @ApiModelProperty(value = "")
  public List<Surcharge> getSurcharges() {
    return surcharges;
  }

  public void setSurcharges(List<Surcharge> surcharges) {
    this.surcharges = surcharges;
  }

  public ShippingOrderLine unitPrice(String unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

   /**
   * Get unitPrice
   * @return unitPrice
  **/
  @ApiModelProperty(value = "")
  public String getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(String unitPrice) {
    this.unitPrice = unitPrice;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShippingOrderLine shippingOrderLine = (ShippingOrderLine) o;
    return Objects.equals(this.availability, shippingOrderLine.availability) &&
        Objects.equals(this.cancellable, shippingOrderLine.cancellable) &&
        Objects.equals(this.etaDate, shippingOrderLine.etaDate) &&
        Objects.equals(this.fsoLineRefId, shippingOrderLine.fsoLineRefId) &&
        Objects.equals(this.id, shippingOrderLine.id) &&
        Objects.equals(this.inventoryReservationRefId, shippingOrderLine.inventoryReservationRefId) &&
        Objects.equals(this.qtyCancelled, shippingOrderLine.qtyCancelled) &&
        Objects.equals(this.qtyOrdered, shippingOrderLine.qtyOrdered) &&
        Objects.equals(this.qtyShipped, shippingOrderLine.qtyShipped) &&
        Objects.equals(this.shippingCharges, shippingOrderLine.shippingCharges) &&
        Objects.equals(this.status, shippingOrderLine.status) &&
        Objects.equals(this.surcharges, shippingOrderLine.surcharges) &&
        Objects.equals(this.unitPrice, shippingOrderLine.unitPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(availability, cancellable, etaDate, fsoLineRefId, id, inventoryReservationRefId, qtyCancelled, qtyOrdered, qtyShipped, shippingCharges, status, surcharges, unitPrice);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShippingOrderLine {\n");
    
    sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
    sb.append("    cancellable: ").append(toIndentedString(cancellable)).append("\n");
    sb.append("    etaDate: ").append(toIndentedString(etaDate)).append("\n");
    sb.append("    fsoLineRefId: ").append(toIndentedString(fsoLineRefId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    inventoryReservationRefId: ").append(toIndentedString(inventoryReservationRefId)).append("\n");
    sb.append("    qtyCancelled: ").append(toIndentedString(qtyCancelled)).append("\n");
    sb.append("    qtyOrdered: ").append(toIndentedString(qtyOrdered)).append("\n");
    sb.append("    qtyShipped: ").append(toIndentedString(qtyShipped)).append("\n");
    sb.append("    shippingCharges: ").append(toIndentedString(shippingCharges)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    surcharges: ").append(toIndentedString(surcharges)).append("\n");
    sb.append("    unitPrice: ").append(toIndentedString(unitPrice)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

