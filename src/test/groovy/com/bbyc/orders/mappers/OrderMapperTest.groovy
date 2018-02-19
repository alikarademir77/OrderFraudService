package com.bbyc.orders.mappers

import com.bbyc.orders.model.client.orderdetails.*
import com.bbyc.orders.model.internal.Item
import com.bbyc.orders.model.internal.Order
import com.bbyc.orders.model.internal.PaymentDetails
import com.bbyc.orders.model.internal.PaymentDetails.CreditCard
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

        OrderMapper orderDetailsMapper = Mappers.getMapper(OrderMapper.class)

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

        mappedOrder.getFsOrderID() == orderToMap.getId()
    }


    def "Test mapping for Order.ipAddress"() {

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.ipAddress should be mapped correctly"

        mappedOrder.getIpAddress() == orderToMap.getIpAddress()
    }


    def "Test mapping for Order.csrSalesRepID"() {

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.csrSalesRepID should be mapped correctly"

        // TODO map csrSalesRepID
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

        mappedOrder.getWebOrderRefID() == orderToMap.getWebOrderRefId()
    }


    def "Test mapping for Order.webOrderCreationDate"(){

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.webOrderCreationDate should be mapped correctly"

        assertDatesAreEqual(mappedOrder.getWebOrderCreationDate(), orderToMap.getWebOrderCreationDate())
    }


    def "Test mapping for Order.items"() {

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.items should be mapped correctly"

        mappedOrder.getItems().size() == orderToMap.getFsOrderLines().size()

        for(int i = 0; i < orderToMap.getFsOrderLines().size(); i++) {

            Item mappedItem = mappedOrder.getItems().get(i)
            FSOrderLine fsOrderLineToMap = orderToMap.getFsOrderLines().get(i)

            mappedItem.getFsoLineID() == fsOrderLineToMap.getId()
            mappedItem.getName() == fsOrderLineToMap.getProduct().getName()
            mappedItem.getItemUnitPrice() == fsOrderLineToMap.getItemCharge().getUnitPrice()
            mappedItem.getItemQuantity() == fsOrderLineToMap.getQtyOrdered()
            mappedItem.getItemTax() == fsOrderLineToMap.getItemCharge().getTax().getGst() + fsOrderLineToMap.getItemCharge().getTax().getPst()

            // TODO map itemDiscounts, category
        }

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


    def "Test mapping for Order.purchaseOrders"(){

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.purchaseOrders should be mapped correctly"

        mappedOrder.getPurchaseOrders().size() == orderToMap.getPurchaseOrders().size()
        for(int i = 0; i < orderToMap.getPurchaseOrders().size(); i++) {

            com.bbyc.orders.model.internal.PurchaseOrder mappedPurchaseOrder = mappedOrder.getPurchaseOrders().get(i)
            PurchaseOrder purchaseOrderToMap = orderToMap.getPurchaseOrders().get(i)

            mappedPurchaseOrder.getPurchaseOrderID() == purchaseOrderToMap.getId()
            mappedPurchaseOrder.getPurchaseOrderStatus() == purchaseOrderToMap.getPoSendStatus().getName()
            mappedPurchaseOrder.getShippingOrderRefID() == purchaseOrderToMap.getShippingOrderRefId()
        }

    }


    def "Test mapping for Order.shippingOrders"(){

        given: "A valid FS Order response from Order details"

        and: "mapper is invoked to map the response to the respective internal domain object"

        expect: "Order.shippingOrders should be mapped correctly"

        mappedOrder.getShippingOrders().size() == orderToMap.getShippingOrders().size()
        for(int i = 0; i < orderToMap.getShippingOrders().size(); i++) {

            com.bbyc.orders.model.internal.ShippingOrder mappedShippingOrder = mappedOrder.getShippingOrders().get(i)
            ShippingOrder shippingOrderToMap = orderToMap.getShippingOrders().get(i)

            mappedShippingOrder.getShippingOrderID() == shippingOrderToMap.getId()
            mappedShippingOrder.getGlobalContractID() == shippingOrderToMap.getGlobalContractRefId()
            mappedShippingOrder.getFulfillmentPartner() == shippingOrderToMap.getFulfillmentPartner()
            mappedShippingOrder.getShippingOrderStatus() == shippingOrderToMap.getStatus().getName()
            mappedShippingOrder.getShippingMethod() == shippingOrderToMap.getRequestedCarrier().getName()
            assertMappedAddress(mappedShippingOrder.getShippingAddress(), shippingOrderToMap.getShipToAddress())

            mappedShippingOrder.getShippingOrderLines().size() == shippingOrderToMap.getShippingOrderLines().size()
            for(int j = 0; j < shippingOrderToMap.getShippingOrderLines().size(); j++) {

                com.bbyc.orders.model.internal.ShippingOrderLine mappedShippingOrderLine = mappedShippingOrder.getShippingOrderLines().get(j)
                ShippingOrderLine shippingOrderLineToMap = shippingOrderToMap.getShippingOrderLines().get(j)

                assertMappedShippingOrderLine(mappedShippingOrderLine, shippingOrderLineToMap)
            }

            assertDatesAreEqual(mappedShippingOrder.getDeliveryDate(), shippingOrderToMap.getRequestedCarrier().getLevelOfService().getDeliveryDate())

            // TODO map shippingCharge, chargebacks

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

        // TODO Map avsResponse, cvvResponse, totalAuthorizedAmount, secureValue3D
    }


    void assertMappedShippingOrderLine(com.bbyc.orders.model.internal.ShippingOrderLine mappedShippingOrderLine, ShippingOrderLine shippingOrderLineToMap) {

        if(shippingOrderLineToMap != null) {
            assert mappedShippingOrderLine != null
        }

        assert mappedShippingOrderLine.getShippingOrderLineID() == shippingOrderLineToMap.getId()
        assert mappedShippingOrderLine.getShippingOrderLineStatus() == shippingOrderLineToMap.getStatus().getName()
        assert mappedShippingOrderLine.getQuantity() == shippingOrderLineToMap.getQtyOrdered()
        assert mappedShippingOrderLine.getFsoLineRefID() == shippingOrderLineToMap.getFsoLineRefId()
        // TODO map shippingCharge, shippingTax, shippingDiscount, ehf, ehfTax

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


    void assertDatesAreEqual(LocalDateTime mappedDate, DateTime dateToMap) {

        if(dateToMap != null) {
            assert mappedDate != null
        }

        assert mappedDate.getDayOfMonth() == dateToMap.dayOfMonth().get()
        assert mappedDate.getMonth().getValue() == dateToMap.monthOfYear().get()
        assert mappedDate.getYear() == dateToMap.year().get()
        assert mappedDate.getHour() == dateToMap.hourOfDay().get()
        assert mappedDate.getMinute() == dateToMap.minuteOfHour().get()
        assert mappedDate.getSecond() == dateToMap.secondOfMinute().get()

    }

}
