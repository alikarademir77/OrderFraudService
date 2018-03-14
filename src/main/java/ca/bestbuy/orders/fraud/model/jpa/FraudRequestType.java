package ca.bestbuy.orders.fraud.model.jpa;

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

	public RequestTypes getRequestTypeCode() {
		return requestTypeCode;
	}

	public void setRequestTypeCode(RequestTypes requestTypeCode) {
		this.requestTypeCode = requestTypeCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getRequestTypeDescription() {
		return requestTypeDescription;
	}

	public void setRequestTypeDescription(String requestTypeDescription) {
		this.requestTypeDescription = requestTypeDescription;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	public static enum RequestTypes {
		FRAUD_CHECK, ORDER_CANCEL;
	}
}