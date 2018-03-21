package ca.bestbuy.orders.fraud.client

import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper
import org.springframework.oxm.Marshaller
import org.springframework.oxm.Unmarshaller
import org.springframework.ws.client.core.WebServiceTemplate
import org.springframework.ws.transport.WebServiceMessageSender
import spock.lang.Specification

class FraudServiceTasClientImplConstructorTest extends Specification {


    def "Test that exception is thrown if TASRequestXMLMapper is null when constructing FraudServiceTASClient"() {

        when:

        new FraudServiceTASClientImpl(null, createValidTASResponseXMLMapper(), createValidWebServiceTemplate(), createValidFraudCheckOperation())

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown if TASResponseXMLMapper is null when constructing FraudServiceTASClient"() {

        when:

        new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), null, createValidWebServiceTemplate(), createValidFraudCheckOperation())

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown if WebServiceTemplate is null when constructing FraudServiceTASClient"() {

        when:

        new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), null, createValidFraudCheckOperation())

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown if WebServiceTemplate.defaultUri is empty when constructing FraudServiceTASClient"() {

        given:

        WebServiceTemplate webServiceTemplate = createValidWebServiceTemplate()

        // Condition under test
        webServiceTemplate.setDefaultUri("")

        when:

        new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), webServiceTemplate, createValidFraudCheckOperation())

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown if WebServiceTemplate.marshaller is null when constructing FraudServiceTASClient"() {

        given:

        WebServiceTemplate webServiceTemplate = createValidWebServiceTemplate()

        // Condition under test
        webServiceTemplate.setMarshaller(null)

        when:

        new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), webServiceTemplate, createValidFraudCheckOperation())

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown if WebServiceTemplate.unmarshaller is null when constructing FraudServiceTASClient"() {

        given:

        WebServiceTemplate webServiceTemplate = createValidWebServiceTemplate()

        // Condition under test
        webServiceTemplate.setUnmarshaller(null)

        when:

        new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), webServiceTemplate, createValidFraudCheckOperation())

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown if fraudCheckOperation is null when constructing FraudServiceTASClient"() {

        when:

        new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), createValidWebServiceTemplate(), null)

        then:

        thrown IllegalArgumentException

    }


    def "Test that exception is thrown if fraudCheckOperation is empty when constructing FraudServiceTASClient"() {

        when:

        new FraudServiceTASClientImpl(createValidTASRequestXMLMapper(), createValidTASResponseXMLMapper(), createValidWebServiceTemplate(), "")

        then:

        thrown IllegalArgumentException

    }


    private TASRequestXMLMapper createValidTASRequestXMLMapper() {
        return Stub(TASRequestXMLMapper.class)
    }

    private String createValidFraudCheckOperation() {
        return "BLAH BLAH BLAH"
    }

    private TASResponseXMLMapper createValidTASResponseXMLMapper() {
        return Stub(TASResponseXMLMapper.class)
    }

    private WebServiceTemplate createValidWebServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate()
        webServiceTemplate.setDefaultUri("12345")
        webServiceTemplate.setMarshaller(Stub(Marshaller.class))
        webServiceTemplate.setUnmarshaller(Stub(Unmarshaller.class))
        webServiceTemplate.setMessageSender(Stub(WebServiceMessageSender.class))
        return webServiceTemplate
    }


}
