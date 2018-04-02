package ca.bestbuy.orders.fraud.client.tas

import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper
import ca.bestbuy.orders.fraud.model.internal.Order
import org.springframework.oxm.Marshaller
import org.springframework.oxm.Unmarshaller
import org.springframework.ws.client.core.WebServiceTemplate
import org.springframework.ws.transport.WebServiceMessageSender
import spock.lang.Specification

class FraudServiceTasClientImplTest extends Specification {


    def "Test that exception is thrown if TASRequestXMLMapper is null when constructing FraudServiceTASClient"() {

        when:

        new FraudServiceTASClientImpl(null, createValidTASResponseXMLMapper(), createValidWebServiceTemplate())

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("TASRequestXMLMapper")

    }


    def "Test that exception is thrown if TASResponseXMLMapper is null when constructing FraudServiceTASClient"() {

        when:

        new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), null, createValidWebServiceTemplate())

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("TASResponseXMLMapper")

    }


    def "Test that exception is thrown if WebServiceTemplate is null when constructing FraudServiceTASClient"() {

        when:

        new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), null)

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("WebServiceTemplate")

    }


    def "Test that exception is thrown when calling doFraudCheck() with a null Order"() {

        setup:

        FraudServiceTASClientImpl client = new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), createValidWebServiceTemplate())

        when:

        client.doFraudCheck(null)

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("Order")

    }


    def "Test that exception is thrown when calling doFraudCheck() when tasBaseUrl is null"() {

        setup:

        FraudServiceTASClientImpl client = new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), createValidWebServiceTemplate())
        client.setFraudcheckSOAPActionCallback("callback")

        when:

        client.doFraudCheck(new Order())

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("TasBaseUrl")

    }


    def "Test that exception is thrown when calling doFraudCheck() when tasBaseUrl is empty"() {

        setup:

        FraudServiceTASClientImpl client = new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), createValidWebServiceTemplate())
        client.setFraudcheckSOAPActionCallback("callback")
        client.setTasBaseUrl("")

        when:

        client.doFraudCheck(new Order())

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("TasBaseUrl")

    }


    def "Test that exception is thrown when calling doFraudCheck() when fraudCheckSOAPActionCallback is null"() {

        setup:

        FraudServiceTASClientImpl client = new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), createValidWebServiceTemplate())
        client.setTasBaseUrl("url")
        client.setFraudcheckSOAPActionCallback(null)

        when:

        client.doFraudCheck(new Order())

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("FraudCheckSOAPActionCallback")

    }


    def "Test that exception is thrown when calling doFraudCheck() when fraudCheckSOAPActionCallback is empty"() {

        setup:

        FraudServiceTASClientImpl client = new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), createValidWebServiceTemplate())
        client.setTasBaseUrl("url")
        client.setFraudcheckSOAPActionCallback("")

        when:

        client.doFraudCheck(new Order())

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("FraudCheckSOAPActionCallback")

    }


    private TASRequestXMLMapper createValidTASRequestXMLMapper() {
        return Stub(TASRequestXMLMapper.class)
    }


    private TASResponseXMLMapper createValidTASResponseXMLMapper() {
        return Stub(TASResponseXMLMapper.class)
    }

    private WebServiceTemplate createValidWebServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate()
        webServiceTemplate.setMarshaller(Stub(Marshaller.class))
        webServiceTemplate.setUnmarshaller(Stub(Unmarshaller.class))
        webServiceTemplate.setMessageSender(Stub(WebServiceMessageSender.class))
        return webServiceTemplate
    }


}
