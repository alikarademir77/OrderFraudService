package ca.bestbuy.orders.fraud.client;

import java.io.IOException;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.xml.transform.StringResult;

import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper;
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderActionCode;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderRequest;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ObjectFactory;
import ca.bestbuy.orders.fraud.model.internal.FraudResult;
import ca.bestbuy.orders.fraud.model.internal.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FraudServiceTASClientImpl implements FraudServiceTASClient {


    private TASRequestXMLMapper tasRequestXMLMapper;
    private TASResponseXMLMapper tasResponseXMLMapper;
    private WebServiceTemplate webServiceTemplate;
    private String fraudCheckEndpoint;

    public FraudServiceTASClientImpl(TASRequestXMLMapper tasRequestXMLMapper, TASResponseXMLMapper tasResponseXMLMapper, WebServiceTemplate webServiceTemplate, String fraudCheckEndpoint){
        this.tasRequestXMLMapper = tasRequestXMLMapper;
        this.tasResponseXMLMapper = tasResponseXMLMapper;
        this.webServiceTemplate = webServiceTemplate;
        this.fraudCheckEndpoint = fraudCheckEndpoint;
    }

    @Override
    public FraudResult doFraudCheck(Order order) {


        //Map the request
        ManageOrderRequest request = new ManageOrderRequest();
        request.setIxTranType(ManageOrderActionCode.FRAUDCHECK);
        request.setTransactionData(tasRequestXMLMapper.mapTransactionData(order));

        ObjectFactory  objectFactory = new ObjectFactory();

        JAXBElement<ManageOrderRequest> jaxbRequest = objectFactory.createManageOrderRequest(request);
        log.info("Request sent to TAS:" + convertToXMLString(jaxbRequest));


        //Send request to TAS and receive response
        JAXBElement<ManageOrderResponse> jaxbResponse = (JAXBElement<ManageOrderResponse>) webServiceTemplate.marshalSendAndReceive(webServiceTemplate.getDefaultUri()+fraudCheckEndpoint, jaxbRequest);
        log.info("Response received from TAS:" + convertToXMLString(jaxbResponse));


        //map response to FraudResult object
        ManageOrderResponse response = jaxbResponse.getValue();
        FraudResult fraudResult = tasResponseXMLMapper.mapManageOrderResult(response);
        return fraudResult;
    }

    private String convertToXMLString(JAXBElement jaxbElement) {

        StringResult output = new StringResult();
        try {
            webServiceTemplate.getMarshaller().marshal(jaxbElement, output);
        } catch (IOException e) {
            throw new IllegalStateException("Error while marshalling TAS request/response for logging purposes", e);
        }

        return output.toString();
    }


}
