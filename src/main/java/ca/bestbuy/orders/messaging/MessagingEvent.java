/**
 * 
 */
package ca.bestbuy.orders.messaging;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akaradem
 *
 */
@Accessors(chain=true)
@Getter
@EqualsAndHashCode
@ToString
public final class MessagingEvent {
	private final EventTypes type;
	private final String orderNumber;
	private final String purchaseOrderNumber;
	private final String requestVersion;
	private final Date messageCreationDate;
	
	@JsonCreator
	public MessagingEvent(
			@JsonProperty("type") 
			final EventTypes type,
			@JsonProperty("orderNumber")
			final String orderNumber,
			@JsonProperty("purchaseOrderNumber")
			final String purchaseOrderNumber,
			@JsonProperty("requestVersion")
			final String requestVersion,
			@JsonProperty("messageCreationDate")
			@JsonFormat(pattern = "dd/mm/yyyy hh:mm:ss", timezone="UTC")
			final Date messageCreationDate){
		this.type = type;
		this.orderNumber = orderNumber;
		this.purchaseOrderNumber = purchaseOrderNumber;
		this.requestVersion = requestVersion;
		this.messageCreationDate = messageCreationDate;
	}
}

