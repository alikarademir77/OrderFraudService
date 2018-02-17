/*
 * Order Details Service REST API
 * REST API for retrieving details of an order.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: eCommCAOrders@bestbuy.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.bbyc.orders.model.client.orderdetails;

import java.util.Objects;
import com.bbyc.orders.model.client.orderdetails.ItemChargeDiscount;
import com.bbyc.orders.model.client.orderdetails.Tax;
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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-16T18:06:09.605-08:00")
public class ItemCharge {
  @JsonProperty("discounts")
  private List<ItemChargeDiscount> discounts = null;

  @JsonProperty("discountedUnitPrice")
  private Float discountedUnitPrice = null;

  @JsonProperty("tax")
  private Tax tax = null;

  @JsonProperty("unitPrice")
  private Float unitPrice = null;

  public ItemCharge discounts(List<ItemChargeDiscount> discounts) {
    this.discounts = discounts;
    return this;
  }

  public ItemCharge addDiscountsItem(ItemChargeDiscount discountsItem) {
    if (this.discounts == null) {
      this.discounts = new ArrayList<ItemChargeDiscount>();
    }
    this.discounts.add(discountsItem);
    return this;
  }

   /**
   * Get discounts
   * @return discounts
  **/
  @ApiModelProperty(value = "")
  public List<ItemChargeDiscount> getDiscounts() {
    return discounts;
  }

  public void setDiscounts(List<ItemChargeDiscount> discounts) {
    this.discounts = discounts;
  }

  public ItemCharge discountedUnitPrice(Float discountedUnitPrice) {
    this.discountedUnitPrice = discountedUnitPrice;
    return this;
  }

   /**
   * Get discountedUnitPrice
   * @return discountedUnitPrice
  **/
  @ApiModelProperty(value = "")
  public Float getDiscountedUnitPrice() {
    return discountedUnitPrice;
  }

  public void setDiscountedUnitPrice(Float discountedUnitPrice) {
    this.discountedUnitPrice = discountedUnitPrice;
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

  public ItemCharge unitPrice(Float unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

   /**
   * Get unitPrice
   * @return unitPrice
  **/
  @ApiModelProperty(value = "")
  public Float getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Float unitPrice) {
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
    return Objects.equals(this.discounts, itemCharge.discounts) &&
        Objects.equals(this.discountedUnitPrice, itemCharge.discountedUnitPrice) &&
        Objects.equals(this.tax, itemCharge.tax) &&
        Objects.equals(this.unitPrice, itemCharge.unitPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(discounts, discountedUnitPrice, tax, unitPrice);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemCharge {\n");
    
    sb.append("    discounts: ").append(toIndentedString(discounts)).append("\n");
    sb.append("    discountedUnitPrice: ").append(toIndentedString(discountedUnitPrice)).append("\n");
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

