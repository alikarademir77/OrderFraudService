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
@Table(name="FRAUDREQUESTSTATUSHISTORYDETAILS", schema="ORDER_FRAUD")
public class FraudRequestStatusHistoryDetail implements Serializable {

	@Id
	@Column(name = "FRAUDREQUESTSTATUSHISTORYID")
	private long fraudRequestStatusHistoryId;

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

	//bi-directional many-to-one association to Fraudrequeststatushistory
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FRAUDREQUESTSTATUSHISTORYID", insertable=false, updatable=false)
	private FraudRequestStatusHistory fraudRequestStatusHistory;

	public FraudRequestStatusHistoryDetail() {
	}

	public long getFraudRequestStatusHistoryId() {
		return fraudRequestStatusHistoryId;
	}

	public void setFraudRequestStatusHistoryId(long fraudRequestStatusHistoryId) {
		this.fraudRequestStatusHistoryId = fraudRequestStatusHistoryId;
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

	public FraudRequestStatusHistory getFraudRequestStatusHistory() {
		return fraudRequestStatusHistory;
	}

	public void setFraudRequestStatusHistory(FraudRequestStatusHistory fraudRequestStatusHistory) {
		this.fraudRequestStatusHistory = fraudRequestStatusHistory;
	}
	
}