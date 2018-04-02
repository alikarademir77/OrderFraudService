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


package ca.bestbuy.orders.fraud.model.client.orderdetails;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-04-02T11:45:53.709-07:00")
public class ErrorDetail {
  @JsonProperty("debugMessage")
  private String debugMessage = null;

  @JsonProperty("errorCode")
  private String errorCode = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("path")
  private String path = null;

  @JsonProperty("status")
  private Integer status = null;

  @JsonProperty("timestamp")
  private DateTime timestamp = null;

  public ErrorDetail debugMessage(String debugMessage) {
    this.debugMessage = debugMessage;
    return this;
  }

   /**
   * Message to provide more context to app developer about the error condition
   * @return debugMessage
  **/
  @ApiModelProperty(value = "Message to provide more context to app developer about the error condition")
  public String getDebugMessage() {
    return debugMessage;
  }

  public void setDebugMessage(String debugMessage) {
    this.debugMessage = debugMessage;
  }

  public ErrorDetail errorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

   /**
   * Error code to indicate message category
   * @return errorCode
  **/
  @ApiModelProperty(value = "Error code to indicate message category")
  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorDetail message(String message) {
    this.message = message;
    return this;
  }

   /**
   * User friendly error message
   * @return message
  **/
  @ApiModelProperty(value = "User friendly error message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorDetail path(String path) {
    this.path = path;
    return this;
  }

   /**
   * The request URI that resulted in the error
   * @return path
  **/
  @ApiModelProperty(value = "The request URI that resulted in the error")
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
   * HTTP status code
   * @return status
  **/
  @ApiModelProperty(value = "HTTP status code")
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public ErrorDetail timestamp(DateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Time when the error occurred
   * @return timestamp
  **/
  @ApiModelProperty(value = "Time when the error occurred")
  public DateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(DateTime timestamp) {
    this.timestamp = timestamp;
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
    return Objects.equals(this.debugMessage, errorDetail.debugMessage) &&
        Objects.equals(this.errorCode, errorDetail.errorCode) &&
        Objects.equals(this.message, errorDetail.message) &&
        Objects.equals(this.path, errorDetail.path) &&
        Objects.equals(this.status, errorDetail.status) &&
        Objects.equals(this.timestamp, errorDetail.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(debugMessage, errorCode, message, path, status, timestamp);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorDetail {\n");
    
    sb.append("    debugMessage: ").append(toIndentedString(debugMessage)).append("\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
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

