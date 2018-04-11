/**
 * 
 */
package ca.bestbuy.orders.messaging;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
	private final String requestVersion;
	private final Date messageCreationDate;
	
	/**
	 * @param type
	 * @param orderNumber
	 * @param requestVersion
	 * @param messageCreationDate
	 */
	@JsonCreator
	public MessagingEvent(
			@JsonProperty("type") 
			final EventTypes type,
			@JsonProperty("orderNumber")
			final String orderNumber,
			@JsonProperty("requestVersion")
			final String requestVersion,
			@JsonProperty("messageCreationDate")
			@JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", timezone="UTC")
			final Date messageCreationDate){
		this.type = type;
		this.orderNumber = orderNumber;
		this.requestVersion = requestVersion;
		this.messageCreationDate = messageCreationDate;
	}
}

