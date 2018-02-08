package com.bbyc.orders.fraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * Written by Keval
 *
 */

@SpringBootApplication
@EnableBinding(OrderFraudChannels.class)
public class OrderFraudServiceApplication {
	public static void main(String[] args) {

		SpringApplication.run(OrderFraudServiceApplication.class, args);

	}
}
