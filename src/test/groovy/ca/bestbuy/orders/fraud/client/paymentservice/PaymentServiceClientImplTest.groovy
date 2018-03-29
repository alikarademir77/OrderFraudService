package ca.bestbuy.orders.fraud.client.paymentservice

import ca.bestbuy.orders.fraud.mappers.PaymentServiceResponseMapper
import ca.bestbuy.orders.fraud.model.internal.PaymentDetails
import org.mapstruct.factory.Mappers
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import org.springframework.ws.client.core.WebServiceTemplate
import org.springframework.ws.test.client.MockWebServiceServer
import org.springframework.ws.test.client.RequestMatchers
import org.springframework.ws.test.client.ResponseCreators
import org.springframework.xml.transform.StringSource
import spock.lang.Specification

import javax.xml.transform.Source

class PaymentServiceClientImplTest extends Specification {


    MockWebServiceServer mockServer
    PaymentServiceClientImpl client


    def setup() {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller()
        marshaller.setContextPath("ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl")
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate()
        webServiceTemplate.setDefaultUri("defaultURI")
        webServiceTemplate.setMarshaller(marshaller)
        webServiceTemplate.setUnmarshaller(marshaller)

        client = new PaymentServiceClientImpl(webServiceTemplate, Mappers.getMapper(PaymentServiceResponseMapper.class))

        mockServer = MockWebServiceServer.createServer(webServiceTemplate)

    }


    def "Test getPayPalAdditionalInfo() with a mocked response"() {

        given:

        Source responsePayload = new StringSource(
                "<GetPaymentDetailsResponse xsi:type=\"GetPayPalPaymentDetailsResponse\" xmlns=\"http://mccp.services.bby.com/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                        "         <Status>Success</Status>\n" +
                        "         <TransactionProcessedDateTime>2018-03-21T20:47:10.595025Z</TransactionProcessedDateTime>\n" +
                        "         <PaymentDetails>\n" +
                        "            <Token>EC-3Y931534FV206970S</Token>\n" +
                        "            <PayerName>tests one</PayerName>\n" +
                        "            <PayerEmail>tests_101@yahoo.com</PayerEmail>\n" +
                        "            <PayerStatus>Verified</PayerStatus>\n" +
                        "            <BillingAggrementId>B-835266756X914233C</BillingAggrementId>\n" +
                        "            <CurrentShippingAddressStatus>Confirmed</CurrentShippingAddressStatus>\n" +
                        "            <PayPalOrders>\n" +
                        "               <PayPalOrder>\n" +
                        "                  <Id>902853</Id>\n" +
                        "                  <OrderStatus>Active</OrderStatus>\n" +
                        "                  <Amount>5.59</Amount>\n" +
                        "                  <PayPalOrderId>O-5UR080200U464280T</PayPalOrderId>\n" +
                        "                  <ShippingAddress>\n" +
                        "                     <FirstName>Adam</FirstName>\n" +
                        "                     <LastName>Mark</LastName>\n" +
                        "                     <ApartmentNumber/>\n" +
                        "                     <AddressLine1>237 Abbott St.</AddressLine1>\n" +
                        "                     <AddressLine2/>\n" +
                        "                     <City>Vancouver</City>\n" +
                        "                     <StateProvince>BC</StateProvince>\n" +
                        "                     <PostalCode>V6B2K7</PostalCode>\n" +
                        "                     <Country>Canada</Country>\n" +
                        "                     <Phone>(987)654-3210</Phone>\n" +
                        "                  </ShippingAddress>\n" +
                        "                  <ShippingAddressStatus>Confirmed</ShippingAddressStatus>\n" +
                        "                  <TransactionDetails/>\n" +
                        "                  <OrderTransactionDetails>\n" +
                        "                     <PayPalOrderTransaction>\n" +
                        "                        <Id>7569338</Id>\n" +
                        "                        <OrderTransactionType>Create</OrderTransactionType>\n" +
                        "                        <TransactionStatus>Success</TransactionStatus>\n" +
                        "                        <PayPalProcessTime>2018-03-09T19:54:40</PayPalProcessTime>\n" +
                        "                        <AuditData>\n" +
                        "                           <CreatedBy>BestBuyCanada-WebChannel</CreatedBy>\n" +
                        "                           <CreatedDate>2018-03-09T19:54:38.413</CreatedDate>\n" +
                        "                           <UpdatedBy>BestBuyCanada-WebChannel</UpdatedBy>\n" +
                        "                           <UpdatedDate>2018-03-09T19:54:38.413</UpdatedDate>\n" +
                        "                        </AuditData>\n" +
                        "                     </PayPalOrderTransaction>\n" +
                        "                  </OrderTransactionDetails>\n" +
                        "               </PayPalOrder>\n" +
                        "            </PayPalOrders>\n" +
                        "         </PaymentDetails>\n" +
                        "      </GetPaymentDetailsResponse>")

        mockServer.expect(RequestMatchers.anything())
                .andRespond(ResponseCreators.withPayload(responsePayload))

        when:

        PaymentDetails.PayPal.PayPalAdditionalInfo result = client.getPayPalAdditionalInfo("1234")

        then:

        result != null
        result.email == "tests_101@yahoo.com"
        result.verifiedStatus.equalsIgnoreCase("Verified")

        mockServer.verify()

    }


    def "Test error response from Payment Service"() {

        given:

        Source responsePayload = new StringSource(
                "<GetPaymentDetailsResponse xsi:type=\"GetPayPalPaymentDetailsResponse\" xmlns=\"http://mccp.services.bby.com/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                        "<Status>Failed</Status>\n" +
                        "         <ErrorDetails>\n" +
                        "            <ErrorCode>4001</ErrorCode>\n" +
                        "            <ErrorDescription>it's all keval's fault</ErrorDescription>\n" +
                        "         </ErrorDetails>\n" +
                        "         <TransactionProcessedDateTime>2018-03-27T22:47:08.2790791Z</TransactionProcessedDateTime>" +
                        "      </GetPaymentDetailsResponse>")

        mockServer.expect(RequestMatchers.anything())
                .andRespond(ResponseCreators.withPayload(responsePayload))

        when:

        PaymentDetails.PayPal.PayPalAdditionalInfo result = client.getPayPalAdditionalInfo("1234")

        then:

        thrown PaymentServiceException

        mockServer.verify()

    }



    def "Test unexpected status in Payment Service response"() {

        given:

        Source responsePayload = new StringSource(
                "<GetPaymentDetailsResponse xsi:type=\"GetPayPalPaymentDetailsResponse\" xmlns=\"http://mccp.services.bby.com/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                        "<Status>Declined</Status>\n" +
                        "         <ErrorDetails>\n" +
                        "            <ErrorCode>4001</ErrorCode>\n" +
                        "            <ErrorDescription>it's all keval's fault</ErrorDescription>\n" +
                        "         </ErrorDetails>\n" +
                        "         <TransactionProcessedDateTime>2018-03-27T22:47:08.2790791Z</TransactionProcessedDateTime>" +
                        "      </GetPaymentDetailsResponse>")

        mockServer.expect(RequestMatchers.anything())
                .andRespond(ResponseCreators.withPayload(responsePayload))

        when:

        PaymentDetails.PayPal.PayPalAdditionalInfo result = client.getPayPalAdditionalInfo("1234")

        then:

        thrown UnexpectedResponseException

        mockServer.verify()

    }




}
