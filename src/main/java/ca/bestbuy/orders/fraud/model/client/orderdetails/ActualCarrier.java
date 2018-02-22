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

import io.swagger.annotations.ApiModelProperty;

/**
 * ActualCarrier
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-21T17:00:44.098-08:00")
public class ActualCarrier {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("levelOfService")
  private LevelOfService levelOfService = null;

  @JsonProperty("shipmentType")
  private String shipmentType = null;

  @JsonProperty("tracking")
  private Tracking tracking = null;

  public ActualCarrier id(String id) {
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

  public ActualCarrier levelOfService(LevelOfService levelOfService) {
    this.levelOfService = levelOfService;
    return this;
  }

   /**
   * Get levelOfService
   * @return levelOfService
  **/
  @ApiModelProperty(value = "")
  public LevelOfService getLevelOfService() {
    return levelOfService;
  }

  public void setLevelOfService(LevelOfService levelOfService) {
    this.levelOfService = levelOfService;
  }

  public ActualCarrier shipmentType(String shipmentType) {
    this.shipmentType = shipmentType;
    return this;
  }

   /**
   * Get shipmentType
   * @return shipmentType
  **/
  @ApiModelProperty(value = "")
  public String getShipmentType() {
    return shipmentType;
  }

  public void setShipmentType(String shipmentType) {
    this.shipmentType = shipmentType;
  }

  public ActualCarrier tracking(Tracking tracking) {
    this.tracking = tracking;
    return this;
  }

   /**
   * Get tracking
   * @return tracking
  **/
  @ApiModelProperty(value = "")
  public Tracking getTracking() {
    return tracking;
  }

  public void setTracking(Tracking tracking) {
    this.tracking = tracking;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ActualCarrier actualCarrier = (ActualCarrier) o;
    return Objects.equals(this.id, actualCarrier.id) &&
        Objects.equals(this.levelOfService, actualCarrier.levelOfService) &&
        Objects.equals(this.shipmentType, actualCarrier.shipmentType) &&
        Objects.equals(this.tracking, actualCarrier.tracking);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, levelOfService, shipmentType, tracking);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActualCarrier {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    levelOfService: ").append(toIndentedString(levelOfService)).append("\n");
    sb.append("    shipmentType: ").append(toIndentedString(shipmentType)).append("\n");
    sb.append("    tracking: ").append(toIndentedString(tracking)).append("\n");
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

