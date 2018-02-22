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
 * Tax
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-21T17:00:44.098-08:00")
public class Tax {
  @JsonProperty("gst")
  private String gst = null;

  @JsonProperty("pst")
  private String pst = null;

  public Tax gst(String gst) {
    this.gst = gst;
    return this;
  }

   /**
   * Get gst
   * @return gst
  **/
  @ApiModelProperty(value = "")
  public String getGst() {
    return gst;
  }

  public void setGst(String gst) {
    this.gst = gst;
  }

  public Tax pst(String pst) {
    this.pst = pst;
    return this;
  }

   /**
   * Get pst
   * @return pst
  **/
  @ApiModelProperty(value = "")
  public String getPst() {
    return pst;
  }

  public void setPst(String pst) {
    this.pst = pst;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tax tax = (Tax) o;
    return Objects.equals(this.gst, tax.gst) &&
        Objects.equals(this.pst, tax.pst);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gst, pst);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Tax {\n");
    
    sb.append("    gst: ").append(toIndentedString(gst)).append("\n");
    sb.append("    pst: ").append(toIndentedString(pst)).append("\n");
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

