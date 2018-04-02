package ca.bestbuy.orders.fraud.utility

import org.apache.http.impl.client.HttpClientBuilder
import spock.lang.Specification

class WebClientUtilityConfigureTimeoutsTest extends Specification {


    def "Test configureTimeouts() when builder passed in is null"() {

        when:

        WebClientUtility.configureTimeouts(null, new TimeoutConfig(null, null))

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("HttpClientBuilder")
    }

    def "Test configureTimeouts() when timeoutConfig passed in is null"() {

        when:

        WebClientUtility.configureTimeouts(HttpClientBuilder.create(), null)

        then:

        noExceptionThrown()
    }


    def "Test configureTimeouts() with all valid parameters - TimeoutConfig(1000, 2000)"() {

        when:

        WebClientUtility.configureTimeouts(HttpClientBuilder.create(), new TimeoutConfig(1000, 2000))

        then:

        noExceptionThrown()

    }


    def "Test configureTimeouts() with all valid parameters - TimeoutConfig(null, null)"() {

        when:

        WebClientUtility.configureTimeouts(HttpClientBuilder.create(), new TimeoutConfig(null, null))

        then:

        noExceptionThrown()

    }


}
