/**
 * 
 */
package com.bbyc.orders.messaging;

import java.util.Calendar;

/**
 * @author akaradem
 *
 */
public class MessagingEvent {
	private EventTypes type;
	private String orderNumer;
	private String purchaseOrderNumer;
	private String requestVersion;
	private Calendar messageCreationDate;
	public EventTypes getType() {
		return type;
	}
	public void setType(EventTypes type) {
		this.type = type;
	}
	public String getOrderNumer() {
		return orderNumer;
	}
	public void setOrderNumer(String orderNumer) {
		this.orderNumer = orderNumer;
	}
	public String getPurchaseOrderNumer() {
		return purchaseOrderNumer;
	}
	public void setPurchaseOrderNumer(String purchaseOrderNumer) {
		this.purchaseOrderNumer = purchaseOrderNumer;
	}
	public String getRequestVersion() {
		return requestVersion;
	}
	public void setRequestVersion(String requestVersion) {
		this.requestVersion = requestVersion;
	}
	public Calendar getMessageCreationDate() {
		return messageCreationDate;
	}
	public void setMessageCreationDate(Calendar messageCreationDate) {
		this.messageCreationDate = messageCreationDate;
	}
	@Override
	public String toString() {
		return "MessagingEvent [type=" + type + ", orderNumer=" + orderNumer + ", purchaseOrderNumer="
				+ purchaseOrderNumer + ", requestVersion=" + requestVersion + ", messageCreationDate="
				+ messageCreationDate.getTime() + "]";
	}
	
}

