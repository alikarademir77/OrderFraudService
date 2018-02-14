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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Term
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-14T15:03:30.736-08:00")
public class Term {
  @JsonProperty("length")
  private Integer length = null;

  @JsonProperty("unitOfTerm")
  private String unitOfTerm = null;

  public Term length(Integer length) {
    this.length = length;
    return this;
  }

   /**
   * Get length
   * @return length
  **/
  @ApiModelProperty(value = "")
  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Term unitOfTerm(String unitOfTerm) {
    this.unitOfTerm = unitOfTerm;
    return this;
  }

   /**
   * Get unitOfTerm
   * @return unitOfTerm
  **/
  @ApiModelProperty(value = "")
  public String getUnitOfTerm() {
    return unitOfTerm;
  }

  public void setUnitOfTerm(String unitOfTerm) {
    this.unitOfTerm = unitOfTerm;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Term term = (Term) o;
    return Objects.equals(this.length, term.length) &&
        Objects.equals(this.unitOfTerm, term.unitOfTerm);
  }

  @Override
  public int hashCode() {
    return Objects.hash(length, unitOfTerm);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Term {\n");
    
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    unitOfTerm: ").append(toIndentedString(unitOfTerm)).append("\n");
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

