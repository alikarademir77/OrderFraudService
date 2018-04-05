package ca.bestbuy.orders.fraud.client.tas;

import java.io.IOException;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.xml.transform.StringResult;

import ca.bestbuy.orders.fraud.mappers.TASRequestXMLMapper;
import ca.bestbuy.orders.fraud.mappers.TASResponseXMLMapper;
import ca.bestbuy.orders.fraud.model.client.generated.tas.wsdl.ManageOrderActionCode;
import ca.bestbuy.orders.fraud.model.client.generated.tas.wsdl.ManageOrderRequest;
import ca.bestbuy.orders.fraud.model.client.generated.tas.wsdl.ManageOrderResponse;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentRequest;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentResult;
import ca.bestbuy.orders.fraud.model.internal.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FraudServiceTASClientImpl implements FraudServiceTASClient {


    private TASRequestXMLMapper tasRequestXMLMapper;
    private TASResponseXMLMapper tasResponseXMLMapper;
    private WebServiceTemplate webServiceTemplate;
    private String tasBaseUrl;
    private String fraudcheckSOAPActionCallback;


    public FraudServiceTASClientImpl(TASRequestXMLMapper tasRequestXMLMapper, TASResponseXMLMapper tasResponseXMLMapper, WebServiceTemplate webServiceTemplate) {

        if (tasRequestXMLMapper == null) {
            throw new IllegalArgumentException("TASRequestXMLMapper provided to FraudServiceTASClientImpl must not be null");
        }

        if (tasResponseXMLMapper == null) {
            throw new IllegalArgumentException("TASResponseXMLMapper provided to FraudServiceTASClientImpl must not be null");
        }

        if (webServiceTemplate == null) {
            throw new IllegalArgumentException("WebServiceTemplate provided to FraudServiceTASClientImpl must not be null");
        }

        this.tasRequestXMLMapper = tasRequestXMLMapper;
        this.tasResponseXMLMapper = tasResponseXMLMapper;
        this.webServiceTemplate = webServiceTemplate;
    }


    public void setTasBaseUrl(String tasBaseUrl) {
        this.tasBaseUrl = tasBaseUrl;
    }

    public void setFraudcheckSOAPActionCallback(String fraudcheckSOAPActionCallback) {
        this.fraudcheckSOAPActionCallback = fraudcheckSOAPActionCallback;
    }

    @Override
    public FraudAssessmentResult doFraudCheck(FraudAssessmentRequest fraudAssessmentRequest) {

        if (fraudAssessmentRequest == null) {
            throw new IllegalArgumentException("FraudAssessmentRequest passed in to doFraudCheck() must not be null");
        }

        Order order = fraudAssessmentRequest.getOrder();
        
        if (order == null) {
            throw new IllegalArgumentException("Order passed in to doFraudCheck() must not be null");
        }
        
        if (tasBaseUrl == null || tasBaseUrl.isEmpty()) {
            throw new IllegalStateException("TasBaseUrl must not be null or empty. Please ensure this is set on the client object before invoking doFraudCheck()");
        }

        if (fraudcheckSOAPActionCallback == null || fraudcheckSOAPActionCallback.isEmpty()) {
            throw new IllegalStateException("FraudCheckSOAPActionCallback must not be null or empty. Please ensure this is set on the client object before invoking doFraudCheck()");
        }

        //Map the request

        ManageOrderRequest request = new ManageOrderRequest();
        request.setIxTranType(ManageOrderActionCode.FRAUDCHECK);
        request.setTransactionData(tasRequestXMLMapper.mapTransactionData(fraudAssessmentRequest));

        ObjectFactory objectFactory = new ObjectFactory();
        JAXBElement<ManageOrderRequest> jaxbRequest = objectFactory.createManageOrderRequest(request);
        String requestAsXMLString = convertToXMLString(jaxbRequest);
        log.info("Request sent to TAS:" + requestAsXMLString);

        try {
            // Send request to TAS and receive response
            @SuppressWarnings("unchecked")
            JAXBElement<ManageOrderResponse> jaxbResponse = (JAXBElement<ManageOrderResponse>) webServiceTemplate.marshalSendAndReceive(tasBaseUrl, jaxbRequest,
                new SoapActionCallback(fraudcheckSOAPActionCallback));
            String responseAsXMLString = convertToXMLString(jaxbResponse);
            log.info("Response received from TAS:" + responseAsXMLString);

            // Map response to FraudResult object
            ManageOrderResponse response = jaxbResponse.getValue();
            FraudAssessmentResult fraudAssessmentResult = tasResponseXMLMapper.mapManageOrderResult(response);
            fraudAssessmentResult.setTasRequest(requestAsXMLString);
            fraudAssessmentResult.setTasResponse(responseAsXMLString);
            return fraudAssessmentResult;

        } catch (SoapFaultClientException sfce) {
            //todo: handle this during flow implementation
            log.error("An error occurred while sending request to TAS: FAULT CODE is " + sfce.getFaultCode() + " and FAULT STRING is " + sfce.getFaultStringOrReason());
            throw sfce;
        } catch (WebServiceIOException wse) {
            //todo: handle this during flow implementation
            log.error("A connection error occurred while communicating with TAS: " + wse.getMessage());
            throw wse;
        }

    }    
    private String convertToXMLString(JAXBElement<?> jaxbElement) {

        StringResult output = new StringResult();
        try {
            webServiceTemplate.getMarshaller().marshal(jaxbElement, output);
        } catch (IOException e) {
            throw new IllegalStateException("Error while marshalling TAS request/response for logging purposes", e);
        }

        return output.toString();
    }


}
