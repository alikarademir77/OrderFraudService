package ca.bestbuy.orders.fraud.model.jpa;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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
@Table(name="FRAUDREQUESTTYPES", schema="ORDER_FRAUD")
@Accessors(chain=true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FraudRequestType implements Serializable {

	@Id
	@Column(name = "REQUESTTYPECODE")
	@Enumerated(EnumType.STRING)
	private RequestTypes requestTypeCode;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDATE")
	private Date createDate;

	@Column(name = "CREATEUSER")
	private String createUser;

	@Column(name = "REQUESTTYPEDESCRIPTION")
	private String requestTypeDescription;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDATE")
	private Date updateDate;

	@Column(name = "UPDATEUSER")
	private String updateUser;

	public FraudRequestType() {
	}

	public static enum RequestTypes {
		FRAUD_CHECK, ORDER_CANCEL;
	}
}