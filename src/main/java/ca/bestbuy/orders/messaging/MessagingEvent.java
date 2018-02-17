/**
 * 
 */
package ca.bestbuy.orders.messaging;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akaradem
 *
 */
public class MessagingEvent {
	private EventTypes type;
	private String orderNumer;
	private String purchaseOrderNumer;
	private String requestVersion;
	private Date messageCreationDate;
	
	@JsonCreator
	public MessagingEvent(
			@JsonProperty("type") 
			final EventTypes type,
			@JsonProperty("orderNumer")
			final String orderNumber,
			@JsonProperty("purchaseOrderNumer")
			final String purchaseOrderNumer,
			@JsonProperty("requestVersion")
			final String requestVersion,
			@JsonProperty("messageCreationDate")
			@JsonFormat(pattern = "dd/mm/yyyy hh:mm:ss", timezone="UTC")
			final Date messageCreationDate){
		this.type = type;
		this.orderNumer = orderNumber;
		this.purchaseOrderNumer = purchaseOrderNumer;
		this.requestVersion = requestVersion;
		this.messageCreationDate = messageCreationDate;
	}
	
	public EventTypes getType() {
		return type;
	}
	public String getOrderNumer() {
		return orderNumer;
	}
	public String getPurchaseOrderNumer() {
		return purchaseOrderNumer;
	}
	public String getRequestVersion() {
		return requestVersion;
	}
	public Date getMessageCreationDate() {
		return messageCreationDate;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof MessagingEvent)) {
			return false;
		}
		MessagingEvent castOther = (MessagingEvent) other;
		return new EqualsBuilder().append(type, castOther.type).append(orderNumer, castOther.orderNumer)
				.append(purchaseOrderNumer, castOther.purchaseOrderNumer)
				.append(requestVersion, castOther.requestVersion)
				.append(messageCreationDate, castOther.messageCreationDate).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(type).append(orderNumer).append(purchaseOrderNumer).append(requestVersion)
				.append(messageCreationDate).toHashCode();
	}

	@Override
	public String toString() {
		return "MessagingEvent [type=" + type + ", orderNumer=" + orderNumer + ", purchaseOrderNumer="
				+ purchaseOrderNumer + ", requestVersion=" + requestVersion + ", messageCreationDate="
				+ messageCreationDate + "]";
	}
	
}

