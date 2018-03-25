package ca.bestbuy.orders.fraud.client

import org.springframework.core.io.ClassPathResource
import org.springframework.web.context.support.ServletContextResource
import spock.lang.Specification

class OrderDetailsClientConfigTest extends Specification {

    def "Test that exception is thrown if url configuration is not set"() {

        given:

        OrderDetailsClientConfig config = new OrderDetailsClientConfig()

        when:

        config.setUrl(null)

        then:

        thrown IllegalArgumentException
    }


    def "Test that exception is thrown if getOrderDetailsEndpoint configuration is not set"() {

        given:

        OrderDetailsClientConfig config = new OrderDetailsClientConfig()

        when:

        config.setGetOrderDetailsEndpoint(null)

        then:

        thrown IllegalArgumentException

    }


    def "Test that verifyHostName is set to true if configuration is not set"() {

        given:

        OrderDetailsClientConfig config = new OrderDetailsClientConfig()

        when:

        config.setVerifyHostName(null)

        then:

        config.verifyHostName == true

    }


    def "Test that exception is thrown if sslEnabled configuration is not set"() {

        given:

        OrderDetailsClientConfig config = new OrderDetailsClientConfig()

        when:

        config.setSslEnabled(null)

        then:

        thrown IllegalArgumentException

    }


    def "Test validateSSLConfigurations() when keystore is null"() {

        setup:

        OrderDetailsClientConfig config = createValidConfig_SSLEnabled()
        config.setKeystore(null)

        when:

        config.createHttpClient()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.order-details.connection.ssl.keystore")
    }


    def "Test validateSSLConfigurations() when keystore is not the right type of resource (UrlResource or ClassPathResource)"() {

        setup:

        OrderDetailsClientConfig config = createValidConfig_SSLEnabled()
        config.setKeystore(Stub(ServletContextResource.class))

        when:

        config.createHttpClient()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.order-details.connection.ssl.keystore")
    }


    def "Test validateSSLConfigurations() when keystorePassword is null"() {

        setup:

        OrderDetailsClientConfig config = createValidConfig_SSLEnabled()
        config.setKeystorePassword(null)

        when:

        config.createHttpClient()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.order-details.connection.ssl.keystore-password")
    }


    def "Test validateSSLConfigurations() when truststore is null"() {

        setup:

        OrderDetailsClientConfig config = createValidConfig_SSLEnabled()
        config.setTruststore(null)

        when:

        config.createHttpClient()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.order-details.connection.ssl.truststore")
    }


    def "Test validateSSLConfigurations() when truststore is not the right type of resource (UrlResource or ClassPathResource)"() {

        setup:

        OrderDetailsClientConfig config = createValidConfig_SSLEnabled()
        config.setTruststore(Stub(ServletContextResource.class))

        when:

        config.createHttpClient()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.order-details.connection.ssl.truststore")
    }


    def "Test validateSSLConfigurations() when truststorePassword is null"() {

        setup:

        OrderDetailsClientConfig config = createValidConfig_SSLEnabled()
        config.setTruststorePassword(null)

        when:

        config.createHttpClient()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.order-details.connection.ssl.truststore-password")
    }


    def "Test createHttpClient() when SSL is enabled and with valid SSL configurations"() {

        setup:

        OrderDetailsClientConfig config = createValidConfig_SSLEnabled()

        when:

        config.createHttpClient()

        then:

        noExceptionThrown()
    }


    private static OrderDetailsClientConfig createValidConfig_SSLEnabled() {
        OrderDetailsClientConfig config = new OrderDetailsClientConfig()
        config.setSslEnabled(true)
        config.setUrl("url")
        config.setGetOrderDetailsEndpoint("endpoint")
        config.setRequestTimeout(1000)
        config.setConnectionTimeout(10000)
        config.setVerifyHostName(true)
        config.setKeystore(new ClassPathResource("test-keystores/keystore-with-same-key-password.jks"))
        config.setKeystorePassword("password")
        config.setTruststore(new ClassPathResource("test-keystores/truststore.jks"))
        config.setTruststorePassword("password")
        return config
    }


}
