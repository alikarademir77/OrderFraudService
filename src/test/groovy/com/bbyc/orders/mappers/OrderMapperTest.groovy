package com.bbyc.orders.mappers

import com.bbyc.orders.model.client.orderdetails.*
import com.bbyc.orders.model.internal.Item
import com.bbyc.orders.model.internal.Order
import com.bbyc.orders.model.internal.PaymentDetails
import com.bbyc.orders.model.internal.PaymentDetails.CreditCard
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.mapstruct.factory.Mappers
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

class OrderMapperTest extends Specification {

    // TODO - Rewrite tests to only test methods from OrderMapper


    @Shared
    FSOrder orderToMap

    @Shared
    Order mappedOrder

    @Shared
    OrderMapper orderDetailsMapper


    def setupSpec(){
        orderDetailsMapper = Mappers.getMapper(OrderMapper.class)
    }

    def "Test Order Mapper"() {

        given: "a valid FSOrder"
        FSOrder fsOrderToMap = new FSOrder();
        fsOrderToMap.setWebOrderRefId("webOrderRefId")
        fsOrderToMap.setId("fsorderId")
        fsOrderToMap.setIpAddress("ipAddress")

        DateTime dateTimeToMap = new DateTime()
        dateTimeToMap = DateTime.now()
        fsOrderToMap.setWebOrderCreationDate(dateTimeToMap)


        RewardZone rewardZoneToMap = new RewardZone()
        rewardZoneToMap.setRewardZoneId("rewardZoneID")
        fsOrderToMap.setRewardZone(rewardZoneToMap)

        List<FSOrderLine> fsOrderLineListToMap = new ArrayList<>()
        FSOrderLine orderLine1 = new FSOrderLine()
        FSOrderLine orderLine2 = new FSOrderLine()
        FSOrderLine orderLine3 = new FSOrderLine()
        fsOrderLineListToMap.add(orderLine1)
        fsOrderLineListToMap.add(orderLine2)
        fsOrderLineListToMap.add(orderLine3)
        fsOrderToMap.setFsOrderLines(fsOrderLineListToMap)

        List<PurchaseOrder> purchaseOrderList = new ArrayList<>()
        PurchaseOrder purchaseOrder1 = new PurchaseOrder()
        PurchaseOrder purchaseOrder2 = new PurchaseOrder()
        purchaseOrderList.add(purchaseOrder1)
        purchaseOrderList.add(purchaseOrder2)
        fsOrderToMap.setPurchaseOrders(purchaseOrderList)

        List<ShippingOrder> shippingOrderList = new ArrayList<>()
        ShippingOrder shippingOrder1 = new ShippingOrder()
        ShippingOrder shippingOrder2 = new ShippingOrder()
        ShippingOrder shippingOrder3 = new ShippingOrder()
        ShippingOrder shippingOrder4 = new ShippingOrder()
        shippingOrderList.add(shippingOrder1)
        shippingOrderList.add(shippingOrder2)
        shippingOrderList.add(shippingOrder3)
        shippingOrderList.add(shippingOrder4)
        fsOrderToMap.setShippingOrders(shippingOrderList)

        PaymentMethodInfo paymentMethodInfo = new PaymentMethodInfo()
        fsOrderToMap.setPaymentMethodInfo(paymentMethodInfo)

        when: "the order mapper is called to map the FSOrder to our internal domain objects"

        OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class)
        Order mappedOrder = orderMapper.mapOrder(fsOrderToMap)


        then: "our internal domain FSOrder object was correctly mapped to"

