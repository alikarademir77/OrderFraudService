package ca.bestbuy.orders.messaging.model

import ca.bestbuy.orders.messaging.EventTypes
import spock.lang.Specification

class OutboundMessagingEventTest extends Specification {

    def "Test builder create() with null event type"() {

        when:

        OutboundMessagingEvent.Builder.create(null, "1234", "1", "SUCCESS")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'eventType' must not be null"
    }


    def "Test builder create() with null order number"() {

        when:

        OutboundMessagingEvent.Builder.create(EventTypes.FraudCheck, null, "1", "SUCCESS")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'orderNumber' must not be null or empty"
    }


    def "Test builder create() with empty order number"() {

        when:

        OutboundMessagingEvent.Builder.create(EventTypes.FraudCheck, "", "1", "SUCCESS")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'orderNumber' must not be null or empty"
    }


    def "Test builder create() with null request version"() {

        when:

        OutboundMessagingEvent.Builder.create(EventTypes.FraudCheck, "1234", null, "SUCCESS")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'requestVersion' must not be null or empty"
    }


    def "Test builder create() with empty request version"() {

        when:

        OutboundMessagingEvent.Builder.create(EventTypes.FraudCheck, "1234", "", "SUCCESS")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'requestVersion' must not be null or empty"
    }


    def "Test builder create() with null result status"() {

        when:

        OutboundMessagingEvent.Builder.create(EventTypes.FraudCheck, "1234", "1", null)

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'resultStatus' must not be null or empty"
    }


    def "Test builder create() with empty result status"() {

        when:

        OutboundMessagingEvent.Builder.create(EventTypes.FraudCheck, "1234", "1", "")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'resultStatus' must not be null or empty"
    }


}
