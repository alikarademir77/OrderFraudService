package ca.bestbuy.orders.fraud.mappers

import ca.bestbuy.orders.fraud.client.paymentservice.NoActivePaypalException
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.ArrayOfPayPalOrder
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.OrderStatus
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.PayPalOrder
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

        ArrayOfPayPalOrder arrayOfPayPalOrder = new ArrayOfPayPalOrder()

        arrayOfPayPalOrder.getPayPalOrder().add(createAPayPalOrder(OrderStatus.CLOSED, "1"))
        arrayOfPayPalOrder.getPayPalOrder().add(createAPayPalOrder(OrderStatus.ACTIVE, "2" ))

        payPalPaymentToMap.setPayPalOrders(arrayOfPayPalOrder)


        when:

        PaymentDetails.PayPal.PayPalAdditionalInfo payPalAdditionalInfo = payPalResponseMapper.mapPayPalAdditionalInfo(payPalPaymentToMap)

        then:

        payPalAdditionalInfo.email == payPalPaymentToMap.getPayerEmail()
        payPalAdditionalInfo.verifiedStatus == payPalPaymentToMap.getPayerStatus().toString()
        payPalAdditionalInfo.payPalOrderId == "2"


    }



    def "test PayPalAdditionalInfo mapper with all CLOSED PayPal status"(){


        given:
        PayPalPayment payPalPaymentToMap = new PayPalPayment();
        payPalPaymentToMap.setBillingAggrementId("billingAgreementId")
        payPalPaymentToMap.setPayerEmail("payerEmail")
        payPalPaymentToMap.setPayerStatus(PayerStatus.VERIFIED)

        ArrayOfPayPalOrder arrayOfPayPalOrder = new ArrayOfPayPalOrder()

        arrayOfPayPalOrder.getPayPalOrder().add(createAPayPalOrder(OrderStatus.CLOSED, "1"))
        arrayOfPayPalOrder.getPayPalOrder().add(createAPayPalOrder(OrderStatus.CLOSED, "2" ))

        payPalPaymentToMap.setPayPalOrders(arrayOfPayPalOrder)


        when:

        PaymentDetails.PayPal.PayPalAdditionalInfo payPalAdditionalInfo = payPalResponseMapper.mapPayPalAdditionalInfo(payPalPaymentToMap)

        then:

        thrown NoActivePaypalException


    }

    def "test PayPalAdditionalInfo mapper with one PENDING PayPal status and one CLOSED PayPal status"(){


        given:
        PayPalPayment payPalPaymentToMap = new PayPalPayment();
        payPalPaymentToMap.setBillingAggrementId("billingAgreementId")
        payPalPaymentToMap.setPayerEmail("payerEmail")
        payPalPaymentToMap.setPayerStatus(PayerStatus.VERIFIED)

        ArrayOfPayPalOrder arrayOfPayPalOrder = new ArrayOfPayPalOrder()

        arrayOfPayPalOrder.getPayPalOrder().add(createAPayPalOrder(OrderStatus.PENDING, "1"))
        arrayOfPayPalOrder.getPayPalOrder().add(createAPayPalOrder(OrderStatus.CLOSED, "2" ))

        payPalPaymentToMap.setPayPalOrders(arrayOfPayPalOrder)


        when:

        PaymentDetails.PayPal.PayPalAdditionalInfo payPalAdditionalInfo = payPalResponseMapper.mapPayPalAdditionalInfo(payPalPaymentToMap)

        then:

        thrown NoActivePaypalException


    }


    PayPalOrder createAPayPalOrder(OrderStatus orderStatus, String id){

        PayPalOrder payPalOrder = new PayPalOrder()
        payPalOrder.setOrderStatus(orderStatus)
        payPalOrder.setPayPalOrderId(id)

        return payPalOrder

    }


}