        mappedOrder.getWebOrderRefID() == fsOrderToMap.getWebOrderRefId()
        mappedOrder.getFsOrderID() == fsOrderToMap.getId()
        mappedOrder.getIpAddress() == fsOrderToMap.getIpAddress()
        //todo: need to fix date
   //     mappedOrder.getWebOrderCreationDate() == fsOrderToMap.getWebOrderCreationDate()
        mappedOrder.getRewardZoneID() == fsOrderToMap.getRewardZone().getRewardZoneId()
        mappedOrder.getItems().size() == fsOrderToMap.getFsOrderLines().size()
        mappedOrder.getPurchaseOrders().size() == fsOrderToMap.getPurchaseOrders().size()
        mappedOrder.getShippingOrders().size() == fsOrderToMap.getShippingOrders().size()
        mappedOrder.getPaymentDetails() != null


    }



    def "Test Item Mapper"(){

        given: "a valid fsorderline"
        float totalDiscount = 0.00f
        float totalTaxes = 0.00f
        FSOrderLine fsOrderLineToMap = new FSOrderLine()
        fsOrderLineToMap.setId("fsoLineId")

        Product product = new Product()
        product.setName("product name")
        fsOrderLineToMap.setProduct(product)

        ItemCharge itemCharge = new ItemCharge()
        List<Discount> discounts = new ArrayList<>()
        ItemChargeDiscount discount1 = new ItemChargeDiscount()
        discount1.setUnitValue(1.0f)
        discount1.setQuantity(1)
        ItemChargeDiscount discount2 = new ItemChargeDiscount()
        discount2.setUnitValue(0.10f)
        discount2.setQuantity(5)
        discounts.add(discount1)
        discounts.add(discount2)
        itemCharge.setDiscounts(discounts)



        Tax tax = new Tax()
        tax.setGst(0.05f)
        tax.setPst(0.06f)
        itemCharge.setTax(tax)
        itemCharge.setUnitPrice(2.00f)
        fsOrderLineToMap.setItemCharge(itemCharge)

        fsOrderLineToMap.setQtyOrdered(5)

        when: "the order mapper is called to map the FSOrderLine to our internal Item object"

        Item mappedItem = orderDetailsMapper.mapItem(fsOrderLineToMap)

        for(int i = 0; i < fsOrderLineToMap.getItemCharge().getDiscounts().size(); i++){
            ItemChargeDiscount discount = fsOrderLineToMap.getItemCharge().getDiscounts().get(i)
            totalDiscount += discount.getQuantity() * discount.getUnitValue()
        }

        totalTaxes = fsOrderLineToMap.getItemCharge().getTax().getGst() + fsOrderLineToMap.getItemCharge().getTax().getPst()

        then: "our internal domain Item object were correctly mapped to"

        mappedItem.getFsoLineID() == fsOrderLineToMap.getId()
        mappedItem.getName() == fsOrderLineToMap.getProduct().getName()
        mappedItem.getItemUnitPrice() == fsOrderLineToMap.getItemCharge().getUnitPrice()
        mappedItem.getItemQuantity() == fsOrderLineToMap.getQtyOrdered()
        mappedItem.getItemTax() == totalTaxes
    }


    def "Test PurchaseOrder Mapper"(){

        given: "a valid purchase order object"
        PurchaseOrder purchaseOrderToMap = new PurchaseOrder()
        purchaseOrderToMap.setId("purchaseOrderId")

        Status poSendStatus = new Status()
        poSendStatus.setName("poSendStatus")
        purchaseOrderToMap.setPoSendStatus(poSendStatus)

        purchaseOrderToMap.setShippingOrderRefId("shippingOrderRefId")

        when: "the order mapper is called to map the Purchase Order to our internal object"
        com.bbyc.orders.model.internal.PurchaseOrder mappedPurchaseOrder = orderDetailsMapper.mapPurchaseOrder(purchaseOrderToMap)

        then: "our internal domain Purchased Order object were correctly mapped to"

        mappedPurchaseOrder.getPurchaseOrderID() == purchaseOrderToMap.getId()
        mappedPurchaseOrder.getPurchaseOrderStatus() == purchaseOrderToMap.getPoSendStatus().getName()
        mappedPurchaseOrder.getShippingOrderRefID() == purchaseOrderToMap.getShippingOrderRefId()

    }

    def "test Shipping Order Mapper"(){

        given: "a valid shipping order"
        float unitPrice1 = 1.00f
        float unitPrice2 = 2.00f
        float unitPrice3 = 3.00f
        float totalUnitPrice = 0
        float totalTax = 0
        float totalDiscounts=0

        ShippingOrder shippingOrderToMap = new ShippingOrder()
        shippingOrderToMap.setId("shippingOrderId")
        shippingOrderToMap.setGlobalContractRefId("globalContractRefId")

        Status shippingOrderStatus = new Status()
        shippingOrderStatus.setName("shipping order status")
        shippingOrderToMap.setStatus(shippingOrderStatus)


        List<ShippingCharge> shippingCharges = new ArrayList<>()
        ShippingCharge shippingCharge1 = createAShippingCharge(0.07f,0.07f,unitPrice1)
        ShippingCharge shippingCharge2 = createAShippingCharge(0.08f,0.08f,unitPrice2)
        ShippingCharge shippingCharge3 = createAShippingCharge(0.09f,0.09f,unitPrice3)

        shippingCharges.add(shippingCharge1)
        shippingCharges.add(shippingCharge2)
        shippingCharges.add(shippingCharge3)
        shippingOrderToMap.setShippingCharges(shippingCharges)
        shippingOrderToMap.setFulfillmentPartner("fulfillmentPartner")

        RequestedCarrier requestedCarrier = new RequestedCarrier()
        LevelOfService levelOfService = new LevelOfService()
        levelOfService.setId("level of service id")


        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        DateTime deliveryDate = formatter.parseDateTime("2018-02-19T09:15:52Z");


        levelOfService.setDeliveryDate(deliveryDate)
        requestedCarrier.setLevelOfService(levelOfService)
        shippingOrderToMap.setRequestedCarrier(requestedCarrier)

        Address shippingAddress = new Address()
        shippingOrderToMap.setShipToAddress(shippingAddress)


        List<ShippingOrderLine> shippingOrderLines = new ArrayList<>()
        ShippingOrderLine shippingOrderLine1 = new ShippingOrderLine()
        ShippingOrderLine shippingOrderLine2 = new ShippingOrderLine()
        shippingOrderLines.add(shippingOrderLine1)
        shippingOrderLines.add(shippingOrderLine2)
        shippingOrderToMap.setShippingOrderLines(shippingOrderLines)



        when: "the order mapper is called to map the Shipping Order object to our internal domain object"
        com.bbyc.orders.model.internal.ShippingOrder mappedShippingOrder = orderDetailsMapper.mapShippingOrder(shippingOrderToMap)

        for(int i = 0; i < shippingOrderToMap.getShippingCharges().size(); i++){
            ShippingCharge shippingCharge = shippingOrderToMap.getShippingCharges().get(i)
            totalUnitPrice += shippingCharge.getUnitPrice()
            totalTax += shippingOrderToMap.getShippingCharges().get(i).getTax().getGst() + shippingOrderToMap.getShippingCharges().get(i).getTax().getPst()
            for(int j = 0; j < shippingCharge.getDiscounts().size(); j++){
                Discount discount = shippingCharge.getDiscounts().get(j)
                totalDiscounts += discount.getQuantity()*discount.getUnitValue()

            }
        }

        totalUnitPrice = totalUnitPrice - totalDiscounts

        then: "our internal domain Shipping Order object were correctly mapped to"
        mappedShippingOrder.getShippingOrderID() == shippingOrderToMap.getId()
        mappedShippingOrder.getGlobalContractID() == shippingOrderToMap.getGlobalContractRefId()
        mappedShippingOrder.getShippingOrderStatus() == shippingOrderToMap.getStatus().getName()
        mappedShippingOrder.getShippingCharge() == totalUnitPrice
        mappedShippingOrder.getShippingTax() == totalTax
        mappedShippingOrder.getFulfillmentPartner() == shippingOrderToMap.getFulfillmentPartner()
        mappedShippingOrder.getShippingMethod() == shippingOrderToMap.getRequestedCarrier().getLevelOfService().getId()
        //todo: need to fix date
        //assertDatesAreEqual(mappedShippingOrder.getDeliveryDate(),shippingOrderToMap.getRequestedCarrier().getLevelOfService().getDeliveryDate() )
        mappedShippingOrder.getShippingAddress() != null
        mappedShippingOrder.getShippingOrderLines().size() == shippingOrderToMap.getShippingOrderLines().size()

    }


    ShippingCharge createAShippingCharge(float pst, float gst, float unitPrice){
        ShippingCharge shippingCharge = new ShippingCharge()
        List<Discount> discounts = new ArrayList<>()
        Tax tax = new Tax();
        tax.setPst(pst)
        tax.setGst(gst)

        Discount discount1 = new Discount()
        Discount discount2 = new Discount()
        discount1.setQuantity(1)
        discount1.setUnitValue(2.00f)
        discount2.setQuantity(2)
        discount2.setUnitValue(0.50f)
        discounts.add(discount1)
        discounts.add(discount2)

        shippingCharge.setTax(tax)
        shippingCharge.setUnitPrice(unitPrice)
        shippingCharge.setDiscounts(discounts)

        return shippingCharge

    }



