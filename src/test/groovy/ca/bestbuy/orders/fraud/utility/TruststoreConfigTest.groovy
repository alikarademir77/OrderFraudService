package ca.bestbuy.orders.fraud.utility

import org.springframework.core.io.Resource
import spock.lang.Specification

class TruststoreConfigTest extends Specification {


    def "Test if exception is thrown if truststore provided to constructor (2 arg) is null"() {

        when:

        new TruststoreConfig(null, "abc")

        then:

        final IllegalArgumentException illegalArgumentException = thrown()
        illegalArgumentException.message.contains("truststore ")

    }


    def "Test if exception is thrown if truststorePassword provided to constructor (2 arg) is null"() {

        when:

        new TruststoreConfig(Stub(Resource.class), null)

        then:

        final IllegalArgumentException illegalArgumentException = thrown()
        illegalArgumentException.message.contains("truststorePassword ")

    }


}
