package ca.bestbuy.orders.fraud.flow.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//TODO: Change to use AOP
public abstract class ActionWithException<S, E> implements Action<S, E> {

	/* (non-Javadoc)
	 * @see org.springframework.statemachine.action.Action#execute(org.springframework.statemachine.StateContext)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void execute(StateContext<S, E> context) {
		try{
			doExecute(context);
		}catch(Exception exception){
            context.getStateMachine().setStateMachineError(exception);
            context.getStateMachine().getExtendedState().getVariables().put("ERROR", exception);	
            context.getStateMachine().sendEvent(getErrorEvent());
		}
	}
	
	/**
	 * @param context
	 * @throws Exception
	 */
	protected abstract void doExecute(StateContext<S, E> context) throws Exception;
	
	/**
	 * @return
	 */
	protected abstract E getErrorEvent(); 

}
