package ca.bestbuy.orders.fraud.model.jpa;

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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


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
@Table(name = "FRAUD_RQST_STATUS_HSTRY", schema="ORDER_FRAUD")
@Accessors(chain=true)
@Getter
@Setter
@EqualsAndHashCode(callSuper=true, exclude={"fraudRequestStatusHistoryDetail"})
@ToString(callSuper=true, exclude={"fraudRequestStatusHistoryDetail"})
public class FraudRequestStatusHistory extends OrderFraudBaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="orderFraudIdGenerator")
	@Column(name = "FRAUD_RQST_STATUS_HSTRY_ID")
	private long fraudRequestStatusHistoryId;

	//bi-directional many-to-one association to FraudRequestStatusHistoryDetail
	@OneToOne(mappedBy="fraudRequestStatusHistory", cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private FraudRequestStatusHistoryDetail fraudRequestStatusHistoryDetail;

	//bi-directional many-to-one association to FraudRequest

	@ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="FRAUD_RQST_ID")
	private FraudRequest fraudRequest;

	//uni-directional many-to-one association to FraudStatus
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FRAUD_STATUS_CODE")
	private FraudStatus fraudStatus;

	public FraudRequestStatusHistory() {
	}
}