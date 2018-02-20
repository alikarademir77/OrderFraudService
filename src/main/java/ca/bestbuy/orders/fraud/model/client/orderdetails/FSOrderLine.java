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


package ca.bestbuy.orders.fraud.model.client.orderdetails;

import java.util.Objects;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * FSOrderLine
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-19T01:15:20.679-08:00")
public class FSOrderLine {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("bundle")
  private Bundle bundle = null;

  @JsonProperty("etaDate")
  private DateTime etaDate = null;

  @JsonProperty("itemCharge")
  private ItemCharge itemCharge = null;

  @JsonProperty("lineNumber")
  private String lineNumber = null;

  @JsonProperty("product")
  private Product product = null;

  @JsonProperty("qtyCancelled")
  private Integer qtyCancelled = null;

  @JsonProperty("qtyOnSpecialOrder")
  private Integer qtyOnSpecialOrder = null;

  @JsonProperty("qtyOrdered")
  private Integer qtyOrdered = null;

  @JsonProperty("releaseDate")
  private DateTime releaseDate = null;

  @JsonProperty("status")
  private FSOrderLineStatus status = null;

  @JsonProperty("uom")
  private String uom = null;

  public FSOrderLine id(String id) {
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

  public FSOrderLine bundle(Bundle bundle) {
    this.bundle = bundle;
    return this;
  }

   /**
   * Get bundle
   * @return bundle
  **/
  @ApiModelProperty(value = "")
  public Bundle getBundle() {
    return bundle;
  }

  public void setBundle(Bundle bundle) {
    this.bundle = bundle;
  }

  public FSOrderLine etaDate(DateTime etaDate) {
    this.etaDate = etaDate;
    return this;
  }

   /**
   * Get etaDate
   * @return etaDate
  **/
  @ApiModelProperty(value = "")
  public DateTime getEtaDate() {
    return etaDate;
  }

  public void setEtaDate(DateTime etaDate) {
    this.etaDate = etaDate;
  }

  public FSOrderLine itemCharge(ItemCharge itemCharge) {
    this.itemCharge = itemCharge;
    return this;
  }

   /**
   * Get itemCharge
   * @return itemCharge
  **/
  @ApiModelProperty(value = "")
  public ItemCharge getItemCharge() {
    return itemCharge;
  }

  public void setItemCharge(ItemCharge itemCharge) {
    this.itemCharge = itemCharge;
  }

  public FSOrderLine lineNumber(String lineNumber) {
    this.lineNumber = lineNumber;
    return this;
  }

   /**
   * Get lineNumber
   * @return lineNumber
  **/
  @ApiModelProperty(value = "")
  public String getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(String lineNumber) {
    this.lineNumber = lineNumber;
  }

  public FSOrderLine product(Product product) {
    this.product = product;
    return this;
  }

   /**
   * Get product
   * @return product
  **/
  @ApiModelProperty(value = "")
  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public FSOrderLine qtyCancelled(Integer qtyCancelled) {
    this.qtyCancelled = qtyCancelled;
    return this;
  }

   /**
   * Get qtyCancelled
   * @return qtyCancelled
  **/
  @ApiModelProperty(value = "")
  public Integer getQtyCancelled() {
    return qtyCancelled;
  }

  public void setQtyCancelled(Integer qtyCancelled) {
    this.qtyCancelled = qtyCancelled;
  }

  public FSOrderLine qtyOnSpecialOrder(Integer qtyOnSpecialOrder) {
    this.qtyOnSpecialOrder = qtyOnSpecialOrder;
    return this;
  }

   /**
   * Get qtyOnSpecialOrder
   * @return qtyOnSpecialOrder
  **/
  @ApiModelProperty(value = "")
  public Integer getQtyOnSpecialOrder() {
    return qtyOnSpecialOrder;
  }

  public void setQtyOnSpecialOrder(Integer qtyOnSpecialOrder) {
    this.qtyOnSpecialOrder = qtyOnSpecialOrder;
  }

  public FSOrderLine qtyOrdered(Integer qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
    return this;
  }

   /**
   * Get qtyOrdered
   * @return qtyOrdered
  **/
  @ApiModelProperty(value = "")
  public Integer getQtyOrdered() {
    return qtyOrdered;
  }

  public void setQtyOrdered(Integer qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
  }

  public FSOrderLine releaseDate(DateTime releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }

   /**
   * Get releaseDate
   * @return releaseDate
  **/
  @ApiModelProperty(value = "")
  public DateTime getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(DateTime releaseDate) {
    this.releaseDate = releaseDate;
  }

  public FSOrderLine status(FSOrderLineStatus status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")
  public FSOrderLineStatus getStatus() {
    return status;
  }

  public void setStatus(FSOrderLineStatus status) {
    this.status = status;
  }

  public FSOrderLine uom(String uom) {
    this.uom = uom;
    return this;
  }

   /**
   * Get uom
   * @return uom
  **/
  @ApiModelProperty(value = "")
  public String getUom() {
    return uom;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FSOrderLine fsOrderLine = (FSOrderLine) o;
    return Objects.equals(this.id, fsOrderLine.id) &&
        Objects.equals(this.bundle, fsOrderLine.bundle) &&
        Objects.equals(this.etaDate, fsOrderLine.etaDate) &&
        Objects.equals(this.itemCharge, fsOrderLine.itemCharge) &&
        Objects.equals(this.lineNumber, fsOrderLine.lineNumber) &&
        Objects.equals(this.product, fsOrderLine.product) &&
        Objects.equals(this.qtyCancelled, fsOrderLine.qtyCancelled) &&
        Objects.equals(this.qtyOnSpecialOrder, fsOrderLine.qtyOnSpecialOrder) &&
        Objects.equals(this.qtyOrdered, fsOrderLine.qtyOrdered) &&
        Objects.equals(this.releaseDate, fsOrderLine.releaseDate) &&
        Objects.equals(this.status, fsOrderLine.status) &&
        Objects.equals(this.uom, fsOrderLine.uom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bundle, etaDate, itemCharge, lineNumber, product, qtyCancelled, qtyOnSpecialOrder, qtyOrdered, releaseDate, status, uom);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FSOrderLine {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bundle: ").append(toIndentedString(bundle)).append("\n");
    sb.append("    etaDate: ").append(toIndentedString(etaDate)).append("\n");
    sb.append("    itemCharge: ").append(toIndentedString(itemCharge)).append("\n");
    sb.append("    lineNumber: ").append(toIndentedString(lineNumber)).append("\n");
    sb.append("    product: ").append(toIndentedString(product)).append("\n");
    sb.append("    qtyCancelled: ").append(toIndentedString(qtyCancelled)).append("\n");
    sb.append("    qtyOnSpecialOrder: ").append(toIndentedString(qtyOnSpecialOrder)).append("\n");
    sb.append("    qtyOrdered: ").append(toIndentedString(qtyOrdered)).append("\n");
    sb.append("    releaseDate: ").append(toIndentedString(releaseDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    uom: ").append(toIndentedString(uom)).append("\n");
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

