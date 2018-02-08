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
 * PaymentMethodList
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-08T10:16:57.900-08:00")
public class PaymentMethodList {
  @JsonProperty("creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod")
  private List<Object> creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod = null;

  @JsonProperty("editable")
  private String editable = null;

  public PaymentMethodList creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod(List<Object> creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod) {
    this.creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod = creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod;
    return this;
  }

  public PaymentMethodList addCreditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethodItem(Object creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethodItem) {
    if (this.creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod == null) {
      this.creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod = new ArrayList<Object>();
    }
    this.creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod.add(creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethodItem);
    return this;
  }

   /**
   * Get creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod
   * @return creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod
  **/
  @ApiModelProperty(value = "")
  public List<Object> getCreditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod() {
    return creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod;
  }

  public void setCreditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod(List<Object> creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod) {
    this.creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod = creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod;
  }

  public PaymentMethodList editable(String editable) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentMethodList paymentMethodList = (PaymentMethodList) o;
    return Objects.equals(this.creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod, paymentMethodList.creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod) &&
        Objects.equals(this.editable, paymentMethodList.editable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod, editable);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentMethodList {\n");
    
    sb.append("    creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod: ").append(toIndentedString(creditCardPaymentMethodAndGiftCardPaymentMethodAndPayPalPaymentMethod)).append("\n");
    sb.append("    editable: ").append(toIndentedString(editable)).append("\n");
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

