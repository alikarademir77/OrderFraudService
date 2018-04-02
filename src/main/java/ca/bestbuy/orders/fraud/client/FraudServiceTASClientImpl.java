package ca.bestbuy.orders.fraud.client;

import java.io.IOException;

import javax.xml.bind.JAXBElement;

import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentRequest;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.xml.transform.StringResult;

import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper;
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderActionCode;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderRequest;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ObjectFactory;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentResult;
import ca.bestbuy.orders.fraud.model.internal.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FraudServiceTASClientImpl implements FraudServiceTASClient {


    private TASRequestXMLMapper tasRequestXMLMapper;
    private TASResponseXMLMapper tasResponseXMLMapper;
    private WebServiceTemplate webServiceTemplate;
    private String fraudCheckOperation;

    public FraudServiceTASClientImpl(TASRequestXMLMapper tasRequestXMLMapper, TASResponseXMLMapper tasResponseXMLMapper, WebServiceTemplate webServiceTemplate, String fraudCheckOperation){

        if(tasRequestXMLMapper == null) {
            throw new IllegalArgumentException("TASRequestXMLMapper provided to FraudServiceTASClientImpl must not be null");
        }

        if(tasResponseXMLMapper == null) {
            throw new IllegalArgumentException("TASResponseXMLMapper provided to FraudServiceTASClientImpl must not be null");
        }

        if(webServiceTemplate == null) {
            throw new IllegalArgumentException("WebServiceTemplate provided to FraudServiceTASClientImpl must not be null");
        }

        if(fraudCheckOperation == null || fraudCheckOperation.isEmpty()) {
            throw new IllegalArgumentException("fraudCheckOperation provided to FraudServiceTASClientImpl must not be null");
        }

        if(webServiceTemplate.getMarshaller() == null || webServiceTemplate.getUnmarshaller() == null) {
            throw new IllegalArgumentException("WebServiceTemplate provided to FraudServiceTASClientImpl must have a marshaller and unmarshaller set");
        }

        if(webServiceTemplate.getDefaultUri() == null || webServiceTemplate.getDefaultUri().isEmpty()) {
            throw new IllegalArgumentException("WebServiceTemplate provided to FraudServiceTASClientImpl must have a default uri set");
        }

        this.tasRequestXMLMapper = tasRequestXMLMapper;
        this.tasResponseXMLMapper = tasResponseXMLMapper;
        this.webServiceTemplate = webServiceTemplate;
        this.fraudCheckOperation = fraudCheckOperation;
    }

    @Override
    public FraudAssessmentResult doFraudCheck(FraudAssessmentRequest fraudAssessmentRequest) {

        //Map the request
        ManageOrderRequest request = new ManageOrderRequest();
        Order order = fraudAssessmentRequest.getOrder();
        request.setIxTranType(ManageOrderActionCode.FRAUDCHECK);
        request.setTransactionData(tasRequestXMLMapper.mapTransactionData(order));
        if(request.getTransactionData() != null) {
            request.getTransactionData().setRequestVersion(fraudAssessmentRequest.getRequestVersion());
        }

        ObjectFactory  objectFactory = new ObjectFactory();
        JAXBElement<ManageOrderRequest> jaxbRequest = objectFactory.createManageOrderRequest(request);
        String requestAsXMLString = convertToXMLString(jaxbRequest);
        log.info("Request sent to TAS:" + requestAsXMLString);

        try {
            //Send request to TAS and receive response
            JAXBElement<ManageOrderResponse> jaxbResponse = (JAXBElement<ManageOrderResponse>) webServiceTemplate.marshalSendAndReceive(jaxbRequest, new SoapActionCallback(fraudCheckOperation));
            String responseAsXMLString = convertToXMLString(jaxbResponse);
            log.info("Response received from TAS:" + responseAsXMLString);

            //map response to FraudAssessmentResult object
            ManageOrderResponse response = jaxbResponse.getValue();
            FraudAssessmentResult fraudAssessmentResult = tasResponseXMLMapper.mapManageOrderResult(response);
            fraudAssessmentResult.setTasRequest(requestAsXMLString);
            fraudAssessmentResult.setTasResponse(responseAsXMLString);
            return fraudAssessmentResult;

            //todo: handle error Action Codes like BANKDOWN or SYSERROR
        }catch(SoapFaultClientException sfce){
            //todo: handle this during flow implementation
            log.error("An error occurred while sending request to TAS: FAULT CODE is " + sfce.getFaultCode() + " and FAULT STRING is " + sfce.getFaultStringOrReason());
            throw sfce;
        }catch(WebServiceIOException wse){
            //todo: handle this during flow implementation
            log.error("A connection error occurred while communicating with TAS: " + wse.getMessage());
            throw wse;
        }


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
