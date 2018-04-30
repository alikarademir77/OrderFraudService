/**
 * 
 */
package ca.bestbuy.orders.fraud.utility;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author akaradem
 *
 */
@Component
@Profile("unittest")
public class ContextClosedUatListener implements ApplicationListener<ContextClosedEvent> {

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		if(UatServerHolder.server!=null) {
			UatServerHolder.server.stop();
		}
	}

}
