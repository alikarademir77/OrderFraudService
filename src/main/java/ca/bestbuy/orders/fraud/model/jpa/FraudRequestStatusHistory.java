package ca.bestbuy.orders.fraud.model.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 
 *	@author akaradem
 * 
 * The persistent class for the FRAUDREQUESTHISTORY database table.
 * 
 */
@SuppressWarnings("serial")
@TableGenerator(name = "orderFraudIdGenerator",  schema="ORDER_FRAUD", table = "ID_GENERATOR", pkColumnName = "GENERATED_NAME", valueColumnName = "GENERATED_VALUE", pkColumnValue="FRAUDREQUESTSTATUSHISTORYID")
@Entity
@Access(AccessType.FIELD)
@Table(name = "FRAUDREQUESTSTATUSHISTORY", schema="ORDER_FRAUD")
public class FraudRequestStatusHistory implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="orderFraudIdGenerator")
	@Column(name = "FRAUDREQUESTSTATUSHISTORYID")
	private long fraudRequestStatusHistoryId;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDATE")
	private Date createDate;

	@Column(name = "CREATEUSER")
	private String createUser;

	@Column(name = "ORDERNUMBER")
	private BigDecimal orderNumber;

	@Column(name = "REQUESTVERSION")
	private BigDecimal requestVersion;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDATE")
	private Date updateDate;

	@Column(name = "UPDATEUSER")
	private String updateUser;

	//bi-directional many-to-one association to FraudRequestStatusHistoryDetail
	@OneToOne(mappedBy="fraudRequestStatusHistory", cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private FraudRequestStatusHistoryDetail fraudRequestStatusHistoryDetail;

	//bi-directional many-to-one association to FraudRequest

	@ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="FRAUDREQUESTID")
	private FraudRequest fraudRequest;

	//uni-directional many-to-one association to FraudRequestType
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="REQUESTTYPECODE")
	private FraudRequestType fraudRequestType;

	//uni-directional many-to-one association to FraudStatus
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FRAUDSTATUSCODE")
	private FraudStatus fraudStatus;

	public FraudRequestStatusHistory() {
	}

	public long getFraudRequestStatusHistoryId() {
		return this.fraudRequestStatusHistoryId;
	}

	public void setFraudRequestStatusHistoryId(long fraudRequestStatusHistoryId) {
		this.fraudRequestStatusHistoryId = fraudRequestStatusHistoryId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createdate) {
		this.createDate = createdate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public BigDecimal getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public BigDecimal getRequestVersion() {
		return this.requestVersion;
	}

	public void setRequestVersion(BigDecimal requestVersion) {
		this.requestVersion = requestVersion;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public FraudRequestStatusHistoryDetail getFraudRequestStatusHistoryDetail() {
		return this.fraudRequestStatusHistoryDetail;
	}

	public void setFraudRequestStatusHistoryDetail(FraudRequestStatusHistoryDetail fraudRequestStatusHistoryDetail) {
		this.fraudRequestStatusHistoryDetail = fraudRequestStatusHistoryDetail;
	}

	public FraudRequest getFraudRequest() {
		return this.fraudRequest;
	}

	public void setFraudRequest(FraudRequest fraudRequest) {
		this.fraudRequest = fraudRequest;
	}

	public FraudRequestType getFraudRequestType() {
		return this.fraudRequestType;
	}

	public void setFraudRequestType(FraudRequestType fraudRequestType) {
		this.fraudRequestType = fraudRequestType;
	}

	public FraudStatus getFraudStatus() {
		return this.fraudStatus;
	}

	public void setFraudStatus(FraudStatus fraudStatus) {
		this.fraudStatus = fraudStatus;
	}

}