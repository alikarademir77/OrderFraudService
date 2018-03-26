package ca.bestbuy.orders.fraud.model.jpa;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

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
@EntityListeners(FraudRequestStatusHistoryEntityListener.class)
@SuppressWarnings("serial")
@TableGenerator(name = "orderFraudIdGenerator",  schema="ORDER_FRAUD", table = "ID_GENERATOR", pkColumnName = "GENERATED_NAME", valueColumnName = "GENERATED_VALUE", pkColumnValue="FRAUD_RQST_STATUS_HSTRY_ID")
@Entity
@Access(AccessType.FIELD)
@Table(name = "FRAUD_RQST_STATUS_HSTRY", schema="ORDER_FRAUD")
@Accessors(chain=true)
@EqualsAndHashCode(callSuper=true, exclude={"fraudRequestStatusHistoryDetail"})
@ToString(callSuper=true, exclude={"fraudRequestStatusHistoryDetail"})
public class FraudRequestStatusHistory extends OrderFraudBaseEntity {

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="orderFraudIdGenerator")
	@Column(name = "FRAUD_RQST_STATUS_HSTRY_ID")
	private long fraudRequestStatusHistoryId;

	//bi-directional many-to-one association to FraudRequestStatusHistoryDetail
	@Getter
	@Setter
	@OneToOne(mappedBy="fraudRequestStatusHistory", cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private FraudRequestStatusHistoryDetail fraudRequestStatusHistoryDetail;

	//bi-directional many-to-one association to FraudRequest
	@Getter
	@Setter
	@ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="FRAUD_RQST_ID")
	private FraudRequest fraudRequest;

	@Enumerated(EnumType.STRING)
	@Column(name = "FRAUD_STATUS_CODE")
	FraudStatusCodes fraudStatusCode;//The field is declared as package access level so FraudRequestStatusHistoryEntityListener can set it in prePersist event

	@Getter
	@Transient
    private StateMachine<FraudStatusCodes, FraudStatusEvents> fraudStatusStateMachine;
	
	@SuppressWarnings("unchecked")
	public FraudRequestStatusHistory() {
		AnnotationConfigApplicationContext context = null;
		try{
			context = new AnnotationConfigApplicationContext(FraudStatusStateMachineConfig.class);
			StateMachineFactory<FraudStatusCodes, FraudStatusEvents> factory = context.getBean(StateMachineFactory.class);
			fraudStatusStateMachine = factory.getStateMachine("FraudRequestStatusHistorySM");
			fraudStatusStateMachine.start();
		}finally {
			if(context!=null){
				context.close();
			}
		}
	}

}