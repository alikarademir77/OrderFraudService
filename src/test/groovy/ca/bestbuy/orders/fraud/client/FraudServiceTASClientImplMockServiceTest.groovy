package ca.bestbuy.orders.fraud.client

import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper
import ca.bestbuy.orders.fraud.model.internal.FraudResult
import ca.bestbuy.orders.fraud.model.internal.Item
import ca.bestbuy.orders.fraud.model.internal.Order
import org.mapstruct.factory.Mappers
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import org.springframework.ws.client.core.WebServiceTemplate
import org.springframework.ws.test.client.MockWebServiceServer
import org.springframework.ws.test.client.RequestMatchers
import org.springframework.ws.test.client.ResponseCreators
import org.springframework.xml.transform.StringSource
import spock.lang.Specification

import javax.xml.transform.Source

class FraudServiceTASClientImplMockServiceTest extends Specification {


    MockWebServiceServer mockServer
    FraudServiceTASClientImpl client


    def setup() {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller()
        marshaller.setContextPath("ca.bestbuy.orders.fraud.model.client.generated.tas.wsdl")
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate()
        webServiceTemplate.setMarshaller(marshaller)
        webServiceTemplate.setUnmarshaller(marshaller)

        client = new FraudServiceTASClientImpl(Mappers.getMapper(TASRequestXMLMapper.class), Mappers.getMapper(TASResponseXMLMapper.class), webServiceTemplate)
        client.setTasBaseUrl("url")
        client.setFraudcheckSOAPActionCallback("callback")

        mockServer = MockWebServiceServer.createServer(webServiceTemplate)
    }


    def "Test doFraudCheck() with a mocked response"() {

        given:

        Order order = createTestOrder()

        Source responsePayload = new StringSource(
                "<ten:ManageOrderResult xmlns:ten=\"http://bestbuy.com/TenderAuth\">\n" +
                        "         <ten:actionCode>SUCCESS</ten:actionCode>\n" +
                        "         <ten:transaction-results>\n" +
                        "            <ten:transaction-id>1234</ten:transaction-id>\n" +
                        "            <ten:total-score>1875</ten:total-score>\n" +
                        "            <ten:recommendation-code>1:Allow</ten:recommendation-code>\n" +
                        "            <ten:remarks>AT:  12/16/17 11:49:04 AM EST BUSINESS PROCESS:  1 SCORE:  1,875 USER:  test@accertify.com RESOLUTION:  Allow</ten:remarks>\n" +
                        "            <ten:responseData>\n" +
                        "               <ten:transaction>\n" +
                        "                  <ten:reason-code>ACCEPT</ten:reason-code>\n" +
                        "               </ten:transaction>\n" +
                        "            </ten:responseData>\n" +
                        "         </ten:transaction-results>\n" +
                        "      </ten:ManageOrderResult>")

        mockServer.expect(RequestMatchers.anything())
                .andRespond(ResponseCreators.withPayload(responsePayload))

        when:

        FraudResult result = client.doFraudCheck(order)

        then:

        result != null
        result.getActionCode() == "SUCCESS"
        result.getReasonCode() == "ACCEPT"
        result.getTransactionId() == "1234"
        result.getTotalScore() == "1875"
        result.getRecommendationCode() == "1:Allow"
        result.getRemarks() == "AT:  12/16/17 11:49:04 AM EST BUSINESS PROCESS:  1 SCORE:  1,875 USER:  test@accertify.com RESOLUTION:  Allow"

        mockServer.verify()

    }


    private static Order createTestOrder() {
        Order fraudOrder = new Order();

        List<Item> itemList = new ArrayList<>();
        Item item = new Item()
        item.setFsoLineID("fsolineid")
        item.setName("name")
        item.setCategory("category")
        item.setItemUnitPrice(new BigDecimal("9000"))
        item.setItemQuantity(1);
        item.setItemTax(new BigDecimal("0"))
        item.setItemTotalDiscount(new BigDecimal("0"))
        item.setStaffDiscount(new BigDecimal("0"))
        item.setItemStatus("OPEN")
        item.setItemSkuNumber("sku")
        item.setItemSkuDescription("desc")
        item.setPostCaptureDiscount(new BigDecimal("0"))

        itemList.add(item)
        fraudOrder.setItems(itemList)

        return fraudOrder
    }


}
