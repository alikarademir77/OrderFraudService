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
import com.bbyc.orders.model.client.orderdetails.CreditCardInfo;
import com.bbyc.orders.model.client.orderdetails.GiftCardInfo;
import com.bbyc.orders.model.client.orderdetails.POSPaymentInfo;
import com.bbyc.orders.model.client.orderdetails.PayPalInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * PaymentMethodInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-13T17:36:04.633-08:00")
public class PaymentMethodInfo {
  @JsonProperty("creditCards")
  private List<CreditCardInfo> creditCards = null;

  @JsonProperty("giftCards")
  private List<GiftCardInfo> giftCards = null;

  @JsonProperty("payPal")
  private List<PayPalInfo> payPal = null;

  @JsonProperty("posPayments")
  private List<POSPaymentInfo> posPayments = null;

  public PaymentMethodInfo creditCards(List<CreditCardInfo> creditCards) {
    this.creditCards = creditCards;
    return this;
  }

  public PaymentMethodInfo addCreditCardsItem(CreditCardInfo creditCardsItem) {
    if (this.creditCards == null) {
      this.creditCards = new ArrayList<CreditCardInfo>();
    }
    this.creditCards.add(creditCardsItem);
    return this;
  }

   /**
   * Get creditCards
   * @return creditCards
  **/
  @ApiModelProperty(value = "")
  public List<CreditCardInfo> getCreditCards() {
    return creditCards;
  }

  public void setCreditCards(List<CreditCardInfo> creditCards) {
    this.creditCards = creditCards;
  }

  public PaymentMethodInfo giftCards(List<GiftCardInfo> giftCards) {
    this.giftCards = giftCards;
    return this;
  }

  public PaymentMethodInfo addGiftCardsItem(GiftCardInfo giftCardsItem) {
    if (this.giftCards == null) {
      this.giftCards = new ArrayList<GiftCardInfo>();
    }
    this.giftCards.add(giftCardsItem);
    return this;
  }

   /**
   * Get giftCards
   * @return giftCards
  **/
  @ApiModelProperty(value = "")
  public List<GiftCardInfo> getGiftCards() {
    return giftCards;
  }

  public void setGiftCards(List<GiftCardInfo> giftCards) {
    this.giftCards = giftCards;
  }

  public PaymentMethodInfo payPal(List<PayPalInfo> payPal) {
    this.payPal = payPal;
    return this;
  }

  public PaymentMethodInfo addPayPalItem(PayPalInfo payPalItem) {
    if (this.payPal == null) {
      this.payPal = new ArrayList<PayPalInfo>();
    }
    this.payPal.add(payPalItem);
    return this;
  }

   /**
   * Get payPal
   * @return payPal
  **/
  @ApiModelProperty(value = "")
  public List<PayPalInfo> getPayPal() {
    return payPal;
  }

  public void setPayPal(List<PayPalInfo> payPal) {
    this.payPal = payPal;
  }

  public PaymentMethodInfo posPayments(List<POSPaymentInfo> posPayments) {
    this.posPayments = posPayments;
    return this;
  }

  public PaymentMethodInfo addPosPaymentsItem(POSPaymentInfo posPaymentsItem) {
    if (this.posPayments == null) {
      this.posPayments = new ArrayList<POSPaymentInfo>();
    }
    this.posPayments.add(posPaymentsItem);
    return this;
  }

   /**
   * Get posPayments
   * @return posPayments
  **/
  @ApiModelProperty(value = "")
  public List<POSPaymentInfo> getPosPayments() {
    return posPayments;
  }

  public void setPosPayments(List<POSPaymentInfo> posPayments) {
    this.posPayments = posPayments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentMethodInfo paymentMethodInfo = (PaymentMethodInfo) o;
    return Objects.equals(this.creditCards, paymentMethodInfo.creditCards) &&
        Objects.equals(this.giftCards, paymentMethodInfo.giftCards) &&
        Objects.equals(this.payPal, paymentMethodInfo.payPal) &&
        Objects.equals(this.posPayments, paymentMethodInfo.posPayments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creditCards, giftCards, payPal, posPayments);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentMethodInfo {\n");
    
    sb.append("    creditCards: ").append(toIndentedString(creditCards)).append("\n");
    sb.append("    giftCards: ").append(toIndentedString(giftCards)).append("\n");
    sb.append("    payPal: ").append(toIndentedString(payPal)).append("\n");
    sb.append("    posPayments: ").append(toIndentedString(posPayments)).append("\n");
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

