package ca.bestbuy.orders.messaging.model

import ca.bestbuy.orders.messaging.EventTypes
import spock.lang.Specification

class OutboundMessagingEventTest extends Specification {


    def "Test constructor with null event type"() {

        when:

        new OutboundMessagingEvent<String>(null, "1234", "1", "SUCCESS")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'type' must not be null"
    }


    def "Test constructor with null order number"() {

        when:

        new OutboundMessagingEvent<String>(EventTypes.FraudCheck, "1", null, "SUCCESS")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'orderNumber' must not be null or empty"
    }


    def "Test constructor with empty order number"() {

        when:

        new OutboundMessagingEvent(EventTypes.FraudCheck, "1", "", "SUCCESS")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'orderNumber' must not be null or empty"
    }


    def "Test constructor with null request version"() {

        when:

        new OutboundMessagingEvent<String>(EventTypes.FraudCheck, null, "1234", "SUCCESS")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'requestVersion' must not be null or empty"
    }


    def "Test constructor with empty request version"() {

        when:

        new OutboundMessagingEvent<String>(EventTypes.FraudCheck, "", "1234", "SUCCESS")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'requestVersion' must not be null or empty"
    }

}
