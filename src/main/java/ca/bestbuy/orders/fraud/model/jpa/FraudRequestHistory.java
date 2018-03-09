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
 * The persistent class for the FRAUDREQUESTHISTORY database table.
 * 
 */
@Entity
@NamedQuery(name="FraudRequestHistory.findAll", query="SELECT f FROM FraudRequestHistory f")
@Table(name = "FRAUDREQUESTHISTORY")
public class FraudRequestHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FRAUDREQUESTHISTORY_FRAUDREQUESTHISTORYID_GENERATOR", sequenceName="FRAUDREQUESTHISTORY_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FRAUDREQUESTHISTORY_FRAUDREQUESTHISTORYID_GENERATOR")
	@Column(name = "FRAUDREQUESTHISTORYID")
	private long fraudRequestHistoryId;

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

	//bi-directional many-to-one association to FraudRequestHistoryDetail
	@OneToMany(mappedBy="fraudRequestHistory", cascade={CascadeType.ALL})
	private List<FraudRequestHistoryDetail> fraudRequestHistoryDetail;

	//bi-directional many-to-one association to FraudRequest
	@ManyToOne(cascade={CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="FRAUDREQUESTID")
	private FraudRequest fraudRequest;

	//uni-directional many-to-one association to FraudRequestType
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REQUESTTYPECODE")
	private FraudRequestType fraudRequestType;

	//uni-directional many-to-one association to FraudStatus
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FRAUDSTATUSCODE")
	private FraudStatus fraudStatus;

	public FraudRequestHistory() {
	}

	public long getFraudRequestHistoryId() {
		return this.fraudRequestHistoryId;
	}

	public void setFraudRequestHistoryId(long fraudRequestHistoryId) {
		this.fraudRequestHistoryId = fraudRequestHistoryId;
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

	public List<FraudRequestHistoryDetail> getFraudRequestHistoryDetail() {
		return this.fraudRequestHistoryDetail;
	}

	public void setFraudRequestHistoryDetail(List<FraudRequestHistoryDetail> fraudRequestHistoryDetail) {
		this.fraudRequestHistoryDetail = fraudRequestHistoryDetail;
	}

	public FraudRequestHistoryDetail addFraudRequestHistoryDetail(FraudRequestHistoryDetail fraudRequestHistoryDetail) {
		getFraudRequestHistoryDetail().add(fraudRequestHistoryDetail);
		fraudRequestHistoryDetail.setFraudRequestHistory(this);

		return fraudRequestHistoryDetail;
	}

	public FraudRequestHistoryDetail removeFraudRequesthHistoryDetail(FraudRequestHistoryDetail fraudRequestHistoryDetail) {
		getFraudRequestHistoryDetail().remove(fraudRequestHistoryDetail);
		fraudRequestHistoryDetail.setFraudRequestHistory(null);

		return fraudRequestHistoryDetail;
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