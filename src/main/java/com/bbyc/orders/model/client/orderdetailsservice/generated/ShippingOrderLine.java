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


package com.bbyc.orders.model.client.orderdetailsservice.generated;

import java.util.Objects;
import com.bbyc.orders.model.client.orderdetailsservice.generated.ShippingCharge;
import com.bbyc.orders.model.client.orderdetailsservice.generated.ShippingStatus;
import com.bbyc.orders.model.client.orderdetailsservice.generated.StringEditableType;
import com.bbyc.orders.model.client.orderdetailsservice.generated.Surcharge;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * ShippingOrderLine
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T10:16:57.900-08:00")
public class ShippingOrderLine {
  @JsonProperty("availability")
  private String availability = null;

  @JsonProperty("cancellable")
  private String cancellable = null;

  @JsonProperty("etaDate")
  private String etaDate = null;

  @JsonProperty("fsoLineID")
  private String fsoLineID = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("inventoryReservationId")
  private StringEditableType inventoryReservationId = null;

  @JsonProperty("qtyCancelled")
  private String qtyCancelled = null;

  @JsonProperty("qtyOrdered")
  private String qtyOrdered = null;

  @JsonProperty("qtyShipped")
  private String qtyShipped = null;

  @JsonProperty("shippingCharge")
  private List<ShippingCharge> shippingCharge = null;

  @JsonProperty("shippingStatus")
  private ShippingStatus shippingStatus = null;

  @JsonProperty("surcharge")
  private List<Surcharge> surcharge = null;

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

  public ShippingOrderLine cancellable(String cancellable) {
    this.cancellable = cancellable;
    return this;
  }

   /**
   * Get cancellable
   * @return cancellable
  **/
  @ApiModelProperty(value = "")
  public String getCancellable() {
    return cancellable;
  }

  public void setCancellable(String cancellable) {
    this.cancellable = cancellable;
  }

  public ShippingOrderLine etaDate(String etaDate) {
    this.etaDate = etaDate;
    return this;
  }

   /**
   * Get etaDate
   * @return etaDate
  **/
  @ApiModelProperty(value = "")
  public String getEtaDate() {
    return etaDate;
  }

  public void setEtaDate(String etaDate) {
    this.etaDate = etaDate;
  }

  public ShippingOrderLine fsoLineID(String fsoLineID) {
    this.fsoLineID = fsoLineID;
    return this;
  }

   /**
   * Get fsoLineID
   * @return fsoLineID
  **/
  @ApiModelProperty(value = "")
  public String getFsoLineID() {
    return fsoLineID;
  }

