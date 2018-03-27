package ca.bestbuy.orders.fraud.model.jpa;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ca.bestbuy.orders.fraud.model.internal.FraudAssesmentResult.FraudResponseStatusCodes;
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
@TableGenerator(name = "orderFraudIdGenerator",  schema="ORDER_FRAUD", table = "ID_GENERATOR", pkColumnName = "GENERATED_NAME", valueColumnName = "GENERATED_VALUE", pkColumnValue="FRAUD_RQST_STATUS_HSTRY_DTL_ID", allocationSize=10)
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
	@GeneratedValue(strategy=GenerationType.TABLE, generator="orderFraudIdGenerator")
	@Column(name = "FRAUD_RQST_STATUS_HSTRY_DTL_ID")
	private long fraudRequestStatusHistoryId;

	@Enumerated(EnumType.STRING)
	@Column(name = "FRAUD_RESPONSE_STATUS_CODE")
	private FraudResponseStatusCodes fraudResponseStatusCode;

	@Column(name = "TOTAL_FRAUD_SCORE")
	private String totalFraudScore;

	@Column(name = "RECOMMENDATION_CODE")
	private String recommendationCode;

	@Column(name = "ACCERTIFY_USER")
	private String accertifyUser;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ACCERTIFY_USER_ACTION_TIME")
	private Date accertifyUserActionTime;
	
	@Lob
	@Column(name = "TAS_REQUEST")
	private String tasRequest;

	@Lob
	@Column(name = "TAS_RESPONSE")
	private String tasResponse;
	
	//bi-directional one-to-one association to Fraudrequeststatushistory
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FRAUD_RQST_STATUS_HSTRY_ID")
	private FraudRequestStatusHistory fraudRequestStatusHistory;

	public FraudRequestStatusHistoryDetail() {
	}
}