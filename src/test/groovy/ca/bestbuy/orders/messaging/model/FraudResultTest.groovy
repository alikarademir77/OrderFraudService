package ca.bestbuy.orders.messaging.model

import spock.lang.Specification

class FraudResultTest extends Specification {


    def "Test builder create() with null result status"() {

        when:

        FraudResult.Builder.create(null)

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'resultStatus' must not be null or empty"
    }


    def "Test builder create() with empty result status"() {

        when:

        FraudResult.Builder.create("")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message == "Input parameter 'resultStatus' must not be null or empty"
    }



    def "Test object was created successfully using builder"() {

        given:

        String expectedResultStatus = "ACCEPTED"
        String expectedTotalFraudScore = "1000"
        String expectedRecommendationCode = "1:Accept"
        String expectedAccertifyUser = "User"
        Date expectedAccertifyUserCreationDate = new Date()


        FraudResult.Builder builder = FraudResult.Builder.create(expectedResultStatus)

        builder.totalFraudScore(expectedTotalFraudScore)
        builder.recommendationCode(expectedRecommendationCode)
        builder.accertifyUser(expectedAccertifyUser)
        builder.accertifyUserCreationDate(expectedAccertifyUserCreationDate)

        when:

        FraudResult fraudResult = builder.build()

        then:

        fraudResult != null
        fraudResult.getStatus() == expectedResultStatus
        fraudResult.getTotalFraudScore() == expectedTotalFraudScore
        fraudResult.getRecommendationCode() == expectedRecommendationCode
        fraudResult.getAccertifyUser() == expectedAccertifyUser
        fraudResult.getAccertifyUserCreationDate() == expectedAccertifyUserCreationDate
    }




}
