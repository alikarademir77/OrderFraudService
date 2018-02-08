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
import com.bbyc.orders.model.client.orderdetailsservice.generated.ItemChargeDiscountType;
import com.bbyc.orders.model.client.orderdetailsservice.generated.Tax;
import com.bbyc.orders.model.client.orderdetailsservice.generated.UnitPrice;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * ItemCharge
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T10:16:57.900-08:00")
public class ItemCharge {
  @JsonProperty("discount")
  private List<ItemChargeDiscountType> discount = null;

  @JsonProperty("discountedUnitPrice")
  private String discountedUnitPrice = null;

  @JsonProperty("editable")
  private String editable = null;

  @JsonProperty("tax")
  private Tax tax = null;

  @JsonProperty("unitPrice")
  private UnitPrice unitPrice = null;

  public ItemCharge discount(List<ItemChargeDiscountType> discount) {
    this.discount = discount;
    return this;
  }

  public ItemCharge addDiscountItem(ItemChargeDiscountType discountItem) {
    if (this.discount == null) {
      this.discount = new ArrayList<ItemChargeDiscountType>();
    }
    this.discount.add(discountItem);
    return this;
  }

   /**
   * Get discount
   * @return discount
  **/
  @ApiModelProperty(value = "")
  public List<ItemChargeDiscountType> getDiscount() {
    return discount;
  }

  public void setDiscount(List<ItemChargeDiscountType> discount) {
    this.discount = discount;
  }

  public ItemCharge discountedUnitPrice(String discountedUnitPrice) {
    this.discountedUnitPrice = discountedUnitPrice;
    return this;
  }

   /**
   * Get discountedUnitPrice
   * @return discountedUnitPrice
  **/
  @ApiModelProperty(value = "")
  public String getDiscountedUnitPrice() {
    return discountedUnitPrice;
  }

  public void setDiscountedUnitPrice(String discountedUnitPrice) {
    this.discountedUnitPrice = discountedUnitPrice;
  }

  public ItemCharge editable(String editable) {
    this.editable = editable;
    return this;
  }

   /**
   * Get editable
   * @return editable
  **/
  @ApiModelProperty(value = "")
  public String getEditable() {
    return editable;
  }

  public void setEditable(String editable) {
    this.editable = editable;
  }

  public ItemCharge tax(Tax tax) {
    this.tax = tax;
    return this;
  }

   /**
   * Get tax
   * @return tax
  **/
  @ApiModelProperty(value = "")
  public Tax getTax() {
    return tax;
  }

  public void setTax(Tax tax) {
    this.tax = tax;
  }

  public ItemCharge unitPrice(UnitPrice unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

   /**
   * Get unitPrice
   * @return unitPrice
  **/
  @ApiModelProperty(value = "")
  public UnitPrice getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(UnitPrice unitPrice) {
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
    ItemCharge itemCharge = (ItemCharge) o;
    return Objects.equals(this.discount, itemCharge.discount) &&
        Objects.equals(this.discountedUnitPrice, itemCharge.discountedUnitPrice) &&
        Objects.equals(this.editable, itemCharge.editable) &&
        Objects.equals(this.tax, itemCharge.tax) &&
        Objects.equals(this.unitPrice, itemCharge.unitPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(discount, discountedUnitPrice, editable, tax, unitPrice);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemCharge {\n");
    
    sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
    sb.append("    discountedUnitPrice: ").append(toIndentedString(discountedUnitPrice)).append("\n");
    sb.append("    editable: ").append(toIndentedString(editable)).append("\n");
    sb.append("    tax: ").append(toIndentedString(tax)).append("\n");
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

