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
import com.bbyc.orders.model.client.orderdetailsservice.DeliveryDate;
import com.bbyc.orders.model.client.orderdetailsservice.FfpCarrierCode;
import com.bbyc.orders.model.client.orderdetailsservice.Name;
import com.bbyc.orders.model.client.orderdetailsservice.RequiresInsurance;
import com.bbyc.orders.model.client.orderdetailsservice.RequiresSignature;
import com.bbyc.orders.model.client.orderdetailsservice.SameDayDelivery;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * LevelOfService
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T17:40:22.226-08:00")
public class LevelOfService {
  @JsonProperty("deliveryDate")
  private DeliveryDate deliveryDate = null;

  @JsonProperty("editable")
  private String editable = null;

  @JsonProperty("ffpCarrierCode")
  private FfpCarrierCode ffpCarrierCode = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private Name name = null;

  @JsonProperty("requiresInsurance")
  private RequiresInsurance requiresInsurance = null;

  @JsonProperty("requiresSignature")
  private RequiresSignature requiresSignature = null;

  @JsonProperty("sameDayDelivery")
  private SameDayDelivery sameDayDelivery = null;

  public LevelOfService deliveryDate(DeliveryDate deliveryDate) {
    this.deliveryDate = deliveryDate;
    return this;
  }

   /**
   * Get deliveryDate
   * @return deliveryDate
  **/
  @ApiModelProperty(value = "")
  public DeliveryDate getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(DeliveryDate deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public LevelOfService editable(String editable) {
    this.editable = editable;
    return this;
  }

   /**
   * Get editable
   * @return editable
  **/
  @ApiModelProperty(value = "")
  public String getEditable() {
    return editable;
  }

  public void setEditable(String editable) {
    this.editable = editable;
  }

  public LevelOfService ffpCarrierCode(FfpCarrierCode ffpCarrierCode) {
    this.ffpCarrierCode = ffpCarrierCode;
    return this;
  }

   /**
   * Get ffpCarrierCode
   * @return ffpCarrierCode
  **/
  @ApiModelProperty(value = "")
  public FfpCarrierCode getFfpCarrierCode() {
    return ffpCarrierCode;
  }

  public void setFfpCarrierCode(FfpCarrierCode ffpCarrierCode) {
    this.ffpCarrierCode = ffpCarrierCode;
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

  public LevelOfService name(Name name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public LevelOfService requiresInsurance(RequiresInsurance requiresInsurance) {
    this.requiresInsurance = requiresInsurance;
    return this;
  }

   /**
   * Get requiresInsurance
   * @return requiresInsurance
  **/
  @ApiModelProperty(value = "")
  public RequiresInsurance getRequiresInsurance() {
    return requiresInsurance;
  }

  public void setRequiresInsurance(RequiresInsurance requiresInsurance) {
    this.requiresInsurance = requiresInsurance;
  }

  public LevelOfService requiresSignature(RequiresSignature requiresSignature) {
    this.requiresSignature = requiresSignature;
    return this;
  }

   /**
   * Get requiresSignature
   * @return requiresSignature
  **/
  @ApiModelProperty(value = "")
  public RequiresSignature getRequiresSignature() {
    return requiresSignature;
  }

  public void setRequiresSignature(RequiresSignature requiresSignature) {
    this.requiresSignature = requiresSignature;
  }

  public LevelOfService sameDayDelivery(SameDayDelivery sameDayDelivery) {
    this.sameDayDelivery = sameDayDelivery;
    return this;
  }

   /**
   * Get sameDayDelivery
   * @return sameDayDelivery
  **/
  @ApiModelProperty(value = "")
  public SameDayDelivery getSameDayDelivery() {
    return sameDayDelivery;
  }

  public void setSameDayDelivery(SameDayDelivery sameDayDelivery) {
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
    return Objects.equals(this.deliveryDate, levelOfService.deliveryDate) &&
        Objects.equals(this.editable, levelOfService.editable) &&
        Objects.equals(this.ffpCarrierCode, levelOfService.ffpCarrierCode) &&
        Objects.equals(this.id, levelOfService.id) &&
        Objects.equals(this.name, levelOfService.name) &&
        Objects.equals(this.requiresInsurance, levelOfService.requiresInsurance) &&
        Objects.equals(this.requiresSignature, levelOfService.requiresSignature) &&
        Objects.equals(this.sameDayDelivery, levelOfService.sameDayDelivery);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deliveryDate, editable, ffpCarrierCode, id, name, requiresInsurance, requiresSignature, sameDayDelivery);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LevelOfService {\n");
    
    sb.append("    deliveryDate: ").append(toIndentedString(deliveryDate)).append("\n");
    sb.append("    editable: ").append(toIndentedString(editable)).append("\n");
    sb.append("    ffpCarrierCode: ").append(toIndentedString(ffpCarrierCode)).append("\n");
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

