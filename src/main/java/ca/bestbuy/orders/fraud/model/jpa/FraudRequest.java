package ca.bestbuy.orders.fraud.model.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 
 *	@author akaradem
 * 
 * The persistent class for the FRAUDREQUEST database table.
 * 
 */
@SuppressWarnings("serial")
@TableGenerator(name = "orderFraudIdGenerator", schema="ORDER_FRAUD", table = "ID_GENERATOR", pkColumnName = "GENERATED_NAME", valueColumnName = "GENERATED_VALUE", pkColumnValue="FRAUDREQUESTID")
@Entity
@Access(AccessType.FIELD)
@Table(name = "FRAUDREQUEST", schema="ORDER_FRAUD")
public class FraudRequest implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="orderFraudIdGenerator" )
	@Column(name = "FRAUDREQUESTID")
	private long fraudRequestId;

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

	//uni-directional many-to-one association to FraudRequestType
	@ManyToOne(cascade={CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name="REQUESTTYPECODE")
	private FraudRequestType fraudRequestType;

	//uni-directional many-to-one association to FraudStatus
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FRAUDSTATUSCODE")
	private FraudStatus fraudStatus;

	//bi-directional many-to-one association to FraudRquestHistory
	@OneToMany(mappedBy="fraudRequest", cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private List<FraudRequestStatusHistory> fraudRequestStatusHistory;

	public FraudRequest() {
	}

	public long getFraudRequestId() {
		return this.fraudRequestId;
	}

	public void setFraudRequestId(long fraudRequestId) {
		this.fraudRequestId = fraudRequestId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public List<FraudRequestStatusHistory> getFraudRequestStatusHistory() {
		return this.fraudRequestStatusHistory;
	}

	public void setFraudRequestStatusHistory(List<FraudRequestStatusHistory> fraudRequestStatusHistory) {
		this.fraudRequestStatusHistory = fraudRequestStatusHistory;
	}

	public FraudRequestStatusHistory addFraudRequestStatusHistory(FraudRequestStatusHistory fraudRequestStatusHistory) {
		getFraudRequestStatusHistory().add(fraudRequestStatusHistory);
		fraudRequestStatusHistory.setFraudRequest(this);

		return fraudRequestStatusHistory;
	}

	public FraudRequestStatusHistory removeFraudRequestStatusHistory(FraudRequestStatusHistory fraudRequestStatusHistory) {
		getFraudRequestStatusHistory().remove(fraudRequestStatusHistory);
		fraudRequestStatusHistory.setFraudRequest(null);

		return fraudRequestStatusHistory;
	}

}