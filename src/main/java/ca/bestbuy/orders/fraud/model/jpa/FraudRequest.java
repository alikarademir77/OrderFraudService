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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
 * The persistent class for the FRAUDREQUEST database table.
 * 
 */
@SuppressWarnings("serial")
@TableGenerator(name = "orderFraudIdGenerator", schema="ORDER_FRAUD", table = "ID_GENERATOR", pkColumnName = "GENERATED_NAME", valueColumnName = "GENERATED_VALUE", pkColumnValue="FRAUD_RQST_ID", allocationSize=10)
@Entity
@Access(AccessType.FIELD)
@Table(name = "FRAUD_RQST", schema="ORDER_FRAUD")
@EqualsAndHashCode(callSuper=true, exclude={"fraudRequestStatusHistory"})
@ToString(callSuper=true, exclude={"fraudRequestStatusHistory"})
public class FraudRequest extends OrderFraudBaseEntity implements Serializable {

	@Accessors(chain=true)
	@Getter
	@Setter
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="orderFraudIdGenerator" )
	@Column(name = "FRAUD_RQST_ID")
	private long fraudRequestId;

	@Accessors(chain=true)
	@Getter
	@Setter
	@Temporal(TemporalType.DATE)
	@Column(name = "EVENT_DATE")
	private Date eventDate;

	@Accessors(chain=true)
	@Getter
	@Setter
	@Column(name = "ORDER_NUMBER")
	private BigDecimal orderNumber;

	@Accessors(chain=true)
	@Getter
	@Setter
	@Column(name = "REQUEST_VERSION")
	private Long requestVersion;

	@Accessors(chain=true)
	@Getter
	@Setter
	//uni-directional many-to-one association to FraudRequestType
	@ManyToOne(cascade={CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name="REQUEST_TYPE_CODE")
	private FraudRequestType fraudRequestType;

	@Accessors(chain=true)
	@Getter
	@Setter
	//bi-directional many-to-one association to FraudRquestHistory
	@OneToMany(mappedBy="fraudRequest", cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private List<FraudRequestStatusHistory> fraudRequestStatusHistory;

	@Getter(AccessLevel.PRIVATE)
	@Enumerated(EnumType.STRING)
	@Column(name = "FRAUD_STATUS_CODE")
	private FraudStatusCodes fraudStatusCode;

	@Getter
	@Transient
    private StateMachine<FraudStatusCodes, FraudStatusEvents> fraudStatusStateMachine;

	@SuppressWarnings("unchecked")
	public FraudRequest() {
		AnnotationConfigApplicationContext context = null;
		try{
			context = new AnnotationConfigApplicationContext(FraudStatusStateMachineConfig.class);
			StateMachineFactory<FraudStatusCodes, FraudStatusEvents> factory = context.getBean("FraudStatusStateMachine", StateMachineFactory.class);
			fraudStatusStateMachine = factory.getStateMachine("FraudRequestSM");
			this.fraudStatusCode = fraudStatusStateMachine.getState().getId();
			fraudStatusStateMachine.addStateListener(new StateMachineEventListener(this));
		}finally {
			if(context!=null){
				context.close();
			}
		}

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

	@PostLoad
	public void postLoad() {
		StateMachine<FraudStatusCodes, FraudStatusEvents> stateMachine = this.getFraudStatusStateMachine();
		handleStateForFraudStatus(this.getFraudStatusCode(), stateMachine);
	}

	private static class StateMachineEventListener extends StateMachineListenerAdapter<FraudStatusCodes, FraudStatusEvents> {
		
		private final FraudRequest entity;
		public StateMachineEventListener(FraudRequest entity){
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