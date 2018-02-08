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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

/**
 * ErrorDetail
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T10:16:57.900-08:00")
public class ErrorDetail {
  @JsonProperty("detailedMessage")
  private String detailedMessage = null;

  @JsonProperty("errorCode")
  private String errorCode = null;

  @JsonProperty("errorMessage")
  private String errorMessage = null;

  @JsonProperty("path")
  private String path = null;

  @JsonProperty("status")
  private Integer status = null;

  @JsonProperty("timeStamp")
  private DateTime timeStamp = null;

  public ErrorDetail detailedMessage(String detailedMessage) {
    this.detailedMessage = detailedMessage;
    return this;
  }

   /**
   * Message to provide more context to app developer about the error condition
   * @return detailedMessage
  **/
  @ApiModelProperty(value = "Message to provide more context to app developer about the error condition")
  public String getDetailedMessage() {
    return detailedMessage;
  }

  public void setDetailedMessage(String detailedMessage) {
    this.detailedMessage = detailedMessage;
  }

  public ErrorDetail errorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

   /**
   * Error Code to indicate error message category
   * @return errorCode
  **/
  @ApiModelProperty(required = true, value = "Error Code to indicate error message category")
  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorDetail errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

   /**
   * User friendly error message
   * @return errorMessage
  **/
  @ApiModelProperty(required = true, value = "User friendly error message")
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public ErrorDetail path(String path) {
    this.path = path;
    return this;
  }

   /**
   * The request URI that resulted in error
   * @return path
  **/
  @ApiModelProperty(required = true, value = "The request URI that resulted in error")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public ErrorDetail status(Integer status) {
    this.status = status;
    return this;
  }

   /**
   * HTTP Status Code
   * @return status
  **/
  @ApiModelProperty(required = true, value = "HTTP Status Code")
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public ErrorDetail timeStamp(DateTime timeStamp) {
    this.timeStamp = timeStamp;
    return this;
  }

   /**
   * Time when the error occured.
   * @return timeStamp
  **/
  @ApiModelProperty(required = true, value = "Time when the error occured.")
  public DateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(DateTime timeStamp) {
    this.timeStamp = timeStamp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorDetail errorDetail = (ErrorDetail) o;
    return Objects.equals(this.detailedMessage, errorDetail.detailedMessage) &&
        Objects.equals(this.errorCode, errorDetail.errorCode) &&
        Objects.equals(this.errorMessage, errorDetail.errorMessage) &&
        Objects.equals(this.path, errorDetail.path) &&
        Objects.equals(this.status, errorDetail.status) &&
        Objects.equals(this.timeStamp, errorDetail.timeStamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(detailedMessage, errorCode, errorMessage, path, status, timeStamp);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorDetail {\n");
    
    sb.append("    detailedMessage: ").append(toIndentedString(detailedMessage)).append("\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
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

