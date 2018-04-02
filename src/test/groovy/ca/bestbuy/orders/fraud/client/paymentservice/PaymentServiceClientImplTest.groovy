package ca.bestbuy.orders.fraud.client.paymentservice

import ca.bestbuy.orders.fraud.mappers.PaymentServiceResponseMapper
import org.springframework.oxm.Marshaller
import org.springframework.oxm.Unmarshaller
import org.springframework.ws.client.core.WebServiceTemplate
import spock.lang.Specification

class PaymentServiceClientImplTest extends Specification {


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


    def "Test exception is thrown if passing in null payment service ref ID to getPayPalAdditionalInfo()"() {

        given:

        PaymentServiceClientImpl client = new PaymentServiceClientImpl(Stub(WebServiceTemplate.class), Stub(PaymentServiceResponseMapper.class))

        when:

        client.getPayPalAdditionalInfo(null)

        then:

        thrown IllegalArgumentException

    }


    def "Test exception is thrown if passing in empty payment service ref ID to getPayPalAdditionalInfo()"() {

        given:

        PaymentServiceClientImpl client = new PaymentServiceClientImpl(Stub(WebServiceTemplate.class), Stub(PaymentServiceResponseMapper.class))

        when:

        client.getPayPalAdditionalInfo("")

        then:

        thrown IllegalArgumentException

    }


    def "Test exception is thrown if calling getPayPalAdditionalInfo() when payment service base url is null"() {

        given:

        PaymentServiceClientImpl client = new PaymentServiceClientImpl(Stub(WebServiceTemplate.class), Stub(PaymentServiceResponseMapper.class))
        client.setPaymentServiceBaseUrl(null)

        when:

        client.getPayPalAdditionalInfo("1234")

        then:

        thrown IllegalStateException

    }



    def "Test exception is thrown if calling getPayPalAdditionalInfo() when payment service base url is empty"() {

        given:

        PaymentServiceClientImpl client = new PaymentServiceClientImpl(Stub(WebServiceTemplate.class), Stub(PaymentServiceResponseMapper.class))
        client.setPaymentServiceBaseUrl("")

        when:

        client.getPayPalAdditionalInfo("1234")

        then:

        thrown IllegalStateException

    }




}
