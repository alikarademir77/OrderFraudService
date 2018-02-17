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
 * RewardZone
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-16T18:06:09.605-08:00")
public class RewardZone {
  @JsonProperty("rewardZoneId")
  private String rewardZoneId = null;

  public RewardZone rewardZoneId(String rewardZoneId) {
    this.rewardZoneId = rewardZoneId;
    return this;
  }

   /**
   * Get rewardZoneId
   * @return rewardZoneId
  **/
  @ApiModelProperty(value = "")
  public String getRewardZoneId() {
    return rewardZoneId;
  }

  public void setRewardZoneId(String rewardZoneId) {
    this.rewardZoneId = rewardZoneId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RewardZone rewardZone = (RewardZone) o;
    return Objects.equals(this.rewardZoneId, rewardZone.rewardZoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rewardZoneId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RewardZone {\n");
    
    sb.append("    rewardZoneId: ").append(toIndentedString(rewardZoneId)).append("\n");
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

