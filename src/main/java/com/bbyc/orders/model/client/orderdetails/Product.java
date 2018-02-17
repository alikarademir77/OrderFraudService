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
import com.bbyc.orders.model.client.orderdetails.ProductServicePlan;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Product
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-16T18:06:09.605-08:00")
public class Product {
  @JsonProperty("mfr")
  private String mfr = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("productServicePlan")
  private ProductServicePlan productServicePlan = null;

  @JsonProperty("serialNumbers")
  private List<String> serialNumbers = null;

  @JsonProperty("sku")
  private String sku = null;

  public Product mfr(String mfr) {
    this.mfr = mfr;
    return this;
  }

   /**
   * Get mfr
   * @return mfr
  **/
  @ApiModelProperty(value = "")
  public String getMfr() {
    return mfr;
  }

  public void setMfr(String mfr) {
    this.mfr = mfr;
  }

  public Product name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Product productServicePlan(ProductServicePlan productServicePlan) {
    this.productServicePlan = productServicePlan;
    return this;
  }

   /**
   * Get productServicePlan
   * @return productServicePlan
  **/
  @ApiModelProperty(value = "")
  public ProductServicePlan getProductServicePlan() {
    return productServicePlan;
  }

  public void setProductServicePlan(ProductServicePlan productServicePlan) {
    this.productServicePlan = productServicePlan;
  }

  public Product serialNumbers(List<String> serialNumbers) {
    this.serialNumbers = serialNumbers;
    return this;
  }

  public Product addSerialNumbersItem(String serialNumbersItem) {
    if (this.serialNumbers == null) {
      this.serialNumbers = new ArrayList<String>();
    }
    this.serialNumbers.add(serialNumbersItem);
    return this;
  }

   /**
   * Get serialNumbers
   * @return serialNumbers
  **/
  @ApiModelProperty(value = "")
  public List<String> getSerialNumbers() {
    return serialNumbers;
  }

  public void setSerialNumbers(List<String> serialNumbers) {
    this.serialNumbers = serialNumbers;
  }

  public Product sku(String sku) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(this.mfr, product.mfr) &&
        Objects.equals(this.name, product.name) &&
        Objects.equals(this.productServicePlan, product.productServicePlan) &&
        Objects.equals(this.serialNumbers, product.serialNumbers) &&
        Objects.equals(this.sku, product.sku);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mfr, name, productServicePlan, serialNumbers, sku);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");
    
    sb.append("    mfr: ").append(toIndentedString(mfr)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    productServicePlan: ").append(toIndentedString(productServicePlan)).append("\n");
    sb.append("    serialNumbers: ").append(toIndentedString(serialNumbers)).append("\n");
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
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

