package ca.bestbuy.orders.fraud.mappers

import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.PayPalPayment
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.PayerStatus
import ca.bestbuy.orders.fraud.model.internal.PaymentDetails
import org.mapstruct.factory.Mappers
import spock.lang.Shared
import spock.lang.Specification


class PaymentServiceResponseMapperTest extends Specification{


    @Shared
    PaymentServiceResponseMapper payPalResponseMapper

    def setupSpec(){
        payPalResponseMapper = Mappers.getMapper(PaymentServiceResponseMapper.class)
    }


    def "test PayPalAdditionalInfo mapper"(){


        given:
        PayPalPayment payPalPaymentToMap = new PayPalPayment();
        payPalPaymentToMap.setBillingAggrementId("billingAgreementId")
        payPalPaymentToMap.setPayerEmail("payerEmail")
        payPalPaymentToMap.setPayerStatus(PayerStatus.VERIFIED)


        when:

        PaymentDetails.PayPal.PayPalAdditionalInfo payPalAdditionalInfo = payPalResponseMapper.mapPayPalAdditionalInfo(payPalPaymentToMap)

        then:

      //  payPalAdditionalInfo.getPayPalBillingAgreementId() == payPalPaymentToMap.getBillingAggrementId()
        payPalAdditionalInfo.email == payPalPaymentToMap.getPayerEmail()
        payPalAdditionalInfo.verifiedStatus == payPalPaymentToMap.getPayerStatus().toString()


    }


}
