/**
 * 
 */
package ca.bestbuy.orders.fraud.utility;

import java.sql.SQLException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author akaradem
 *
 */
@Component
@Profile("unittest")
@Slf4j
public class ApplicationStartupUatListener implements ApplicationListener<ApplicationReadyEvent>{

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			UatServerHolder.server = org.h2.tools.Server.createTcpServer(UatServerHolder.args).start();
			log.info("Started H2 TCP Server at port 8093..");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Failed to start H2 TCP Server for uat profile");
		}
	}

}
