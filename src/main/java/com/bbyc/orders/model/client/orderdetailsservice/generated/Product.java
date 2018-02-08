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
import com.bbyc.orders.model.client.orderdetailsservice.generated.PSP;
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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T10:16:57.900-08:00")
public class Product {
  @JsonProperty("mfr")
  private String mfr = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("psp")
  private PSP psp = null;

  @JsonProperty("serialNumber")
  private List<String> serialNumber = null;

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

  public Product psp(PSP psp) {
    this.psp = psp;
    return this;
  }

   /**
   * Get psp
   * @return psp
  **/
  @ApiModelProperty(value = "")
  public PSP getPsp() {
    return psp;
  }

  public void setPsp(PSP psp) {
    this.psp = psp;
  }

  public Product serialNumber(List<String> serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

  public Product addSerialNumberItem(String serialNumberItem) {
    if (this.serialNumber == null) {
      this.serialNumber = new ArrayList<String>();
    }
    this.serialNumber.add(serialNumberItem);
    return this;
  }

   /**
   * Get serialNumber
   * @return serialNumber
  **/
  @ApiModelProperty(value = "")
  public List<String> getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(List<String> serialNumber) {
    this.serialNumber = serialNumber;
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
        Objects.equals(this.psp, product.psp) &&
        Objects.equals(this.serialNumber, product.serialNumber) &&
        Objects.equals(this.sku, product.sku);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mfr, name, psp, serialNumber, sku);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");
    
    sb.append("    mfr: ").append(toIndentedString(mfr)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    psp: ").append(toIndentedString(psp)).append("\n");
    sb.append("    serialNumber: ").append(toIndentedString(serialNumber)).append("\n");
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

