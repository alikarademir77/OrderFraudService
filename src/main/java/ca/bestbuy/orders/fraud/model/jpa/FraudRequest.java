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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * 
 *	@author akaradem
 * 
 * The persistent class for the FRAUDREQUEST database table.
 * 
 */
@SuppressWarnings("serial")
@TableGenerator(name = "orderFraudIdGenerator", schema="ORDER_FRAUD", table = "ID_GENERATOR", pkColumnName = "GENERATED_NAME", valueColumnName = "GENERATED_VALUE", pkColumnValue="FRAUD_RQST_ID")
@Entity
@Access(AccessType.FIELD)
@Table(name = "FRAUD_RQST", schema="ORDER_FRAUD")
@Accessors(chain=true)
@Getter
@Setter
@EqualsAndHashCode(callSuper=true, exclude={"fraudRequestStatusHistory"})
@ToString(callSuper=true, exclude={"fraudRequestStatusHistory"})
public class FraudRequest extends OrderFraudBaseEntity implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="orderFraudIdGenerator" )
	@Column(name = "FRAUD_RQST_ID")
	private long fraudRequestId;

	@Temporal(TemporalType.DATE)
	@Column(name = "EVENT_DATE")
	private Date eventDate;

	@Column(name = "ORDER_NUMBER")
	private BigDecimal orderNumber;

	@Column(name = "REQUEST_VERSION")
	private BigDecimal requestVersion;

	//uni-directional many-to-one association to FraudRequestType
	@ManyToOne(cascade={CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name="REQUEST_TYPE_CODE")
	private FraudRequestType fraudRequestType;

	//uni-directional many-to-one association to FraudStatus
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FRAUD_STATUS_CODE")
	private FraudStatus fraudStatus;

	//bi-directional many-to-one association to FraudRquestHistory
	@OneToMany(mappedBy="fraudRequest", cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private List<FraudRequestStatusHistory> fraudRequestStatusHistory;

	public FraudRequest() {
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