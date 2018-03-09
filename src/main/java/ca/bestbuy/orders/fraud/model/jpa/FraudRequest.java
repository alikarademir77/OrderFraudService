package ca.bestbuy.orders.fraud.model.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the FRAUDREQUEST database table.
 * 
 */
@Entity
@NamedQuery(name="FraudRequest.findAll", query="SELECT f FROM FraudRequest f")
@Table(name = "FRAUDREQUEST")
public class FraudRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FRAUDREQUEST_FRAUDREQUESTID_GENERATOR", sequenceName="FRAUDREQUEST_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FRAUDREQUEST_FRAUDREQUESTID_GENERATOR")
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
	@ManyToOne(cascade={CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="REQUESTTYPECODE")
	private FraudRequestType fraudRequestType;

	//uni-directional many-to-one association to FraudStatus
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FRAUDSTATUSCODE")
	private FraudStatus fraudStatus;

	//bi-directional many-to-one association to FraudRquestHistory
	@OneToMany(mappedBy = "fraudRequest")
	private List<FraudRequestHistory> fraudRequestHistory;

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

	public List<FraudRequestHistory> getFraudRequestHistory() {
		return this.fraudRequestHistory;
	}

	public void setFraudRequestHistory(List<FraudRequestHistory> fraudRequestHistory) {
		this.fraudRequestHistory = fraudRequestHistory;
	}

	public FraudRequestHistory addFraudrequesthistory(FraudRequestHistory fraudRequestHistory) {
		getFraudRequestHistory().add(fraudRequestHistory);
		fraudRequestHistory.setFraudRequest(this);

		return fraudRequestHistory;
	}

	public FraudRequestHistory removeFraudRequestHistory(FraudRequestHistory fraudRequestHistory) {
		getFraudRequestHistory().remove(fraudRequestHistory);
		fraudRequestHistory.setFraudRequest(null);

		return fraudRequestHistory;
	}

}