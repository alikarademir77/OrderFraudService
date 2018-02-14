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
 * Authorization
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-13T17:36:04.633-08:00")
public class Authorization {
  @JsonProperty("authAmount")
  private String authAmount = null;

  @JsonProperty("authCode")
  private String authCode = null;

  public Authorization authAmount(String authAmount) {
    this.authAmount = authAmount;
    return this;
  }

   /**
   * Get authAmount
   * @return authAmount
  **/
  @ApiModelProperty(value = "")
  public String getAuthAmount() {
    return authAmount;
  }

  public void setAuthAmount(String authAmount) {
    this.authAmount = authAmount;
  }

  public Authorization authCode(String authCode) {
    this.authCode = authCode;
    return this;
  }

   /**
   * Get authCode
   * @return authCode
  **/
  @ApiModelProperty(value = "")
  public String getAuthCode() {
    return authCode;
  }

  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Authorization authorization = (Authorization) o;
    return Objects.equals(this.authAmount, authorization.authAmount) &&
        Objects.equals(this.authCode, authorization.authCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authAmount, authCode);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Authorization {\n");
    
    sb.append("    authAmount: ").append(toIndentedString(authAmount)).append("\n");
    sb.append("    authCode: ").append(toIndentedString(authCode)).append("\n");
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

