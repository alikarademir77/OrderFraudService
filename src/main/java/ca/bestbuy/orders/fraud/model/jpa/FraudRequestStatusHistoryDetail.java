package ca.bestbuy.orders.fraud.model.jpa;

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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


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
@Table(name="FRAUD_RQST_STATUS_HSTRY_DTLS", schema="ORDER_FRAUD")
@Accessors(chain=true)
@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class FraudRequestStatusHistoryDetail extends OrderFraudBaseEntity {

	@Id
	@Column(name = "FRAUDREQUESTSTATUSHISTORYID")
	private long fraudRequestStatusHistoryId;

	@Lob
	@Column(name = "TASREQUEST")
	private String tasRequest;

	@Lob
	@Column(name = "TASRESPONSE")
	private String tasResponse;
	
	//bi-directional many-to-one association to Fraudrequeststatushistory
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FRAUDREQUESTSTATUSHISTORYID", insertable = false, updatable = false)
	private FraudRequestStatusHistory fraudRequestStatusHistory;

	public FraudRequestStatusHistoryDetail() {
	}
}