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
import java.util.ArrayList;
import java.util.List;

/**
 * Tracking
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-16T18:06:09.605-08:00")
public class Tracking {
  @JsonProperty("trackingHttpMethod")
  private String trackingHttpMethod = null;

  @JsonProperty("trackingNumbers")
  private List<String> trackingNumbers = null;

  @JsonProperty("trackingParameter")
  private String trackingParameter = null;

  @JsonProperty("trackingURL")
  private String trackingURL = null;

  public Tracking trackingHttpMethod(String trackingHttpMethod) {
    this.trackingHttpMethod = trackingHttpMethod;
    return this;
  }

   /**
   * Get trackingHttpMethod
   * @return trackingHttpMethod
  **/
  @ApiModelProperty(value = "")
  public String getTrackingHttpMethod() {
    return trackingHttpMethod;
  }

  public void setTrackingHttpMethod(String trackingHttpMethod) {
    this.trackingHttpMethod = trackingHttpMethod;
  }

  public Tracking trackingNumbers(List<String> trackingNumbers) {
    this.trackingNumbers = trackingNumbers;
    return this;
  }

  public Tracking addTrackingNumbersItem(String trackingNumbersItem) {
    if (this.trackingNumbers == null) {
      this.trackingNumbers = new ArrayList<String>();
    }
    this.trackingNumbers.add(trackingNumbersItem);
    return this;
  }

   /**
   * Get trackingNumbers
   * @return trackingNumbers
  **/
  @ApiModelProperty(value = "")
  public List<String> getTrackingNumbers() {
    return trackingNumbers;
  }

  public void setTrackingNumbers(List<String> trackingNumbers) {
    this.trackingNumbers = trackingNumbers;
  }

  public Tracking trackingParameter(String trackingParameter) {
    this.trackingParameter = trackingParameter;
    return this;
  }

   /**
   * Get trackingParameter
   * @return trackingParameter
  **/
  @ApiModelProperty(value = "")
  public String getTrackingParameter() {
    return trackingParameter;
  }

  public void setTrackingParameter(String trackingParameter) {
    this.trackingParameter = trackingParameter;
  }

  public Tracking trackingURL(String trackingURL) {
    this.trackingURL = trackingURL;
    return this;
  }

   /**
   * Get trackingURL
   * @return trackingURL
  **/
  @ApiModelProperty(value = "")
  public String getTrackingURL() {
    return trackingURL;
  }

  public void setTrackingURL(String trackingURL) {
    this.trackingURL = trackingURL;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tracking tracking = (Tracking) o;
    return Objects.equals(this.trackingHttpMethod, tracking.trackingHttpMethod) &&
        Objects.equals(this.trackingNumbers, tracking.trackingNumbers) &&
        Objects.equals(this.trackingParameter, tracking.trackingParameter) &&
        Objects.equals(this.trackingURL, tracking.trackingURL);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trackingHttpMethod, trackingNumbers, trackingParameter, trackingURL);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Tracking {\n");
    
    sb.append("    trackingHttpMethod: ").append(toIndentedString(trackingHttpMethod)).append("\n");
    sb.append("    trackingNumbers: ").append(toIndentedString(trackingNumbers)).append("\n");
    sb.append("    trackingParameter: ").append(toIndentedString(trackingParameter)).append("\n");
    sb.append("    trackingURL: ").append(toIndentedString(trackingURL)).append("\n");
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

