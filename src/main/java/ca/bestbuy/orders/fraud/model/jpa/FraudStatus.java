package ca.bestbuy.orders.fraud.model.jpa;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * 
 *	@author akaradem
 * 
 * The persistent class for the FRAUDSTATUSES database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Access(AccessType.FIELD)
@Table(name="FRAUDSTATUSES", schema="ORDER_FRAUD")
@Accessors(chain=true)
@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class FraudStatus extends OrderFraudBaseEntity {

	@Id
	@Column(name = "FRAUDSTATUSCODE")
	@Enumerated(EnumType.STRING)
	private FraudStatusCodes fraudStatusCode;

	@Column(name = "FRAUDSTATUSDESCRIPTION")
	private String fraudStatusDescription;

	public FraudStatus() {
	}

	public static enum FraudStatusCodes{
		INITIAL_REQUEST, DECISION_MADE, PENDING_REVIEW, CANCELLED;
	}
	
}