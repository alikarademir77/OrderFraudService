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


package com.bbyc.orders.model.client.orderdetailsservice;

import java.util.Objects;
import com.bbyc.orders.model.client.orderdetailsservice.ShippingChargeDiscountType;
import com.bbyc.orders.model.client.orderdetailsservice.Sku;
import com.bbyc.orders.model.client.orderdetailsservice.Tax;
import com.bbyc.orders.model.client.orderdetailsservice.UnitPrice;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * ShippingCharge
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T17:40:22.226-08:00")
public class ShippingCharge {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("discount")
  private List<ShippingChargeDiscountType> discount = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("sku")
  private Sku sku = null;

  @JsonProperty("tax")
  private Tax tax = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("unitPrice")
  private UnitPrice unitPrice = null;

  public ShippingCharge description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ShippingCharge discount(List<ShippingChargeDiscountType> discount) {
    this.discount = discount;
    return this;
  }

  public ShippingCharge addDiscountItem(ShippingChargeDiscountType discountItem) {
    if (this.discount == null) {
      this.discount = new ArrayList<ShippingChargeDiscountType>();
    }
    this.discount.add(discountItem);
    return this;
  }

   /**
   * Get discount
   * @return discount
  **/
  @ApiModelProperty(value = "")
  public List<ShippingChargeDiscountType> getDiscount() {
    return discount;
  }

  public void setDiscount(List<ShippingChargeDiscountType> discount) {
    this.discount = discount;
  }

  public ShippingCharge id(String id) {
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

  public ShippingCharge sku(Sku sku) {
    this.sku = sku;
    return this;
  }

   /**
   * Get sku
   * @return sku
  **/
  @ApiModelProperty(value = "")
  public Sku getSku() {
    return sku;
  }

  public void setSku(Sku sku) {
    this.sku = sku;
  }

  public ShippingCharge tax(Tax tax) {
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

  public ShippingCharge type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ShippingCharge unitPrice(UnitPrice unitPrice) {
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
    ShippingCharge shippingCharge = (ShippingCharge) o;
    return Objects.equals(this.description, shippingCharge.description) &&
        Objects.equals(this.discount, shippingCharge.discount) &&
        Objects.equals(this.id, shippingCharge.id) &&
        Objects.equals(this.sku, shippingCharge.sku) &&
        Objects.equals(this.tax, shippingCharge.tax) &&
        Objects.equals(this.type, shippingCharge.type) &&
        Objects.equals(this.unitPrice, shippingCharge.unitPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, discount, id, sku, tax, type, unitPrice);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShippingCharge {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
    sb.append("    tax: ").append(toIndentedString(tax)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

