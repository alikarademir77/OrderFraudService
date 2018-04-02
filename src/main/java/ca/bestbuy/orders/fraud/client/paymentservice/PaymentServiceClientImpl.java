package ca.bestbuy.orders.fraud.client.paymentservice;

import java.io.IOException;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.xml.transform.StringResult;

import ca.bestbuy.orders.fraud.mappers.PaymentServiceResponseMapper;
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.ErrorDetails;
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.GetPayPalPaymentDetailsRequest;
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.GetPayPalPaymentDetailsResponse;
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.ObjectFactory;
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.Status;
import ca.bestbuy.orders.fraud.model.internal.PaymentDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentServiceClientImpl implements PaymentServiceClient {

    private WebServiceTemplate webServiceTemplate;
    private PaymentServiceResponseMapper mapper;
    private String paymentServiceBaseUrl;

    private static final String QNAME_NAMESPACE = "http://mccp.services.bby.com/";
    private static final String QNAME_LOCALPART = "GetPaymentDetailsRequest";
    private static final String SOAP_ACTION_CALLBACK = "http://mccp.services.bby.com/GetPaymentDetails";


    public PaymentServiceClientImpl(WebServiceTemplate webServiceTemplate, PaymentServiceResponseMapper mapper) {

        if (webServiceTemplate == null) {
            throw new IllegalArgumentException("WebServiceTemplate provided to PaymentServiceClientImpl must not be null");
        }

        if (mapper == null) {
            throw new IllegalArgumentException("PaymentServiceResponseMapper provided to PaymentServiceClientImpl must not be null");
        }

        this.webServiceTemplate = webServiceTemplate;
        this.mapper = mapper;
    }


    public void setPaymentServiceBaseUrl(String paymentServiceBaseUrl) {
        this.paymentServiceBaseUrl = paymentServiceBaseUrl;
    }


    @Override
    public PaymentDetails.PayPal.PayPalAdditionalInfo getPayPalAdditionalInfo(String paymentServiceReferenceId) throws PaymentServiceException, ConnectionException, UnexpectedResponseException, NoActivePaypalException {

        if(paymentServiceReferenceId == null || paymentServiceReferenceId.isEmpty()) {
            throw new IllegalArgumentException("Payment service reference ID provided cannot be null");
        }

        if(paymentServiceBaseUrl == null || paymentServiceBaseUrl.isEmpty()) {
            throw new IllegalStateException("PaymentServiceBaseUrl must not be null or empty. Please ensure this is set on the client object before invoking getPayPalAdditionalInfo()");
        }

        ObjectFactory objectFactory = new ObjectFactory();

        GetPayPalPaymentDetailsRequest request = objectFactory.createGetPayPalPaymentDetailsRequest();
        request.setRequestedBy("Order Details Service");
        request.setPaymentId(paymentServiceReferenceId);
        request.setIncludeDetails(true);

        QName qname = new QName(QNAME_NAMESPACE, QNAME_LOCALPART);
        JAXBElement<GetPayPalPaymentDetailsRequest> jaxbRequest = new JAXBElement<>(qname, GetPayPalPaymentDetailsRequest.class, request);
        log.info("Request sent to Payment Service:" + convertToXMLString(jaxbRequest));

        try {
            // Send request to Payment Service and receive response
            JAXBElement<GetPayPalPaymentDetailsResponse> response = (JAXBElement<GetPayPalPaymentDetailsResponse>) webServiceTemplate.marshalSendAndReceive(paymentServiceBaseUrl, jaxbRequest, new SoapActionCallback(SOAP_ACTION_CALLBACK));

            if(response == null || response.getValue() == null || response.getValue().getStatus() == null) {

                String errorResponse = "Received an unexpected response from Payment Service. Response was either null or did not contain any value.";

                log.error(errorResponse);
                throw new UnexpectedResponseException(errorResponse);
            }

            Status responseStatus = response.getValue().getStatus();
            if(responseStatus == Status.SUCCESS) {
                log.info("Response received from Payment Service: " + convertToXMLString(response));
            }
            else if(responseStatus == Status.FAILED) {

                log.error("Error response received from Payment Service: " + convertToXMLString(response));

                ErrorDetails errorDetails = response.getValue().getErrorDetails();
                String errorCode = null;
                String errorSubcode = null;
                String errorDescription = null;
                if(errorDetails != null) {
                    errorCode = errorDetails.getErrorCode();
                    errorSubcode = errorDetails.getErrorSubCode();
                    errorDescription = errorDetails.getErrorDescription();
                }

                throw new PaymentServiceException("Received an error while making a call to Payment Service.", errorCode, errorSubcode, errorDescription);

            }
            else {

                // This will be for any other statuses we receive (as we don't expect them)

                String errorResponse = "Unexpected response received from Payment Service. Response status was not 'SUCCESS' or 'FAILURE'";

                log.error(errorResponse + ": " + convertToXMLString(response));
                throw new UnexpectedResponseException(errorResponse);
            }

            // Map response to PayPalAdditionalInfo object
            PaymentDetails.PayPal.PayPalAdditionalInfo payPalAdditionalInfo = mapper.mapPayPalAdditionalInfo(response.getValue().getPaymentDetails());
            return payPalAdditionalInfo;

        } catch (SoapFaultClientException sfce) {
            // This will only be thrown for any SOAP faults

            log.error("Received a soap fault while sending a request to Payment Service: FAULT CODE is " + sfce.getFaultCode() + " and FAULT STRING is " + sfce.getFaultStringOrReason());
            throw sfce;

        } catch (WebServiceIOException wse) {
            // This should only be thrown in cases of timeouts

            String errorMessage = "A connection error occurred while communicating with Payment Service";
            log.error(errorMessage + ": " + wse.getMessage());
            throw new ConnectionException(errorMessage, wse);
        } catch (NoActivePaypalException mie){
            // This will only be thrown in cases of no active paypal orders being found

            String errorMessage = "An error occurred while mapping PayPal Additional Information";
            log.error(errorMessage + ": " + mie.getMessage());
            throw mie;

        }

    }


    private String convertToXMLString(JAXBElement jaxbElement) {

        StringResult output = new StringResult();
        try {
            webServiceTemplate.getMarshaller().marshal(jaxbElement, output);
        } catch (IOException e) {
            throw new IllegalStateException("Error while marshalling Payment Service request/response for logging purposes", e);
        }

        return output.toString();
    }

}
