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
import java.util.ArrayList;
import java.util.List;

/**
 * TrackingNumbers
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T10:16:57.900-08:00")
public class TrackingNumbers {
  @JsonProperty("trackingNumber")
  private List<String> trackingNumber = null;

  public TrackingNumbers trackingNumber(List<String> trackingNumber) {
    this.trackingNumber = trackingNumber;
    return this;
  }

  public TrackingNumbers addTrackingNumberItem(String trackingNumberItem) {
    if (this.trackingNumber == null) {
      this.trackingNumber = new ArrayList<String>();
    }
    this.trackingNumber.add(trackingNumberItem);
    return this;
  }

   /**
   * Get trackingNumber
   * @return trackingNumber
  **/
  @ApiModelProperty(value = "")
  public List<String> getTrackingNumber() {
    return trackingNumber;
  }

  public void setTrackingNumber(List<String> trackingNumber) {
    this.trackingNumber = trackingNumber;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TrackingNumbers trackingNumbers = (TrackingNumbers) o;
    return Objects.equals(this.trackingNumber, trackingNumbers.trackingNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trackingNumber);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TrackingNumbers {\n");
    
    sb.append("    trackingNumber: ").append(toIndentedString(trackingNumber)).append("\n");
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

