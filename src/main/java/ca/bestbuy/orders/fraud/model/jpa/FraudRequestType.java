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
	@Column(name = "REQUESTTYPECODE")
	@Enumerated(EnumType.STRING)
	private RequestTypes requestTypeCode;

	@Column(name = "REQUESTTYPEDESCRIPTION")
	private String requestTypeDescription;

	public FraudRequestType() {
	}

	public static enum RequestTypes {
		FRAUD_CHECK, ORDER_CANCEL;
	}
}