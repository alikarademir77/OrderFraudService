package ca.bestbuy.orders.fraud.model.jpa;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import ca.bestbuy.orders.fraud.model.jpa.statemachine.FraudStatusEvents;
import ca.bestbuy.orders.fraud.model.jpa.statemachine.FraudStatusStateMachineConfig;
import lombok.AccessLevel;
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
@TableGenerator(name = "orderFraudIdGenerator",  schema="ORDER_FRAUD", table = "ID_GENERATOR", pkColumnName = "GENERATED_NAME", valueColumnName = "GENERATED_VALUE", pkColumnValue="FRAUD_RQST_STATUS_HSTRY_ID", allocationSize=10)
@Entity
@Access(AccessType.FIELD)
@Table(name = "FRAUD_RQST_STATUS_HSTRY", schema="ORDER_FRAUD")
@EqualsAndHashCode(callSuper=true, exclude={"fraudRequestStatusHistoryDetail"})
@ToString(callSuper=true, exclude={"fraudRequestStatusHistoryDetail"})
public class FraudRequestStatusHistory extends OrderFraudBaseEntity {

	@Accessors(chain=true)
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="orderFraudIdGenerator")
	@Column(name = "FRAUD_RQST_STATUS_HSTRY_ID")
	private long fraudRequestStatusHistoryId;

	@Accessors(chain=true)
	@Getter
	@Setter
	//bi-directional many-to-one association to FraudRequestStatusHistoryDetail
	@OneToOne(mappedBy="fraudRequestStatusHistory", cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private FraudRequestStatusHistoryDetail fraudRequestStatusHistoryDetail;

	@Accessors(chain=true)
	@Getter
	@Setter
	//bi-directional many-to-one association to FraudRequest
	@ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="FRAUD_RQST_ID")
	private FraudRequest fraudRequest;

	@Access(AccessType.PROPERTY)
	@Getter(AccessLevel.PRIVATE)
	@Enumerated(EnumType.STRING)
	@Column(name = "FRAUD_STATUS_CODE")
	private FraudStatusCodes fraudStatusCode;

	@Getter
	@Transient
    private StateMachine<FraudStatusCodes, FraudStatusEvents> fraudStatusStateMachine;
	
	@SuppressWarnings("unchecked")
	public FraudRequestStatusHistory() {
		AnnotationConfigApplicationContext context = null;
		try{
			context = new AnnotationConfigApplicationContext(FraudStatusStateMachineConfig.class);
			StateMachineFactory<FraudStatusCodes, FraudStatusEvents> factory = context.getBean("FraudStatusStateMachine", StateMachineFactory.class);
			fraudStatusStateMachine = factory.getStateMachine("FraudRequestStatusHistorySM");
			this.fraudStatusCode = fraudStatusStateMachine.getState().getId();
			fraudStatusStateMachine.addStateListener(new StateMachineEventListener(this));
		}finally {
			if(context!=null){
				context.close();
			}
		}
	}
	
	@PostLoad
	public void postLoad() {
		StateMachine<FraudStatusCodes, FraudStatusEvents> stateMachine = this.getFraudStatusStateMachine();
		handleStateForFraudStatus(this.getFraudStatusCode(), stateMachine);
	}
	
	private static class StateMachineEventListener extends StateMachineListenerAdapter<FraudStatusCodes, FraudStatusEvents> {
		
		private final FraudRequestStatusHistory entity;
		public StateMachineEventListener(FraudRequestStatusHistory entity){
			this.entity = entity;
		}
		@Override
		public void stateEntered(State<FraudStatusCodes, FraudStatusEvents> state) {
			entity.fraudStatusCode = state.getId();
		}
	}

	@SuppressWarnings("unused")
	//Used by Spring Framework
	private void setFraudStatusCode(FraudStatusCodes fraudStatusCode) {
		if((fraudStatusCode!=null)&&(this.getFraudStatusStateMachine().getState().getId()!=fraudStatusCode)){
			handleStateForFraudStatus(this.getFraudStatusCode(), this.getFraudStatusStateMachine());
		}
		this.fraudStatusCode = fraudStatusCode;
	}

	
	
}