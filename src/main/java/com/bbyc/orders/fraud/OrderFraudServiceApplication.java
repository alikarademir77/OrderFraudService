package com.bbyc.orders.fraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Written by Keval
 *
 */

@SpringBootApplication
public class OrderFraudServiceApplication {

	@Autowired
	private OrderFraudMessagingClient messagingClient;

	public static void main(String[] args) {

		SpringApplication.run(OrderFraudServiceApplication.class, args);

	}
	
}
