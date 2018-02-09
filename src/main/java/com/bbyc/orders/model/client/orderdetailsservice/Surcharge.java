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
import com.bbyc.orders.model.client.orderdetailsservice.Discount;
import com.bbyc.orders.model.client.orderdetailsservice.StringEditableType;
import com.bbyc.orders.model.client.orderdetailsservice.Tax;
import com.bbyc.orders.model.client.orderdetailsservice.TotalValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Surcharge
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T17:40:22.226-08:00")
public class Surcharge {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("discount")
  private List<Discount> discount = null;

  @JsonProperty("endDate")
  private String endDate = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("quantity")
  private StringEditableType quantity = null;

  @JsonProperty("separateLine")
  private String separateLine = null;

  @JsonProperty("sku")
  private StringEditableType sku = null;

  @JsonProperty("startDate")
  private String startDate = null;

  @JsonProperty("tax")
  private Tax tax = null;

  @JsonProperty("totalValue")
  private TotalValue totalValue = null;

  @JsonProperty("type")
  private String type = null;

  public Surcharge description(String description) {
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

  public Surcharge discount(List<Discount> discount) {
    this.discount = discount;
    return this;
  }

  public Surcharge addDiscountItem(Discount discountItem) {
    if (this.discount == null) {
      this.discount = new ArrayList<Discount>();
    }
    this.discount.add(discountItem);
    return this;
  }

   /**
   * Get discount
   * @return discount
  **/
  @ApiModelProperty(value = "")
  public List<Discount> getDiscount() {
    return discount;
  }

  public void setDiscount(List<Discount> discount) {
    this.discount = discount;
  }

  public Surcharge endDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

   /**
   * Get endDate
   * @return endDate
  **/
  @ApiModelProperty(value = "")
  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public Surcharge id(String id) {
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

  public Surcharge quantity(StringEditableType quantity) {
    this.quantity = quantity;
    return this;
  }

   /**
   * Get quantity
   * @return quantity
  **/
  @ApiModelProperty(value = "")
  public StringEditableType getQuantity() {
    return quantity;
  }

  public void setQuantity(StringEditableType quantity) {
    this.quantity = quantity;
  }

  public Surcharge separateLine(String separateLine) {
    this.separateLine = separateLine;
    return this;
  }

   /**
   * Get separateLine
   * @return separateLine
  **/
  @ApiModelProperty(value = "")
  public String getSeparateLine() {
    return separateLine;
  }

  public void setSeparateLine(String separateLine) {
    this.separateLine = separateLine;
  }

  public Surcharge sku(StringEditableType sku) {
    this.sku = sku;
    return this;
  }

   /**
   * Get sku
   * @return sku
  **/
  @ApiModelProperty(value = "")
  public StringEditableType getSku() {
    return sku;
  }

  public void setSku(StringEditableType sku) {
    this.sku = sku;
  }

  public Surcharge startDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

   /**
   * Get startDate
   * @return startDate
  **/
  @ApiModelProperty(value = "")
  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public Surcharge tax(Tax tax) {
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

  public Surcharge totalValue(TotalValue totalValue) {
    this.totalValue = totalValue;
    return this;
  }

   /**
   * Get totalValue
   * @return totalValue
  **/
  @ApiModelProperty(value = "")
  public TotalValue getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(TotalValue totalValue) {
    this.totalValue = totalValue;
  }

  public Surcharge type(String type) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Surcharge surcharge = (Surcharge) o;
    return Objects.equals(this.description, surcharge.description) &&
        Objects.equals(this.discount, surcharge.discount) &&
        Objects.equals(this.endDate, surcharge.endDate) &&
        Objects.equals(this.id, surcharge.id) &&
        Objects.equals(this.quantity, surcharge.quantity) &&
        Objects.equals(this.separateLine, surcharge.separateLine) &&
        Objects.equals(this.sku, surcharge.sku) &&
        Objects.equals(this.startDate, surcharge.startDate) &&
        Objects.equals(this.tax, surcharge.tax) &&
        Objects.equals(this.totalValue, surcharge.totalValue) &&
        Objects.equals(this.type, surcharge.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, discount, endDate, id, quantity, separateLine, sku, startDate, tax, totalValue, type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Surcharge {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    separateLine: ").append(toIndentedString(separateLine)).append("\n");
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    tax: ").append(toIndentedString(tax)).append("\n");
    sb.append("    totalValue: ").append(toIndentedString(totalValue)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

