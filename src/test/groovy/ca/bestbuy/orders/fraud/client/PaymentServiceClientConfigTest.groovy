package ca.bestbuy.orders.fraud.client

import ca.bestbuy.orders.fraud.client.paymentservice.PaymentServiceClientConfig
import org.springframework.core.io.ClassPathResource
import org.springframework.web.context.support.ServletContextResource
import spock.lang.Specification

class PaymentServiceClientConfigTest extends Specification {


    def "Test that exception is thrown if url configuration is not set"() {

        given:

        PaymentServiceClientConfig config = new PaymentServiceClientConfig()

        when:

        config.setUrl(null)

        then:

        thrown IllegalArgumentException
    }


    def "Test that verifyHostName is set to true if configuration is not set"() {

        given:

        PaymentServiceClientConfig config = new PaymentServiceClientConfig()

        when:

        config.setVerifyHostName(null)

        then:

        // NOTE: Seems like in Groovy, you're able to access private members
        config.verifyHostName == true
    }


    def "Test that exception is thrown if ssl enabled configuration is not set"() {

        given:

        PaymentServiceClientConfig config = new PaymentServiceClientConfig()

        when:

        config.setSslEnabled(null)

        then:

        thrown IllegalArgumentException
    }


    def "Test validateSSLConfigurations() when keystore is null"() {

        setup:

        PaymentServiceClientConfig config = createValidConfig_SSLEnabled()
        config.setKeystore(null)

        when:

        config.httpComponentsMessageSender()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.payment-service.connection.ssl.keystore")
    }


    def "Test validateSSLConfigurations() when keystore is not the right type of resource (UrlResource or ClassPathResource)"() {

        setup:

        PaymentServiceClientConfig config = createValidConfig_SSLEnabled()
        config.setKeystore(Stub(ServletContextResource.class))

        when:

        config.httpComponentsMessageSender()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.payment-service.connection.ssl.keystore")
    }


    def "Test validateSSLConfigurations() when keystorePassword is null"() {

        setup:

        PaymentServiceClientConfig config = createValidConfig_SSLEnabled()
        config.setKeystorePassword(null)

        when:

        config.httpComponentsMessageSender()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.payment-service.connection.ssl.keystore-password")
    }


    def "Test validateSSLConfigurations() when truststore is null"() {

        setup:

        PaymentServiceClientConfig config = createValidConfig_SSLEnabled()
        config.setTruststore(null)

        when:

        config.httpComponentsMessageSender()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.payment-service.connection.ssl.truststore")
    }


    def "Test validateSSLConfigurations() when truststore is not the right type of resource (UrlResource or ClassPathResource)"() {

        setup:

        PaymentServiceClientConfig config = createValidConfig_SSLEnabled()
        config.setTruststore(Stub(ServletContextResource.class))

        when:

        config.httpComponentsMessageSender()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.payment-service.connection.ssl.truststore")
    }


    def "Test validateSSLConfigurations() when truststorePassword is null"() {

        setup:

        PaymentServiceClientConfig config = createValidConfig_SSLEnabled()
        config.setTruststorePassword(null)

        when:

        config.httpComponentsMessageSender()

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("client.payment-service.connection.ssl.truststore-password")
    }


    def "Test createHttpClient() when SSL is enabled and with valid SSL configurations"() {

        setup:

        PaymentServiceClientConfig config = createValidConfig_SSLEnabled()

        when:

        config.httpComponentsMessageSender()

        then:

        noExceptionThrown()
    }


    private static PaymentServiceClientConfig createValidConfig_SSLEnabled() {
        PaymentServiceClientConfig config = new PaymentServiceClientConfig()
        config.setSslEnabled(true)
        config.setUrl("url")
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
