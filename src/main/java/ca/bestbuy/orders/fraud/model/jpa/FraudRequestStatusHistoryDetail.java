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
@Accessors(chain=true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FRAUDREQUESTSTATUSHISTORYID", insertable = false, updatable = false)
	private FraudRequestStatusHistory fraudRequestStatusHistory;

	public FraudRequestStatusHistoryDetail() {
	}
}