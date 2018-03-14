package ca.bestbuy.orders.fraud.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 
 *	@author akaradem
 * 
 * The persistent class for the FRAUDREQUESTHISTORYDETAILS database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Access(AccessType.FIELD)
@Table(name="FRAUDREQUESTHISTORYDETAILS", schema="ORDER_FRAUD")
public class FraudRequestHistoryDetail implements Serializable {

	@Id
	@Column(name = "FRAUDREQUESTHISTORYID")
	private long fraudRequestHistoryId;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDATE")
	private Date createDate;

	@Column(name = "CREATEUSER")
	private String createUser;

	@Lob
	@Column(name = "TASREQUEST")
	private String tasRequest;

	@Lob
	@Column(name = "TASRESPONSE")
	private String tasResponse;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDATE")
	private Date updateDate;

	@Column(name = "UPDATEUSER")
	private String updateUser;

	//bi-directional many-to-one association to Fraudrequesthistory
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FRAUDREQUESTHISTORYID", insertable=false, updatable=false)
	private FraudRequestHistory fraudRequestHistory;

	public FraudRequestHistoryDetail() {
	}

	public long getFraudRequestHistoryId() {
		return fraudRequestHistoryId;
	}

	public void setFraudRequestHistoryId(long fraudRequestHistoryId) {
		this.fraudRequestHistoryId = fraudRequestHistoryId;
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

	public String getTasRequest() {
		return tasRequest;
	}

	public void setTasRequest(String tasRequest) {
		this.tasRequest = tasRequest;
	}

	public String getTasResponse() {
		return tasResponse;
	}

	public void setTasResponse(String tasResponse) {
		this.tasResponse = tasResponse;
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

	public FraudRequestHistory getFraudRequestHistory() {
		return fraudRequestHistory;
	}

	public void setFraudRequestHistory(FraudRequestHistory fraudRequestHistory) {
		this.fraudRequestHistory = fraudRequestHistory;
	}
	
}