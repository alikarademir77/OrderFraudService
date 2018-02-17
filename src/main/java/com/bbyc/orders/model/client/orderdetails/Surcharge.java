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
import com.bbyc.orders.model.client.orderdetails.Discount;
import com.bbyc.orders.model.client.orderdetails.Tax;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Surcharge
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-16T18:06:09.605-08:00")
public class Surcharge {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("discounts")
  private List<Discount> discounts = null;

  @JsonProperty("endDate")
  private DateTime endDate = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("quantity")
  private Integer quantity = null;

  @JsonProperty("separateLine")
  private String separateLine = null;

  @JsonProperty("sku")
  private String sku = null;

  @JsonProperty("startDate")
  private DateTime startDate = null;

  @JsonProperty("tax")
  private Tax tax = null;

  @JsonProperty("totalValue")
  private String totalValue = null;

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

  public Surcharge discounts(List<Discount> discounts) {
    this.discounts = discounts;
    return this;
  }

  public Surcharge addDiscountsItem(Discount discountsItem) {
    if (this.discounts == null) {
      this.discounts = new ArrayList<Discount>();
    }
    this.discounts.add(discountsItem);
    return this;
  }

   /**
   * Get discounts
   * @return discounts
  **/
  @ApiModelProperty(value = "")
  public List<Discount> getDiscounts() {
    return discounts;
  }

  public void setDiscounts(List<Discount> discounts) {
    this.discounts = discounts;
  }

  public Surcharge endDate(DateTime endDate) {
    this.endDate = endDate;
    return this;
  }

   /**
   * Get endDate
   * @return endDate
  **/
  @ApiModelProperty(value = "")
  public DateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(DateTime endDate) {
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

  public Surcharge quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

   /**
   * Get quantity
   * @return quantity
  **/
  @ApiModelProperty(value = "")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
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

  public Surcharge sku(String sku) {
    this.sku = sku;
    return this;
  }

   /**
   * Get sku
   * @return sku
  **/
  @ApiModelProperty(value = "")
  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public Surcharge startDate(DateTime startDate) {
    this.startDate = startDate;
    return this;
  }

   /**
   * Get startDate
   * @return startDate
  **/
  @ApiModelProperty(value = "")
  public DateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(DateTime startDate) {
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

  public Surcharge totalValue(String totalValue) {
    this.totalValue = totalValue;
    return this;
  }

   /**
   * Get totalValue
   * @return totalValue
  **/
  @ApiModelProperty(value = "")
  public String getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(String totalValue) {
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
        Objects.equals(this.discounts, surcharge.discounts) &&
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
    return Objects.hash(description, discounts, endDate, id, quantity, separateLine, sku, startDate, tax, totalValue, type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Surcharge {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    discounts: ").append(toIndentedString(discounts)).append("\n");
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

