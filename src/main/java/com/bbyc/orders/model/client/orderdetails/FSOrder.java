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
import com.bbyc.orders.model.client.orderdetails.Bundle;
import com.bbyc.orders.model.client.orderdetails.Customer;
import com.bbyc.orders.model.client.orderdetails.FSOrderLine;
import com.bbyc.orders.model.client.orderdetails.PaymentMethodInfo;
import com.bbyc.orders.model.client.orderdetails.PurchaseOrder;
import com.bbyc.orders.model.client.orderdetails.RewardZone;
import com.bbyc.orders.model.client.orderdetails.ShippingModel;
import com.bbyc.orders.model.client.orderdetails.ShippingOrder;
import com.bbyc.orders.model.client.orderdetails.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;

/**
 * FSOrder
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-13T11:58:37.070-08:00")
public class FSOrder {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("bundles")
  private List<Bundle> bundles = null;

  @JsonProperty("customer")
  private Customer customer = null;

  @JsonProperty("fsOrderLines")
  private List<FSOrderLine> fsOrderLines = null;

  @JsonProperty("ipAddress")
  private String ipAddress = null;

  @JsonProperty("paymentMethodInfo")
  private PaymentMethodInfo paymentMethodInfo = null;

  @JsonProperty("purchaseOrders")
  private List<PurchaseOrder> purchaseOrders = null;

  @JsonProperty("rewardZone")
  private RewardZone rewardZone = null;

  @JsonProperty("shippingModel")
  private ShippingModel shippingModel = null;

  @JsonProperty("shippingOrders")
  private List<ShippingOrder> shippingOrders = null;

  @JsonProperty("status")
  private Status status = null;

  @JsonProperty("webOrderRefId")
  private String webOrderRefId = null;

  @JsonProperty("webOrderCreationDate")
  private OffsetDateTime webOrderCreationDate = null;

  public FSOrder id(String id) {
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

  public FSOrder bundles(List<Bundle> bundles) {
    this.bundles = bundles;
    return this;
  }

  public FSOrder addBundlesItem(Bundle bundlesItem) {
    if (this.bundles == null) {
      this.bundles = new ArrayList<Bundle>();
    }
    this.bundles.add(bundlesItem);
    return this;
  }

   /**
   * Get bundles
   * @return bundles
  **/
  @ApiModelProperty(value = "")
  public List<Bundle> getBundles() {
    return bundles;
  }

  public void setBundles(List<Bundle> bundles) {
    this.bundles = bundles;
  }

  public FSOrder customer(Customer customer) {
    this.customer = customer;
    return this;
  }

   /**
   * Get customer
   * @return customer
  **/
  @ApiModelProperty(value = "")
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public FSOrder fsOrderLines(List<FSOrderLine> fsOrderLines) {
    this.fsOrderLines = fsOrderLines;
    return this;
  }

  public FSOrder addFsOrderLinesItem(FSOrderLine fsOrderLinesItem) {
    if (this.fsOrderLines == null) {
      this.fsOrderLines = new ArrayList<FSOrderLine>();
    }
    this.fsOrderLines.add(fsOrderLinesItem);
    return this;
  }

   /**
   * Get fsOrderLines
   * @return fsOrderLines
  **/
  @ApiModelProperty(value = "")
  public List<FSOrderLine> getFsOrderLines() {
    return fsOrderLines;
  }

  public void setFsOrderLines(List<FSOrderLine> fsOrderLines) {
    this.fsOrderLines = fsOrderLines;
  }

  public FSOrder ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

   /**
   * Get ipAddress
   * @return ipAddress
  **/
  @ApiModelProperty(value = "")
  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public FSOrder paymentMethodInfo(PaymentMethodInfo paymentMethodInfo) {
    this.paymentMethodInfo = paymentMethodInfo;
    return this;
  }

   /**
   * Get paymentMethodInfo
   * @return paymentMethodInfo
  **/
  @ApiModelProperty(value = "")
  public PaymentMethodInfo getPaymentMethodInfo() {
    return paymentMethodInfo;
  }

  public void setPaymentMethodInfo(PaymentMethodInfo paymentMethodInfo) {
    this.paymentMethodInfo = paymentMethodInfo;
  }

  public FSOrder purchaseOrders(List<PurchaseOrder> purchaseOrders) {
    this.purchaseOrders = purchaseOrders;
    return this;
  }

  public FSOrder addPurchaseOrdersItem(PurchaseOrder purchaseOrdersItem) {
    if (this.purchaseOrders == null) {
      this.purchaseOrders = new ArrayList<PurchaseOrder>();
    }
    this.purchaseOrders.add(purchaseOrdersItem);
    return this;
  }

   /**
   * Get purchaseOrders
   * @return purchaseOrders
  **/
  @ApiModelProperty(value = "")
  public List<PurchaseOrder> getPurchaseOrders() {
    return purchaseOrders;
  }

  public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
    this.purchaseOrders = purchaseOrders;
  }

  public FSOrder rewardZone(RewardZone rewardZone) {
    this.rewardZone = rewardZone;
    return this;
  }

   /**
   * Get rewardZone
   * @return rewardZone
  **/
  @ApiModelProperty(value = "")
  public RewardZone getRewardZone() {
    return rewardZone;
  }

  public void setRewardZone(RewardZone rewardZone) {
    this.rewardZone = rewardZone;
  }

  public FSOrder shippingModel(ShippingModel shippingModel) {
    this.shippingModel = shippingModel;
    return this;
  }

   /**
   * Get shippingModel
   * @return shippingModel
  **/
  @ApiModelProperty(value = "")
  public ShippingModel getShippingModel() {
    return shippingModel;
  }

  public void setShippingModel(ShippingModel shippingModel) {
    this.shippingModel = shippingModel;
  }

  public FSOrder shippingOrders(List<ShippingOrder> shippingOrders) {
    this.shippingOrders = shippingOrders;
    return this;
  }

  public FSOrder addShippingOrdersItem(ShippingOrder shippingOrdersItem) {
    if (this.shippingOrders == null) {
      this.shippingOrders = new ArrayList<ShippingOrder>();
    }
    this.shippingOrders.add(shippingOrdersItem);
    return this;
  }

   /**
   * Get shippingOrders
   * @return shippingOrders
  **/
  @ApiModelProperty(value = "")
  public List<ShippingOrder> getShippingOrders() {
    return shippingOrders;
  }

  public void setShippingOrders(List<ShippingOrder> shippingOrders) {
    this.shippingOrders = shippingOrders;
  }

  public FSOrder status(Status status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")
  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public FSOrder webOrderRefId(String webOrderRefId) {
    this.webOrderRefId = webOrderRefId;
    return this;
  }

   /**
   * Get webOrderRefId
   * @return webOrderRefId
  **/
  @ApiModelProperty(value = "")
  public String getWebOrderRefId() {
    return webOrderRefId;
  }

  public void setWebOrderRefId(String webOrderRefId) {
    this.webOrderRefId = webOrderRefId;
  }

  public FSOrder webOrderCreationDate(OffsetDateTime webOrderCreationDate) {
    this.webOrderCreationDate = webOrderCreationDate;
    return this;
  }

   /**
   * Get webOrderCreationDate
   * @return webOrderCreationDate
  **/
  @ApiModelProperty(value = "")
  public OffsetDateTime getWebOrderCreationDate() {
    return webOrderCreationDate;
  }

  public void setWebOrderCreationDate(OffsetDateTime webOrderCreationDate) {
    this.webOrderCreationDate = webOrderCreationDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FSOrder fsOrder = (FSOrder) o;
    return Objects.equals(this.id, fsOrder.id) &&
        Objects.equals(this.bundles, fsOrder.bundles) &&
        Objects.equals(this.customer, fsOrder.customer) &&
        Objects.equals(this.fsOrderLines, fsOrder.fsOrderLines) &&
        Objects.equals(this.ipAddress, fsOrder.ipAddress) &&
        Objects.equals(this.paymentMethodInfo, fsOrder.paymentMethodInfo) &&
        Objects.equals(this.purchaseOrders, fsOrder.purchaseOrders) &&
        Objects.equals(this.rewardZone, fsOrder.rewardZone) &&
        Objects.equals(this.shippingModel, fsOrder.shippingModel) &&
        Objects.equals(this.shippingOrders, fsOrder.shippingOrders) &&
        Objects.equals(this.status, fsOrder.status) &&
        Objects.equals(this.webOrderRefId, fsOrder.webOrderRefId) &&
        Objects.equals(this.webOrderCreationDate, fsOrder.webOrderCreationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bundles, customer, fsOrderLines, ipAddress, paymentMethodInfo, purchaseOrders, rewardZone, shippingModel, shippingOrders, status, webOrderRefId, webOrderCreationDate);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FSOrder {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bundles: ").append(toIndentedString(bundles)).append("\n");
    sb.append("    customer: ").append(toIndentedString(customer)).append("\n");
    sb.append("    fsOrderLines: ").append(toIndentedString(fsOrderLines)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    paymentMethodInfo: ").append(toIndentedString(paymentMethodInfo)).append("\n");
    sb.append("    purchaseOrders: ").append(toIndentedString(purchaseOrders)).append("\n");
    sb.append("    rewardZone: ").append(toIndentedString(rewardZone)).append("\n");
    sb.append("    shippingModel: ").append(toIndentedString(shippingModel)).append("\n");
    sb.append("    shippingOrders: ").append(toIndentedString(shippingOrders)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    webOrderRefId: ").append(toIndentedString(webOrderRefId)).append("\n");
    sb.append("    webOrderCreationDate: ").append(toIndentedString(webOrderCreationDate)).append("\n");
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

