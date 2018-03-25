package ca.bestbuy.orders.fraud.utility

import org.springframework.core.io.Resource
import spock.lang.Specification

class KeystoreConfigTest extends Specification {

    def "Test if exception is thrown if keystore provided to constructor (2 arg) is null"() {

        when:

        new KeystoreConfig(null, "abc")

        then:

        final IllegalArgumentException illegalArgumentException = thrown()
        illegalArgumentException.message.contains("keystore ")

    }

    def "Test if exception is thrown if keystore provided to constructor (4 arg) is null"() {

        when:

        new KeystoreConfig(null, "abc", null, null)

        then:

        final IllegalArgumentException illegalArgumentException = thrown()
        illegalArgumentException.message.contains("keystore ")

    }

    def "Test if exception is thrown if keystore password provided to constructor (2 arg) is null"() {

        when:

        new KeystoreConfig(Stub(Resource.class), null)

        then:

        final IllegalArgumentException illegalArgumentException = thrown()
        illegalArgumentException.message.contains("keystorePassword ")

    }

    def "Test if exception is thrown if keystore password provided to constructor (4 arg) is null"() {

        when:

        new KeystoreConfig(Stub(Resource.class), null, null, null)

        then:

        final IllegalArgumentException illegalArgumentException = thrown()
        illegalArgumentException.message.contains("keystorePassword ")

    }

}
