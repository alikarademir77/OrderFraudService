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
import com.bbyc.orders.model.client.orderdetailsservice.generated.StringEditableType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Tax
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T10:16:57.900-08:00")
public class Tax {
  @JsonProperty("gst")
  private StringEditableType gst = null;

  @JsonProperty("miscTax")
  private String miscTax = null;

  @JsonProperty("pst")
  private StringEditableType pst = null;

  @JsonProperty("salesTax")
  private String salesTax = null;

  @JsonProperty("valueAddTax")
  private String valueAddTax = null;

  public Tax gst(StringEditableType gst) {
    this.gst = gst;
    return this;
  }

   /**
   * Get gst
   * @return gst
  **/
  @ApiModelProperty(value = "")
  public StringEditableType getGst() {
    return gst;
  }

  public void setGst(StringEditableType gst) {
    this.gst = gst;
  }

  public Tax miscTax(String miscTax) {
    this.miscTax = miscTax;
    return this;
  }

   /**
   * Get miscTax
   * @return miscTax
  **/
  @ApiModelProperty(value = "")
  public String getMiscTax() {
    return miscTax;
  }

  public void setMiscTax(String miscTax) {
    this.miscTax = miscTax;
  }

  public Tax pst(StringEditableType pst) {
    this.pst = pst;
    return this;
  }

   /**
   * Get pst
   * @return pst
  **/
  @ApiModelProperty(value = "")
  public StringEditableType getPst() {
    return pst;
  }

  public void setPst(StringEditableType pst) {
    this.pst = pst;
  }

  public Tax salesTax(String salesTax) {
    this.salesTax = salesTax;
    return this;
  }

   /**
   * Get salesTax
   * @return salesTax
  **/
  @ApiModelProperty(value = "")
  public String getSalesTax() {
    return salesTax;
  }

  public void setSalesTax(String salesTax) {
    this.salesTax = salesTax;
  }

  public Tax valueAddTax(String valueAddTax) {
    this.valueAddTax = valueAddTax;
    return this;
  }

   /**
   * Get valueAddTax
   * @return valueAddTax
  **/
  @ApiModelProperty(value = "")
  public String getValueAddTax() {
    return valueAddTax;
  }

  public void setValueAddTax(String valueAddTax) {
    this.valueAddTax = valueAddTax;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tax tax = (Tax) o;
    return Objects.equals(this.gst, tax.gst) &&
        Objects.equals(this.miscTax, tax.miscTax) &&
        Objects.equals(this.pst, tax.pst) &&
        Objects.equals(this.salesTax, tax.salesTax) &&
        Objects.equals(this.valueAddTax, tax.valueAddTax);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gst, miscTax, pst, salesTax, valueAddTax);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Tax {\n");
    
    sb.append("    gst: ").append(toIndentedString(gst)).append("\n");
    sb.append("    miscTax: ").append(toIndentedString(miscTax)).append("\n");
    sb.append("    pst: ").append(toIndentedString(pst)).append("\n");
    sb.append("    salesTax: ").append(toIndentedString(salesTax)).append("\n");
    sb.append("    valueAddTax: ").append(toIndentedString(valueAddTax)).append("\n");
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

