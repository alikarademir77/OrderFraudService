package com.bbyc.orders.mappers

import com.bbyc.orders.model.client.orderdetails.*
import com.bbyc.orders.model.internal.Order
import com.bbyc.orders.model.internal.PaymentDetails
import com.bbyc.orders.model.internal.PaymentDetails.CreditCard
import com.bbyc.orders.model.internal.ShippingOrder.OrderLine
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import org.joda.time.DateTime
import org.mapstruct.factory.Mappers
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

class OrderMapperTest extends Specification {


    @Shared
    FSOrder orderToMap

    @Shared
    Order mappedOrder


    def setupSpec(){

        OrderMapper orderDetailsMapper = Mappers.getMapper(OrderMapper.class);

        String orderDetailsResponse = new File('src/test/resources/order-details-response.json').text

        ObjectMapper objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JodaModule())
        orderToMap = objectMapper.readValue(orderDetailsResponse, FSOrder.class)

        mappedOrder = orderDetailsMapper.mapOrder(orderToMap);

    }


    def "Test mapping for Order.fsOrderNumber"() {

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.fsOrderNumber should be mapped correctly"

        mappedOrder.getFsOrderNumber() == orderToMap.getId()
    }


    def "Test mapping for Order.ipAddress"() {

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.ipAddress should be mapped correctly"

        mappedOrder.getIpAddress() == orderToMap.getIpAddress()
    }


    def "Test mapping for Order.rewardZoneID"(){

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.rewardZoneID should be mapped correctly"

        mappedOrder.getRewardZoneID() == orderToMap.getRewardZone().getRewardZoneId()
    }


    def "Test mapping for Order.webOrderNumber"(){

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.webOrderNumber should be mapped correctly"

        mappedOrder.getWebOrderNumber() == orderToMap.getWebOrderRefId()
    }


    def "Test mapping for Order.orderCreationTime"(){

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.orderCreationTime should be mapped correctly"

        // TODO
    }


    def "Test mapping for Order.paymentDetails"(){

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.paymentDetails should be mapped correctly"

        PaymentDetails mappedPaymentDetails = mappedOrder.getPaymentDetails()
        mappedPaymentDetails != null
        // Credit cards
        mappedPaymentDetails.creditCards.size() == orderToMap.getPaymentMethodInfo().getCreditCards().size()
        for(int i = 0; i < orderToMap.getPaymentMethodInfo().getCreditCards().size(); i++) {
            assertMappedCreditCard(mappedPaymentDetails.creditCards.get(i), orderToMap.getPaymentMethodInfo().getCreditCards().get(i))
        }

        // Gift cards
        mappedPaymentDetails.giftCards.size() == orderToMap.getPaymentMethodInfo().getGiftCards().size()
        for(int i = 0; i < orderToMap.getPaymentMethodInfo().getGiftCards().size(); i++) {

            String mappedGiftCardNumber = mappedPaymentDetails.getGiftCards().get(i).giftCardNumber
            String giftCardNumberToMap = orderToMap.getPaymentMethodInfo().getGiftCards().get(i).getGiftCardNumber()

            assert mappedGiftCardNumber == giftCardNumberToMap
        }

        // TODO Paypal mapping
    }


    def "Test mapping for Order.shippingOrders"(){

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.shippingOrders should be mapped correctly"

        mappedOrder.shippingOrders.size() == orderToMap.getShippingOrders().size()
        for(int i = 0; i < orderToMap.getShippingOrders().size(); i++) {

            com.bbyc.orders.model.internal.ShippingOrder mappedShippingOrder = mappedOrder.shippingOrders.get(i)
            ShippingOrder shippingOrderToMap = orderToMap.getShippingOrders().get(i)

            mappedShippingOrder.shippingOrderID == shippingOrderToMap.getId()
            mappedShippingOrder.globalContractID == shippingOrderToMap.getGlobalContractRefId()
            mappedShippingOrder.fulfillmentPartner == shippingOrderToMap.getFulfillmentPartner()
            mappedShippingOrder.shippingOrderStatus == shippingOrderToMap.getStatus().getName()
            mappedShippingOrder.shippingDetails != null
            mappedShippingOrder.shippingDetails.carrierCode == shippingOrderToMap.getActualCarrier().getLevelOfService().getCarrierCode()
            mappedShippingOrder.shippingDetails.serviceLevel == shippingOrderToMap.getActualCarrier().getLevelOfService().getName()
            assertMappedAddress(mappedShippingOrder.shippingDetails.shippingAddress, shippingOrderToMap.getShipToAddress())

            mappedShippingOrder.shippingOrderLines.size() == shippingOrderToMap.getShippingOrderLines().size()
            for(int j = 0; j < shippingOrderToMap.getShippingOrderLines().size(); j++) {

                OrderLine mappedShippingOrderLine = mappedShippingOrder.shippingOrderLines.get(j)
                ShippingOrderLine shippingOrderLineToMap = shippingOrderToMap.getShippingOrderLines().get(j)

                assertMappedShippingOrderLine(mappedShippingOrderLine, shippingOrderLineToMap)
            }

            // TODO map purchaseOrderID, purchaseOrderStatus, shippingCharge, shippingChargeTax, shippingDetails.shippingMethod, shippingDetails.deadline, chargebacks, shippingDetails.deadline

        }


    }


    void assertMappedCreditCard(CreditCard mappedCreditCard, CreditCardInfo creditCardToMap) {

        if(creditCardToMap != null) {
            assert mappedCreditCard != null
        }

        assert mappedCreditCard.creditCardNumber == creditCardToMap.getCreditCardNumber()
        assert mappedCreditCard.creditCardType == creditCardToMap.getCreditCardType()
        assertMappedAddress(mappedCreditCard.billingAddress, creditCardToMap.getBillingAddress())
        assert mappedCreditCard.creditCardExpiryDate == creditCardToMap.getCreditCardExpiryDate()

        // TODO Map avs response, cvv response, total authorized amount, 3d secure value
    }


    void assertMappedShippingOrderLine(OrderLine mappedShippingOrderLine, ShippingOrderLine shippingOrderLineToMap) {

        if(shippingOrderLineToMap != null) {
            assert mappedShippingOrderLine != null
        }

        assert mappedShippingOrderLine.lineNumber == shippingOrderLineToMap.getId()
        assert mappedShippingOrderLine.status == shippingOrderLineToMap.getStatus().getName()
        assert mappedShippingOrderLine.price == shippingOrderLineToMap.getUnitPrice()
        assert mappedShippingOrderLine.quantity == shippingOrderLineToMap.getQtyOrdered()

        // TODO map description, staffDiscount, postCaptureDiscount, salesTax, itemTax, productSalesTax, shippingChargeTax, environmentHandlingFeeTax

    }


    void assertMappedAddress(com.bbyc.orders.model.internal.Address mappedAddress, Address addressToMap) {

        if(addressToMap != null) {
            assert mappedAddress != null
        }

        assert mappedAddress.address1 == addressToMap.getAddress1()
        assert mappedAddress.address2 == addressToMap.getAddress2()
        assert mappedAddress.city == addressToMap.getCity()
        assert mappedAddress.country == addressToMap.getCountry()
        assert mappedAddress.email == addressToMap.getEmail()
        assert mappedAddress.firstName == addressToMap.getFirstName()
        assert mappedAddress.lastName == addressToMap.getLastName()
        assert mappedAddress.phoneNumber == addressToMap.getPhone()
        assert mappedAddress.postalCode == addressToMap.getPostalCode()
        assert mappedAddress.province == addressToMap.getProvince()
        assert mappedAddress.secondaryPhoneNumber == addressToMap.getPhone2()
    }


    DateTime convertLocalDateTimeToJodaDateTime(LocalDateTime localDateTime) {

        // TODO

        return null
    }

}
