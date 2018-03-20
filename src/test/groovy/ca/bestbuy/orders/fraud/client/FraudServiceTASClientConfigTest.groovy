package ca.bestbuy.orders.fraud.client

import org.springframework.core.io.Resource
import spock.lang.Specification

class FraudServiceTASClientConfigTest extends Specification {


    def "Test that exception is thrown if url configuration is not set"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()

        when:

        config.setUrl(null)

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown if fraudCheckEndpoint configuration is not set"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()

        when:

        config.setFraudCheckEndpoint(null)

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown if hostname configuration is not set"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()

        when:

        config.setHostname(null)

        then:

        thrown IllegalArgumentException

    }


    def "Test that verifyHostName is set to true if configuration is not set"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()

        when:

        config.setVerifyHostName(null)

        then:

        config.verifyHostName == true

    }


    def "Test that exception is thrown if tlsEnabled configuration is not set"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()

        when:

        config.setTlsEnabled(null)

        then:

        thrown IllegalArgumentException

    }



    def "Test that HttpClient is not set with a SSLConnectionSocketFactory if tlsEnabled is false"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setTlsEnabled(false)

        when:

        config.httpClient()

        then:

        // 0 invocations of method
        0 * config.sslConnectionSocketFactory()

    }


    def "Test that exception is thrown when sslContext() is called with a null keystore"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setKeyStorePassword("password")
        config.setKeyPassword("password")
        config.setKeyAlias("alias")
        config.setKeyStoreType("JKS")
        config.setTrustStore(Stub(Resource.class))
        config.setTrustStorePassword("password")

        // Condition under test
        config.setKeyStore(null)

        when:

        config.sslContext()

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown when sslContext() is called with a null keystore password"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setKeyStore(Stub(Resource.class))
        config.setKeyPassword("password")
        config.setKeyAlias("alias")
        config.setKeyStoreType("JKS")
        config.setTrustStore(Stub(Resource.class))
        config.setTrustStorePassword("password")

        // Condition under test
        config.setKeyStorePassword(null)

        when:

        config.sslContext()

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown when sslContext() is called with a null keystore type"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setKeyStore(Stub(Resource.class))
        config.setKeyStorePassword("password")
        config.setKeyPassword("password")
        config.setKeyAlias("alias")
        config.setTrustStore(Stub(Resource.class))
        config.setTrustStorePassword("password")

        // Condition under test
        config.setKeyStoreType(null)

        when:

        config.sslContext()

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown when sslContext() is called with an empty keystore type"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setKeyStore(Stub(Resource.class))
        config.setKeyStorePassword("password")
        config.setKeyPassword("password")
        config.setKeyAlias("alias")
        config.setTrustStore(Stub(Resource.class))
        config.setTrustStorePassword("password")

        // Condition under test
        config.setKeyStoreType("")

        when:

        config.sslContext()

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown when sslContext() is called with a string value other than JKS for keystore type"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setKeyStore(Stub(Resource.class))
        config.setKeyStorePassword("password")
        config.setKeyPassword("password")
        config.setKeyAlias("alias")
        config.setTrustStore(Stub(Resource.class))
        config.setTrustStorePassword("password")

        // Condition under test
        config.setKeyStoreType("asdasdad")

        when:

        config.sslContext()

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown when sslContext() is called with a null key alias"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setKeyStore(Stub(Resource.class))
        config.setKeyStorePassword("password")
        config.setKeyPassword("password")
        config.setKeyStoreType("JKS")
        config.setTrustStore(Stub(Resource.class))
        config.setTrustStorePassword("password")

        // Condition under test
        config.setKeyAlias(null)

        when:

        config.sslContext()

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown when sslContext() is called with an empty key alias"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setKeyStore(Stub(Resource.class))
        config.setKeyStorePassword("password")
        config.setKeyPassword("password")
        config.setKeyStoreType("JKS")
        config.setTrustStore(Stub(Resource.class))
        config.setTrustStorePassword("password")

        // Condition under test
        config.setKeyAlias("")

        when:

        config.sslContext()

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown when sslContext() is called with a null key password"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setKeyStore(Stub(Resource.class))
        config.setKeyStorePassword("password")
        config.setKeyAlias("alias")
        config.setKeyStoreType("JKS")
        config.setTrustStore(Stub(Resource.class))
        config.setTrustStorePassword("password")

        // Condition under test
        config.setKeyPassword(null)

        when:

        config.sslContext()

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown when sslContext() is called with a null trust store"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setKeyStore(Stub(Resource.class))
        config.setKeyStorePassword("password")
        config.setKeyAlias("alias")
        config.setKeyStoreType("JKS")
        config.setKeyPassword("password")
        config.setTrustStorePassword("password")

        // Condition under test
        config.setTrustStore(null)

        when:

        config.sslContext()

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown when sslContext() is called with a null trust store password"() {

        given:

        FraudServiceTASClientConfig config = new FraudServiceTASClientConfig()
        config.setKeyStore(Stub(Resource.class))
        config.setKeyStorePassword("password")
        config.setKeyAlias("alias")
        config.setKeyStoreType("JKS")
        config.setKeyPassword("password")
        config.setTrustStore(Stub(Resource.class))

        // Condition under test
        config.setTrustStorePassword(null)

        when:

        config.sslContext()

        then:

        thrown IllegalArgumentException

    }


}
