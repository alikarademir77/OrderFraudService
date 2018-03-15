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
 * The persistent class for the FRAUDSTATUSES database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Access(AccessType.FIELD)
@Table(name="FRAUDSTATUSES", schema="ORDER_FRAUD")
public class FraudStatus implements Serializable {

	@Id
	@Column(name = "FRAUDSTATUSCODE")
	@Enumerated(EnumType.STRING)
	private FraudStatusCodes fraudStatusCode;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDATE")
	private Date createDate;

	@Column(name = "CREATEUSER")
	private String createUser;

	@Column(name = "FRAUDSTATUSDESCRIPTION")
	private String fraudStatusDescription;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDATE")
	private Date updateDate;

	@Column(name = "UPDATEUSER")
	private String updateUser;

	public FraudStatus() {
	}

	public FraudStatusCodes getFraudStatusCode() {
		return fraudStatusCode;
	}

	public void setFraudStatusCode(FraudStatusCodes fraudStatusCode) {
		this.fraudStatusCode = fraudStatusCode;
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

	public String getFraudStatusDescription() {
		return fraudStatusDescription;
	}

	public void setFraudStatusDescription(String fraudStatusDescription) {
		this.fraudStatusDescription = fraudStatusDescription;
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

	public static enum FraudStatusCodes{
		INITIAL_REQUEST, DECISION_MADE, PENDING_REVIEW, CANCELLED;
	}
	
}