package ca.bestbuy.orders.fraud;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Header;

import ca.bestbuy.foundation.logging.LoggingContextFilter;
import ca.bestbuy.orders.messaging.MessageConsumingService;
import ca.bestbuy.orders.messaging.MessagingEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author akaradem
 *
 */

@SpringBootApplication
@EnableBinding(OrderFraudChannels.class)
@Slf4j
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
		try{
			consumingService.consumeMessage(event);
		}catch(Exception ex){
			long count = 0;
			if ((death != null) && (death.get("count")!=null)){
				count = (Long)death.get("count");
			}
			if (count >= errorRetryCount) {
	            // giving up - don't send to DLX
				log.info("Reached retry limit for event (" + event + ")..");
				return;
	        }
			throw ex;
		}
	}

	@Bean
    public FilterRegistrationBean loggingContextBean() {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(new LoggingContextFilter());
        frb.addUrlPatterns("/*");
        return frb;
    }
}