//    def "Test mapping for Order.fsOrderNumber"() {
//
//        given: "A valid FS Order response from Order details"
//
//        and: "mapper is invoked to map the response to the respective internal domain object"
//
//        expect: "Order.fsOrderNumber should be mapped correctly"
//
//        mappedOrder.getFsOrderID() == orderToMap.getId()
//    }
//
//
//    def "Test mapping for Order.ipAddress"() {
//
//        given: "A valid FS Order response from Order details"
//
//        and: "mapper is invoked to map the response to the respective internal domain object"
//
//        expect: "Order.ipAddress should be mapped correctly"
//
//        mappedOrder.getIpAddress() == orderToMap.getIpAddress()
//    }
//
//
//    def "Test mapping for Order.csrSalesRepID"() {
//
//        given: "A valid FS Order response from Order details"
//
//        and: "mapper is invoked to map the response to the respective internal domain object"
//
//        expect: "Order.csrSalesRepID should be mapped correctly"
//
//        // TODO map csrSalesRepID
//    }
//
//
//    def "Test mapping for Order.rewardZoneID"() {
//
//        given: "A valid FS Order response from Order details"
//
//        and: "mapper is invoked to map the response to the respective internal domain object"
//
//        expect: "Order.rewardZoneID should be mapped correctly"
//
//        mappedOrder.getRewardZoneID() == orderToMap.getRewardZone().getRewardZoneId()
//    }
//
//
//    def "Test mapping for Order.webOrderNumber"() {
//
//        given: "A valid FS Order response from Order details"
//
//        and: "mapper is invoked to map the response to the respective internal domain object"
//
//        expect: "Order.webOrderNumber should be mapped correctly"
//
//        mappedOrder.getWebOrderRefID() == orderToMap.getWebOrderRefId()
//    }
//
//
//    def "Test mapping for Order.webOrderCreationDate"() {
//
//        given: "A valid FS Order response from Order details"
//
//        and: "mapper is invoked to map the response to the respective internal domain object"
//
//        expect: "Order.webOrderCreationDate should be mapped correctly"
//
//        assertDatesAreEqual(mappedOrder.getWebOrderCreationDate(), orderToMap.getWebOrderCreationDate())
//    }
//
//
//    def "Test mapping for Order.items"() {
//
//        given: "A valid FS Order response from Order details"
//
//        and: "mapper is invoked to map the response to the respective internal domain object"
//
//        expect: "Order.items should be mapped correctly"
//
//        mappedOrder.getItems().size() == orderToMap.getFsOrderLines().size()
//
//        for (int i = 0; i < orderToMap.getFsOrderLines().size(); i++) {
//
//            Item mappedItem = mappedOrder.getItems().get(i)
//            FSOrderLine fsOrderLineToMap = orderToMap.getFsOrderLines().get(i)
//
//            mappedItem.getFsoLineID() == fsOrderLineToMap.getId()
//            mappedItem.getName() == fsOrderLineToMap.getProduct().getName()
//            mappedItem.getItemUnitPrice() == fsOrderLineToMap.getItemCharge().getUnitPrice()
//            mappedItem.getItemQuantity() == fsOrderLineToMap.getQtyOrdered()
//            mappedItem.getItemTax() == fsOrderLineToMap.getItemCharge().getTax().getGst() + fsOrderLineToMap.getItemCharge().getTax().getPst()
//
//            // TODO map itemDiscounts, category
//        }
//
//    }
//
//
//    def "Test mapping for Order.paymentDetails"() {
//
//        given: "A valid FS Order response from Order details"
//
//        and: "mapper is invoked to map the response to the respective internal domain object"
//
//        expect: "Order.paymentDetails should be mapped correctly"
//
//        PaymentDetails mappedPaymentDetails = mappedOrder.getPaymentDetails()
//        mappedPaymentDetails != null
//        // Credit cards
//        mappedPaymentDetails.creditCards.size() == orderToMap.getPaymentMethodInfo().getCreditCards().size()
//        for (int i = 0; i < orderToMap.getPaymentMethodInfo().getCreditCards().size(); i++) {
//            assertMappedCreditCard(mappedPaymentDetails.creditCards.get(i), orderToMap.getPaymentMethodInfo().getCreditCards().get(i))
//        }
//
//        // Gift cards
//        mappedPaymentDetails.giftCards.size() == orderToMap.getPaymentMethodInfo().getGiftCards().size()
//        for (int i = 0; i < orderToMap.getPaymentMethodInfo().getGiftCards().size(); i++) {
//
//            String mappedGiftCardNumber = mappedPaymentDetails.getGiftCards().get(i).giftCardNumber
//            String giftCardNumberToMap = orderToMap.getPaymentMethodInfo().getGiftCards().get(i).getGiftCardNumber()
//
//            assert mappedGiftCardNumber == giftCardNumberToMap
//        }
//
//        // TODO Paypal mapping
//    }
//
//
//    def "Test mapping for Order.purchaseOrders"() {
//
//        given: "A valid FS Order response from Order details"
//
//        and: "mapper is invoked to map the response to the respective internal domain object"
//
//        expect: "Order.purchaseOrders should be mapped correctly"
//
//        mappedOrder.getPurchaseOrders().size() == orderToMap.getPurchaseOrders().size()
//        for (int i = 0; i < orderToMap.getPurchaseOrders().size(); i++) {
//
//            com.bbyc.orders.model.internal.PurchaseOrder mappedPurchaseOrder = mappedOrder.getPurchaseOrders().get(i)
//            PurchaseOrder purchaseOrderToMap = orderToMap.getPurchaseOrders().get(i)
//
//            mappedPurchaseOrder.getPurchaseOrderID() == purchaseOrderToMap.getId()
//            mappedPurchaseOrder.getPurchaseOrderStatus() == purchaseOrderToMap.getPoSendStatus().getName()
//            mappedPurchaseOrder.getShippingOrderRefID() == purchaseOrderToMap.getShippingOrderRefId()
//        }
//
//    }
//
//
//    def "Test mapping for Order.shippingOrders"() {
//
//        given: "A valid FS Order response from Order details"
//
//        and: "mapper is invoked to map the response to the respective internal domain object"
//
//        expect: "Order.shippingOrders should be mapped correctly"
//
//        mappedOrder.getShippingOrders().size() == orderToMap.getShippingOrders().size()
//        for (int i = 0; i < orderToMap.getShippingOrders().size(); i++) {
//
//            com.bbyc.orders.model.internal.ShippingOrder mappedShippingOrder = mappedOrder.getShippingOrders().get(i)
//            ShippingOrder shippingOrderToMap = orderToMap.getShippingOrders().get(i)
//
//            mappedShippingOrder.getShippingOrderID() == shippingOrderToMap.getId()
//            mappedShippingOrder.getGlobalContractID() == shippingOrderToMap.getGlobalContractRefId()
//            mappedShippingOrder.getFulfillmentPartner() == shippingOrderToMap.getFulfillmentPartner()
//            mappedShippingOrder.getShippingOrderStatus() == shippingOrderToMap.getStatus().getName()
//            mappedShippingOrder.getShippingMethod() == shippingOrderToMap.getRequestedCarrier().getName()
//            assertMappedAddress(mappedShippingOrder.getShippingAddress(), shippingOrderToMap.getShipToAddress())
//
//            mappedShippingOrder.getShippingOrderLines().size() == shippingOrderToMap.getShippingOrderLines().size()
//            for (int j = 0; j < shippingOrderToMap.getShippingOrderLines().size(); j++) {
//
//                com.bbyc.orders.model.internal.ShippingOrderLine mappedShippingOrderLine = mappedShippingOrder.getShippingOrderLines().get(j)
//                ShippingOrderLine shippingOrderLineToMap = shippingOrderToMap.getShippingOrderLines().get(j)
//
//                assertMappedShippingOrderLine(mappedShippingOrderLine, shippingOrderLineToMap)
//            }
//
//            assertDatesAreEqual(mappedShippingOrder.getDeliveryDate(), shippingOrderToMap.getRequestedCarrier().getLevelOfService().getDeliveryDate())
//
//            // TODO map shippingCharge, chargebacks
//
//        }
//
//
//    }
//
//
//    def "Test mapping for Order.shippingOrder charges"() {
//
//        given: "A valid FS Order response from Order details"
//
//        FSOrder fsOrder = new FSOrder()
//
//        ShippingOrder shippingOrder = new ShippingOrder()
//        shippingOrder.setId("1")
//
//
//        shippingOrder.setShippingCharges()
//
//
//    }
//
//
//    void assertMappedCreditCard(CreditCard mappedCreditCard, CreditCardInfo creditCardToMap) {
//
//        if (creditCardToMap != null) {
//            assert mappedCreditCard != null
//        }
//
//        assert mappedCreditCard.creditCardNumber == creditCardToMap.getCreditCardNumber()
//        assert mappedCreditCard.creditCardType == creditCardToMap.getCreditCardType()
//        assertMappedAddress(mappedCreditCard.billingAddress, creditCardToMap.getBillingAddress())
//        assert mappedCreditCard.creditCardExpiryDate == creditCardToMap.getCreditCardExpiryDate()
//
//        // TODO Map avsResponse, cvvResponse, totalAuthorizedAmount, secureValue3D
//    }
//
//
//    void assertMappedShippingOrderLine(com.bbyc.orders.model.internal.ShippingOrderLine mappedShippingOrderLine, ShippingOrderLine shippingOrderLineToMap) {
//
//        if (shippingOrderLineToMap != null) {
//            assert mappedShippingOrderLine != null
//        }
//
//        assert mappedShippingOrderLine.getShippingOrderLineID() == shippingOrderLineToMap.getId()
//        assert mappedShippingOrderLine.getShippingOrderLineStatus() == shippingOrderLineToMap.getStatus().getName()
//        assert mappedShippingOrderLine.getQuantity() == shippingOrderLineToMap.getQtyOrdered()
//        assert mappedShippingOrderLine.getFsoLineRefID() == shippingOrderLineToMap.getFsoLineRefId()
//        // TODO map shippingCharge, shippingTax, shippingDiscount, ehf, ehfTax
//
//    }
//
//
//    void assertMappedAddress(com.bbyc.orders.model.internal.Address mappedAddress, Address addressToMap) {
//
//        if (addressToMap != null) {
//            assert mappedAddress != null
//        }
//
//        assert mappedAddress.address1 == addressToMap.getAddress1()
//        assert mappedAddress.address2 == addressToMap.getAddress2()
//        assert mappedAddress.city == addressToMap.getCity()
//        assert mappedAddress.country == addressToMap.getCountry()
//        assert mappedAddress.email == addressToMap.getEmail()
//        assert mappedAddress.firstName == addressToMap.getFirstName()
//        assert mappedAddress.lastName == addressToMap.getLastName()
//        assert mappedAddress.phoneNumber == addressToMap.getPhone()
//        assert mappedAddress.postalCode == addressToMap.getPostalCode()
//        assert mappedAddress.province == addressToMap.getProvince()
//        assert mappedAddress.secondaryPhoneNumber == addressToMap.getPhone2()
//    }
//
//
    void assertDatesAreEqual(LocalDateTime mappedDate, DateTime dateToMap) {

        if (dateToMap != null) {
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
