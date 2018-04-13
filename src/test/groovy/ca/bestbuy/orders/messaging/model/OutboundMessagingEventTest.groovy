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


    def "Test object was created successfully using builder"() {

        given:

        EventTypes expectedEventType = EventTypes.FraudCheck
        String expectedOrderNumber = "1234"
        String expectedRequestVersion = "1"
        String expectedResultStatus = "ACCEPTED"
        String expectedTotalFraudScore = "1000"
        String expectedRecommendationCode = "1:Accept"
        String expectedAccertifyUser = "User"
        Date expectedAccertifyUserCreationDate = new Date()


        OutboundMessagingEvent.Builder builder = OutboundMessagingEvent.Builder.create(expectedEventType, expectedOrderNumber, expectedRequestVersion, expectedResultStatus)

        builder.totalFraudScore(expectedTotalFraudScore)
        builder.recommendationCode(expectedRecommendationCode)
        builder.accertifyUser(expectedAccertifyUser)
        builder.accertifyUserCreationDate(expectedAccertifyUserCreationDate)

        when:

        OutboundMessagingEvent messagingEvent = builder.build()

        then:

        messagingEvent.getType() == expectedEventType
        messagingEvent.getOrderNumber() == expectedOrderNumber
        messagingEvent.getMessageCreationDate() != null
        messagingEvent.getResult() != null
        messagingEvent.getResult().getStatus() == expectedResultStatus
        messagingEvent.getResult().getTotalFraudScore() == expectedTotalFraudScore
        messagingEvent.getResult().getRequestVersion() == expectedRequestVersion
        messagingEvent.getResult().getRecommendationCode() == expectedRecommendationCode
        messagingEvent.getResult().getAccertifyUser() == expectedAccertifyUser
        messagingEvent.getResult().getAccertifyUserCreationDate() == expectedAccertifyUserCreationDate
    }

}
