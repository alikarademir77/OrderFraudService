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
 * GiftCardInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-21T17:00:44.098-08:00")
public class GiftCardInfo {
  @JsonProperty("active")
  private Boolean active = null;

  @JsonProperty("giftCardNumber")
  private String giftCardNumber = null;

  @JsonProperty("giftCardSecureCode")
  private String giftCardSecureCode = null;

  @JsonProperty("giftCardType")
  private String giftCardType = null;

  @JsonProperty("invoicedAmount")
  private String invoicedAmount = null;

  public GiftCardInfo active(Boolean active) {
    this.active = active;
    return this;
  }

   /**
   * Get active
   * @return active
  **/
  @ApiModelProperty(example = "false", value = "")
  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public GiftCardInfo giftCardNumber(String giftCardNumber) {
    this.giftCardNumber = giftCardNumber;
    return this;
  }

   /**
   * Get giftCardNumber
   * @return giftCardNumber
  **/
  @ApiModelProperty(value = "")
  public String getGiftCardNumber() {
    return giftCardNumber;
  }

  public void setGiftCardNumber(String giftCardNumber) {
    this.giftCardNumber = giftCardNumber;
  }

  public GiftCardInfo giftCardSecureCode(String giftCardSecureCode) {
    this.giftCardSecureCode = giftCardSecureCode;
    return this;
  }

   /**
   * Get giftCardSecureCode
   * @return giftCardSecureCode
  **/
  @ApiModelProperty(value = "")
  public String getGiftCardSecureCode() {
    return giftCardSecureCode;
  }

  public void setGiftCardSecureCode(String giftCardSecureCode) {
    this.giftCardSecureCode = giftCardSecureCode;
  }

  public GiftCardInfo giftCardType(String giftCardType) {
    this.giftCardType = giftCardType;
    return this;
  }

   /**
   * Get giftCardType
   * @return giftCardType
  **/
  @ApiModelProperty(value = "")
  public String getGiftCardType() {
    return giftCardType;
  }

  public void setGiftCardType(String giftCardType) {
    this.giftCardType = giftCardType;
  }

  public GiftCardInfo invoicedAmount(String invoicedAmount) {
    this.invoicedAmount = invoicedAmount;
    return this;
  }

   /**
   * Get invoicedAmount
   * @return invoicedAmount
  **/
  @ApiModelProperty(value = "")
  public String getInvoicedAmount() {
    return invoicedAmount;
  }

  public void setInvoicedAmount(String invoicedAmount) {
    this.invoicedAmount = invoicedAmount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCardInfo giftCardInfo = (GiftCardInfo) o;
    return Objects.equals(this.active, giftCardInfo.active) &&
        Objects.equals(this.giftCardNumber, giftCardInfo.giftCardNumber) &&
        Objects.equals(this.giftCardSecureCode, giftCardInfo.giftCardSecureCode) &&
        Objects.equals(this.giftCardType, giftCardInfo.giftCardType) &&
        Objects.equals(this.invoicedAmount, giftCardInfo.invoicedAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(active, giftCardNumber, giftCardSecureCode, giftCardType, invoicedAmount);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GiftCardInfo {\n");
    
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    giftCardNumber: ").append(toIndentedString(giftCardNumber)).append("\n");
    sb.append("    giftCardSecureCode: ").append(toIndentedString(giftCardSecureCode)).append("\n");
    sb.append("    giftCardType: ").append(toIndentedString(giftCardType)).append("\n");
    sb.append("    invoicedAmount: ").append(toIndentedString(invoicedAmount)).append("\n");
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

