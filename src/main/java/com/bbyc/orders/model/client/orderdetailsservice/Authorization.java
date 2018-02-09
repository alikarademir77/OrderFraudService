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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Authorization
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T17:40:22.226-08:00")
public class Authorization {
  @JsonProperty("authAmount")
  private String authAmount = null;

  @JsonProperty("authCode")
  private String authCode = null;

  @JsonProperty("paymentServiceErrorCode")
  private String paymentServiceErrorCode = null;

  @JsonProperty("paymentServiceErrorSubCode")
  private String paymentServiceErrorSubCode = null;

  @JsonProperty("responseCode")
  private String responseCode = null;

  @JsonProperty("responseMessage")
  private String responseMessage = null;

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

  public Authorization paymentServiceErrorCode(String paymentServiceErrorCode) {
    this.paymentServiceErrorCode = paymentServiceErrorCode;
    return this;
  }

   /**
   * Get paymentServiceErrorCode
   * @return paymentServiceErrorCode
  **/
  @ApiModelProperty(value = "")
  public String getPaymentServiceErrorCode() {
    return paymentServiceErrorCode;
  }

  public void setPaymentServiceErrorCode(String paymentServiceErrorCode) {
    this.paymentServiceErrorCode = paymentServiceErrorCode;
  }

  public Authorization paymentServiceErrorSubCode(String paymentServiceErrorSubCode) {
    this.paymentServiceErrorSubCode = paymentServiceErrorSubCode;
    return this;
  }

   /**
   * Get paymentServiceErrorSubCode
   * @return paymentServiceErrorSubCode
  **/
  @ApiModelProperty(value = "")
  public String getPaymentServiceErrorSubCode() {
    return paymentServiceErrorSubCode;
  }

  public void setPaymentServiceErrorSubCode(String paymentServiceErrorSubCode) {
    this.paymentServiceErrorSubCode = paymentServiceErrorSubCode;
  }

  public Authorization responseCode(String responseCode) {
    this.responseCode = responseCode;
    return this;
  }

   /**
   * Get responseCode
   * @return responseCode
  **/
  @ApiModelProperty(value = "")
  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public Authorization responseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
    return this;
  }

   /**
   * Get responseMessage
   * @return responseMessage
  **/
  @ApiModelProperty(value = "")
  public String getResponseMessage() {
    return responseMessage;
  }

  public void setResponseMessage(String responseMessage) {
    this.responseMessage = responseMessage;
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
        Objects.equals(this.authCode, authorization.authCode) &&
        Objects.equals(this.paymentServiceErrorCode, authorization.paymentServiceErrorCode) &&
        Objects.equals(this.paymentServiceErrorSubCode, authorization.paymentServiceErrorSubCode) &&
        Objects.equals(this.responseCode, authorization.responseCode) &&
        Objects.equals(this.responseMessage, authorization.responseMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authAmount, authCode, paymentServiceErrorCode, paymentServiceErrorSubCode, responseCode, responseMessage);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Authorization {\n");
    
    sb.append("    authAmount: ").append(toIndentedString(authAmount)).append("\n");
    sb.append("    authCode: ").append(toIndentedString(authCode)).append("\n");
    sb.append("    paymentServiceErrorCode: ").append(toIndentedString(paymentServiceErrorCode)).append("\n");
    sb.append("    paymentServiceErrorSubCode: ").append(toIndentedString(paymentServiceErrorSubCode)).append("\n");
    sb.append("    responseCode: ").append(toIndentedString(responseCode)).append("\n");
    sb.append("    responseMessage: ").append(toIndentedString(responseMessage)).append("\n");
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

