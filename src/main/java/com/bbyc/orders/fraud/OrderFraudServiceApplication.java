package com.bbyc.orders.fraud;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;

import com.bbyc.orders.messaging.MessageConsumingService;
import com.bbyc.orders.messaging.MessagingEvent;

/**
 * @author akaradem
 *
 */

@SpringBootApplication
@EnableBinding(OrderFraudChannels.class)
public class OrderFraudServiceApplication {

	@Value("${messaging.errorRetryCount}")
	private Long errorRetryCount; 

	@Autowired
	private OrderFraudChannels channels;

	@Autowired
	private MessageConsumingService consumingService;

	public static void main(String[] args) {

		SpringApplication.run(OrderFraudServiceApplication.class, args);

	}
	
	@StreamListener(OrderFraudChannels.INPUT)
	public void receiveEvent(MessagingEvent event, @Header(name = "x-death", required = false) Map<?,?> death) {
		if (death != null && death.get("count").equals(errorRetryCount)) {
            // giving up - don't send to DLX
			System.out.println("Reached retry limit for event (" + event + ")..");
			return;
        }

		consumingService.consumeMessage(event);
	}

}
