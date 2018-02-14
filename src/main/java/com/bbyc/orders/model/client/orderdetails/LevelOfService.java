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
import org.joda.time.DateTime;

/**
 * LevelOfService
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-14T15:03:30.736-08:00")
public class LevelOfService {
  @JsonProperty("carrierCode")
  private String carrierCode = null;

  @JsonProperty("deliveryDate")
  private DateTime deliveryDate = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("requiresInsurance")
  private Boolean requiresInsurance = null;

  @JsonProperty("requiresSignature")
  private Boolean requiresSignature = null;

  @JsonProperty("sameDayDelivery")
  private Boolean sameDayDelivery = null;

  public LevelOfService carrierCode(String carrierCode) {
    this.carrierCode = carrierCode;
    return this;
  }

   /**
   * Get carrierCode
   * @return carrierCode
  **/
  @ApiModelProperty(value = "")
  public String getCarrierCode() {
    return carrierCode;
  }

  public void setCarrierCode(String carrierCode) {
    this.carrierCode = carrierCode;
  }

  public LevelOfService deliveryDate(DateTime deliveryDate) {
    this.deliveryDate = deliveryDate;
    return this;
  }

   /**
   * Get deliveryDate
   * @return deliveryDate
  **/
  @ApiModelProperty(value = "")
  public DateTime getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(DateTime deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public LevelOfService id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LevelOfService name(String name) {
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

  public LevelOfService requiresInsurance(Boolean requiresInsurance) {
    this.requiresInsurance = requiresInsurance;
    return this;
  }

   /**
   * Get requiresInsurance
   * @return requiresInsurance
  **/
  @ApiModelProperty(value = "")
  public Boolean getRequiresInsurance() {
    return requiresInsurance;
  }

  public void setRequiresInsurance(Boolean requiresInsurance) {
    this.requiresInsurance = requiresInsurance;
  }

  public LevelOfService requiresSignature(Boolean requiresSignature) {
    this.requiresSignature = requiresSignature;
    return this;
  }

   /**
   * Get requiresSignature
   * @return requiresSignature
  **/
  @ApiModelProperty(value = "")
  public Boolean getRequiresSignature() {
    return requiresSignature;
  }

  public void setRequiresSignature(Boolean requiresSignature) {
    this.requiresSignature = requiresSignature;
  }

  public LevelOfService sameDayDelivery(Boolean sameDayDelivery) {
    this.sameDayDelivery = sameDayDelivery;
    return this;
  }

   /**
   * Get sameDayDelivery
   * @return sameDayDelivery
  **/
  @ApiModelProperty(value = "")
  public Boolean getSameDayDelivery() {
    return sameDayDelivery;
  }

  public void setSameDayDelivery(Boolean sameDayDelivery) {
    this.sameDayDelivery = sameDayDelivery;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LevelOfService levelOfService = (LevelOfService) o;
    return Objects.equals(this.carrierCode, levelOfService.carrierCode) &&
        Objects.equals(this.deliveryDate, levelOfService.deliveryDate) &&
        Objects.equals(this.id, levelOfService.id) &&
        Objects.equals(this.name, levelOfService.name) &&
        Objects.equals(this.requiresInsurance, levelOfService.requiresInsurance) &&
        Objects.equals(this.requiresSignature, levelOfService.requiresSignature) &&
        Objects.equals(this.sameDayDelivery, levelOfService.sameDayDelivery);
  }

  @Override
  public int hashCode() {
    return Objects.hash(carrierCode, deliveryDate, id, name, requiresInsurance, requiresSignature, sameDayDelivery);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LevelOfService {\n");
    
    sb.append("    carrierCode: ").append(toIndentedString(carrierCode)).append("\n");
    sb.append("    deliveryDate: ").append(toIndentedString(deliveryDate)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    requiresInsurance: ").append(toIndentedString(requiresInsurance)).append("\n");
    sb.append("    requiresSignature: ").append(toIndentedString(requiresSignature)).append("\n");
    sb.append("    sameDayDelivery: ").append(toIndentedString(sameDayDelivery)).append("\n");
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

