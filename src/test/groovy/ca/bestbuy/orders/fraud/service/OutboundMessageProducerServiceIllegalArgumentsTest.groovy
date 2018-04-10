package ca.bestbuy.orders.fraud.service

import ca.bestbuy.orders.fraud.OrderFraudChannels
import spock.lang.Specification

class OutboundMessageProducerServiceIllegalArgumentsTest extends Specification {


    def "Test exception is thrown when passing null argument to constructor"() {

        when:

        new OutboundQueueProducerService(null)

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("Input channels cannot be null")
    }


    def "Test exception is thrown when calling sendOutboundMessage() with null argument"() {

        given:

        OutboundQueueProducerService service = new OutboundQueueProducerService(Stub(OrderFraudChannels.class))

        when:

        service.sendOutboundMessage(null)

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("Input message cannot be null")
    }



}