  public void setFsoLineID(String fsoLineID) {
    this.fsoLineID = fsoLineID;
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

  public ShippingOrderLine inventoryReservationId(StringEditableType inventoryReservationId) {
    this.inventoryReservationId = inventoryReservationId;
    return this;
  }

   /**
   * Get inventoryReservationId
   * @return inventoryReservationId
  **/
  @ApiModelProperty(value = "")
  public StringEditableType getInventoryReservationId() {
    return inventoryReservationId;
  }

  public void setInventoryReservationId(StringEditableType inventoryReservationId) {
    this.inventoryReservationId = inventoryReservationId;
  }

  public ShippingOrderLine qtyCancelled(String qtyCancelled) {
    this.qtyCancelled = qtyCancelled;
    return this;
  }

   /**
   * Get qtyCancelled
   * @return qtyCancelled
  **/
  @ApiModelProperty(value = "")
  public String getQtyCancelled() {
    return qtyCancelled;
  }

  public void setQtyCancelled(String qtyCancelled) {
    this.qtyCancelled = qtyCancelled;
  }

  public ShippingOrderLine qtyOrdered(String qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
    return this;
  }

   /**
   * Get qtyOrdered
   * @return qtyOrdered
  **/
  @ApiModelProperty(value = "")
  public String getQtyOrdered() {
    return qtyOrdered;
  }

  public void setQtyOrdered(String qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
  }

  public ShippingOrderLine qtyShipped(String qtyShipped) {
    this.qtyShipped = qtyShipped;
    return this;
  }

   /**
   * Get qtyShipped
   * @return qtyShipped
  **/
  @ApiModelProperty(value = "")
  public String getQtyShipped() {
    return qtyShipped;
  }

  public void setQtyShipped(String qtyShipped) {
    this.qtyShipped = qtyShipped;
  }

  public ShippingOrderLine shippingCharge(List<ShippingCharge> shippingCharge) {
    this.shippingCharge = shippingCharge;
    return this;
  }

  public ShippingOrderLine addShippingChargeItem(ShippingCharge shippingChargeItem) {
    if (this.shippingCharge == null) {
      this.shippingCharge = new ArrayList<ShippingCharge>();
    }
    this.shippingCharge.add(shippingChargeItem);
    return this;
  }

   /**
   * Get shippingCharge
   * @return shippingCharge
  **/
  @ApiModelProperty(value = "")
  public List<ShippingCharge> getShippingCharge() {
    return shippingCharge;
  }

  public void setShippingCharge(List<ShippingCharge> shippingCharge) {
    this.shippingCharge = shippingCharge;
  }

  public ShippingOrderLine shippingStatus(ShippingStatus shippingStatus) {
    this.shippingStatus = shippingStatus;
    return this;
  }

   /**
   * Get shippingStatus
   * @return shippingStatus
  **/
  @ApiModelProperty(value = "")
  public ShippingStatus getShippingStatus() {
    return shippingStatus;
  }

  public void setShippingStatus(ShippingStatus shippingStatus) {
    this.shippingStatus = shippingStatus;
  }

  public ShippingOrderLine surcharge(List<Surcharge> surcharge) {
    this.surcharge = surcharge;
    return this;
  }

  public ShippingOrderLine addSurchargeItem(Surcharge surchargeItem) {
    if (this.surcharge == null) {
      this.surcharge = new ArrayList<Surcharge>();
    }
    this.surcharge.add(surchargeItem);
    return this;
  }

   /**
   * Get surcharge
   * @return surcharge
  **/
  @ApiModelProperty(value = "")
  public List<Surcharge> getSurcharge() {
    return surcharge;
  }

  public void setSurcharge(List<Surcharge> surcharge) {
    this.surcharge = surcharge;
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
        Objects.equals(this.fsoLineID, shippingOrderLine.fsoLineID) &&
        Objects.equals(this.id, shippingOrderLine.id) &&
        Objects.equals(this.inventoryReservationId, shippingOrderLine.inventoryReservationId) &&
        Objects.equals(this.qtyCancelled, shippingOrderLine.qtyCancelled) &&
        Objects.equals(this.qtyOrdered, shippingOrderLine.qtyOrdered) &&
        Objects.equals(this.qtyShipped, shippingOrderLine.qtyShipped) &&
        Objects.equals(this.shippingCharge, shippingOrderLine.shippingCharge) &&
        Objects.equals(this.shippingStatus, shippingOrderLine.shippingStatus) &&
        Objects.equals(this.surcharge, shippingOrderLine.surcharge) &&
        Objects.equals(this.unitPrice, shippingOrderLine.unitPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(availability, cancellable, etaDate, fsoLineID, id, inventoryReservationId, qtyCancelled, qtyOrdered, qtyShipped, shippingCharge, shippingStatus, surcharge, unitPrice);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShippingOrderLine {\n");
    
    sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
    sb.append("    cancellable: ").append(toIndentedString(cancellable)).append("\n");
    sb.append("    etaDate: ").append(toIndentedString(etaDate)).append("\n");
    sb.append("    fsoLineID: ").append(toIndentedString(fsoLineID)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    inventoryReservationId: ").append(toIndentedString(inventoryReservationId)).append("\n");
    sb.append("    qtyCancelled: ").append(toIndentedString(qtyCancelled)).append("\n");
    sb.append("    qtyOrdered: ").append(toIndentedString(qtyOrdered)).append("\n");
    sb.append("    qtyShipped: ").append(toIndentedString(qtyShipped)).append("\n");
    sb.append("    shippingCharge: ").append(toIndentedString(shippingCharge)).append("\n");
    sb.append("    shippingStatus: ").append(toIndentedString(shippingStatus)).append("\n");
    sb.append("    surcharge: ").append(toIndentedString(surcharge)).append("\n");
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

