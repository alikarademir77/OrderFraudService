package ca.bestbuy.orders.fraud.client

import ca.bestbuy.orders.fraud.client.paymentservice.PaymentServiceClientImpl
import ca.bestbuy.orders.fraud.mappers.PaymentServiceResponseMapper
import org.springframework.oxm.Marshaller
import org.springframework.oxm.Unmarshaller
import org.springframework.ws.client.core.WebServiceTemplate
import spock.lang.Specification

class PaymentServiceClientImplConstructorTest extends Specification {


    def "Test that exception is thrown if webServiceTemplate provided to constructor is null"() {

        given:

        PaymentServiceResponseMapper payPalResponseMapper = Stub(PaymentServiceResponseMapper.class)

        // Condition under test
        WebServiceTemplate webServiceTemplate = null

        when:

        new PaymentServiceClientImpl(webServiceTemplate, payPalResponseMapper)

        then:

        final IllegalArgumentException exception = thrown()

        exception.message.contains('WebServiceTemplate')

    }


    def "Test that exception is thrown if webServiceTemplate provided to constructor has a marshaller that is null"() {

        given:

        PaymentServiceResponseMapper payPalResponseMapper = Stub(PaymentServiceResponseMapper.class)
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate()
        webServiceTemplate.setUnmarshaller(Stub(Unmarshaller.class))
        webServiceTemplate.setDefaultUri("defaultUri")

        // Condition under test
        webServiceTemplate.setMarshaller(null)

        when:

        new PaymentServiceClientImpl(webServiceTemplate, payPalResponseMapper)

        then:

        final IllegalArgumentException exception = thrown()

        exception.message.contains('marshaller')

    }


    def "Test that exception is thrown if webServiceTemplate provided to constructor has an unmarshaller that is null"() {

        given:

        PaymentServiceResponseMapper payPalResponseMapper = Stub(PaymentServiceResponseMapper.class)
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate()
        webServiceTemplate.setMarshaller(Stub(Marshaller.class))
        webServiceTemplate.setDefaultUri("defaultUri")

        // Condition under test

        webServiceTemplate.setUnmarshaller(null)

        when:

        new PaymentServiceClientImpl(webServiceTemplate, payPalResponseMapper)

        then:

        final IllegalArgumentException exception = thrown()

        exception.message.contains('unmarshaller')

    }


    def "Test that exception is thrown if webServiceTemplate provided to constructor has a defaultUri that is empty"() {

        given:

        PaymentServiceResponseMapper payPalResponseMapper = Stub(PaymentServiceResponseMapper.class)
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate()
        webServiceTemplate.setMarshaller(Stub(Marshaller.class))
        webServiceTemplate.setUnmarshaller(Stub(Unmarshaller.class))

        // Condition under test
        webServiceTemplate.setDefaultUri("")

        when:

        new PaymentServiceClientImpl(webServiceTemplate, payPalResponseMapper)

        then:

        final IllegalArgumentException exception = thrown()

        exception.message.contains('default uri')

    }


    def "Test that exception is thrown if mapper provided to constructor is null"() {

        given:

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate()
        webServiceTemplate.setMarshaller(Stub(Marshaller.class))
        webServiceTemplate.setUnmarshaller(Stub(Unmarshaller.class))
        webServiceTemplate.setDefaultUri("default")

        // Condition under test
        PaymentServiceResponseMapper payPalResponseMapper = null

        when:

        new PaymentServiceClientImpl(webServiceTemplate, payPalResponseMapper)

        then:

        final IllegalArgumentException exception = thrown()

        exception.message.contains('PaymentServiceResponseMapper')

    }




}
