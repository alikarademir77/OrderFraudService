package ca.bestbuy.orders.fraud.model.jpa;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import ca.bestbuy.orders.messaging.EventTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * 
 *	@author akaradem
 * 
 * The persistent class for the FRAUDREQUESTTYPES database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Access(AccessType.FIELD)
@Table(name="FRAUD_RQST_TYPES", schema="ORDER_FRAUD")
@Accessors(chain=true)
@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class FraudRequestType extends OrderFraudBaseEntity {
	@Id
	@Column(name = "REQUEST_TYPE_CODE")
	@Enumerated(EnumType.STRING)
	private RequestTypeCodes requestTypeCode;

	@Column(name = "REQUEST_TYPE_DESCRIPTION")
	private String requestTypeDescription;

	public FraudRequestType() {
	}

	/**
	 * @author akaradem
	 *
	 */
	public static enum RequestTypeCodes {
		
		FRAUD_CHECK(EventTypes.FraudCheck), ORDER_CANCEL(EventTypes.OrderCancel);
		
		private final EventTypes eventType;
		private RequestTypeCodes(EventTypes eventType){
			this.eventType = eventType;
		}
		
		/**
		 * @param eventType
		 * @return
		 */
		public static RequestTypeCodes findByEventType(EventTypes eventType){
			for(RequestTypeCodes curr : values()){
				if(curr.eventType == eventType){
					return curr;
				}
			}
			return null;
		}
	}
}