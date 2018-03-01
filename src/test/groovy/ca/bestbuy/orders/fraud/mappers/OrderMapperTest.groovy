package ca.bestbuy.orders.fraud.mappers

import ca.bestbuy.orders.fraud.model.client.orderdetails.*
import ca.bestbuy.orders.fraud.model.internal.Item
import ca.bestbuy.orders.fraud.model.internal.Order
import ca.bestbuy.orders.fraud.model.internal.PaymentDetails
import ca.bestbuy.orders.fraud.model.internal.PaymentDetails.CreditCard
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.mapstruct.factory.Mappers
import spock.lang.Shared
import spock.lang.Specification

import java.time.ZonedDateTime

class OrderMapperTest extends Specification {

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

        DateTime dateTimeToMap = new DateTime().withZone(DateTimeZone.forID("America/Los_Angeles"))
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
        assertDatesAreEqual( mappedOrder.getWebOrderCreationDate(), fsOrderToMap.getWebOrderCreationDate())
        mappedOrder.getRewardZoneID() == fsOrderToMap.getRewardZone().getRewardZoneId()
        mappedOrder.getItems().size() == fsOrderToMap.getFsOrderLines().size()
        mappedOrder.getPurchaseOrders().size() == fsOrderToMap.getPurchaseOrders().size()
        mappedOrder.getShippingOrders().size() == fsOrderToMap.getShippingOrders().size()
        mappedOrder.getPaymentDetails() != null


    }



    def "Test Item Mapper"(){

        given: "a valid fsorderline"
        FSOrderLine fsOrderLineToMap = new FSOrderLine()
        fsOrderLineToMap.setId("fsoLineId")

        Product product = new Product()
        product.setName("product name")
        fsOrderLineToMap.setProduct(product)

        ItemCharge itemCharge = new ItemCharge()
        List<ItemChargeDiscount> discounts = new ArrayList<>()
        ItemChargeDiscount discount1 = new ItemChargeDiscount()
        discount1.setUnitValue("1.22")
        discount1.setQuantity(1)
        ItemChargeDiscount discount2 = new ItemChargeDiscount()
        discount2.setUnitValue("2.22")
        discount2.setQuantity(2)
        discount2.setCode("SP")
        discounts.add(discount1)
        discounts.add(discount2)
        itemCharge.setDiscounts(discounts)

        Tax tax = new Tax()
        tax.setGst("0.05")
        tax.setPst("0.06")
        itemCharge.setTax(tax)
        itemCharge.setUnitPrice("2.00")
        fsOrderLineToMap.setItemCharge(itemCharge)

        fsOrderLineToMap.setQtyOrdered(5)

        BigDecimal totalTaxes = new BigDecimal("0.11")
        BigDecimal totalDiscount = new BigDecimal("5.66")
        BigDecimal staffDiscount = new BigDecimal("4.44")
        BigDecimal itemPrice = new BigDecimal("2")

        when: "the order mapper is called to map the FSOrderLine to our internal Item object"

        Item mappedItem = orderDetailsMapper.mapItem(fsOrderLineToMap)

        then: "our internal domain Item object were correctly mapped to"

        mappedItem.getFsoLineID() == fsOrderLineToMap.getId()
        mappedItem.getName() == fsOrderLineToMap.getProduct().getName()
        mappedItem.getItemUnitPrice() == itemPrice
        mappedItem.getItemQuantity() == fsOrderLineToMap.getQtyOrdered()
        mappedItem.getItemTax() == totalTaxes
        mappedItem.getItemTotalDiscount() == totalDiscount
        mappedItem.getStaffDiscount() == staffDiscount
    }


    def "Test Shipping Order Line Mapper"() {

        given: "A valid FS Order ShippingOrderLine object"

        ShippingOrderLine shippingOrderLineToMap = new ShippingOrderLine()

        Status status = new Status()
        status.setName("STATUS")
        status.setDate(new DateTime().withZone(DateTimeZone.forID("America/Los_Angeles")))
        status.setDescription("DESCRIPTION")
        shippingOrderLineToMap.setStatus(status)

        shippingOrderLineToMap.setId("SHIPPINGORDERLINEID")
        shippingOrderLineToMap.setFsoLineRefId("FSOLINEREFID")
        shippingOrderLineToMap.setQtyOrdered(1)
        shippingOrderLineToMap.setUnitPrice("1.09")
        shippingOrderLineToMap.setAvailability("AVAILABLE")
        shippingOrderLineToMap.setQtyCancelled(3)
        shippingOrderLineToMap.setQtyShipped(2)
        shippingOrderLineToMap.setEtaDate(DateTime.now())
        shippingOrderLineToMap.setInventoryReservationRefId("INVENTORYRESERVATIONREFID")
        shippingOrderLineToMap.setCancellable(true)

        // Shipping charge 1
        ShippingCharge shippingCharge = new ShippingCharge()
        shippingCharge.setUnitPrice("2.54")

        Tax tax = new Tax()
        tax.setGst("1.23")
        tax.setPst("4.56")
        shippingCharge.setTax(tax)

        Discount discount = new Discount()
        discount.setUnitValue("16.10")
        discount.setQuantity(1)

        Discount discount2 = new Discount()
        discount2.setUnitValue("1.50")
        discount2.setQuantity(2)

        Discount discount3 = new Discount()
        discount3.setUnitValue("1.25")
        discount3.setQuantity(1)

        shippingCharge.setDiscounts(Arrays.asList(discount, discount2))

        // Shipping charge 2
        ShippingCharge shippingCharge2 = new ShippingCharge()
        shippingCharge2.setUnitPrice("34.56")

        Tax tax2 = new Tax()
        tax2.setGst("1.12")
        tax2.setPst("2.12")
        shippingCharge2.setTax(tax2)

        shippingCharge2.setDiscounts(Arrays.asList(discount3))

        shippingOrderLineToMap.setShippingCharges(Arrays.asList(shippingCharge, shippingCharge2))


        // Surcharge 1
        Surcharge surcharge = new Surcharge()
        surcharge.setTax(tax)
        surcharge.setTotalValue("15.55")

        Surcharge surcharge2 = new Surcharge()
        surcharge2.setTax(tax)
        surcharge2.setTotalValue("14.56")

        shippingOrderLineToMap.setSurcharges(Arrays.asList(surcharge, surcharge2))

        BigDecimal totalShippingCharge = new BigDecimal("37.1")
        BigDecimal totalShippingDiscount = new BigDecimal("20.35")
        BigDecimal totalShippingTax = new BigDecimal("9.03")
        BigDecimal totalEhf = new BigDecimal("30.11")
        BigDecimal totalEhfTax = new BigDecimal("11.58")


        when: "OrderMapper.mapShippingOrderLine() is invoked on the FS Order ShippingOrderLine object"

        ca.bestbuy.orders.fraud.model.internal.ShippingOrderLine mappedShippingOrderLine = orderDetailsMapper.mapShippingOrderLine(shippingOrderLineToMap)

        then: "ShippingOrderLine object returned should be mapped correctly"

        mappedShippingOrderLine.getFsoLineRefID() == shippingOrderLineToMap.getFsoLineRefId()
        mappedShippingOrderLine.getShippingOrderLineID() == shippingOrderLineToMap.getId()
        mappedShippingOrderLine.getShippingOrderLineStatus() == shippingOrderLineToMap.getStatus().getName()
        mappedShippingOrderLine.getQuantity() == shippingOrderLineToMap.getQtyOrdered()
        mappedShippingOrderLine.getShippingCharge() == totalShippingCharge
        mappedShippingOrderLine.getShippingDiscount() == totalShippingDiscount
        mappedShippingOrderLine.getShippingTax() == totalShippingTax
        mappedShippingOrderLine.getEhf() == totalEhf
        mappedShippingOrderLine.getEhfTax() == totalEhfTax

    }


    def "Test Payment Details Mapper"() {

        given: "A valid FS Order PaymentMethodInfo object"

        PaymentMethodInfo paymentMethodInfoToMap = new PaymentMethodInfo()
        paymentMethodInfoToMap.setCreditCards(Arrays.asList(new CreditCardInfo(), new CreditCardInfo()))
        paymentMethodInfoToMap.setGiftCards(Arrays.asList(new GiftCardInfo()))

        when: "OrderMapper.mapCreditCard() is invoked on the FS Order CreditCardInfo object"

        PaymentDetails mappedPaymentDetails = orderDetailsMapper.mapPaymentDetails(paymentMethodInfoToMap)

        then: "PaymentDetails object returned should be mapped correctly"

        mappedPaymentDetails.getCreditCards().size() == paymentMethodInfoToMap.getCreditCards().size()
        mappedPaymentDetails.getGiftCards().size() == paymentMethodInfoToMap.getGiftCards().size()

        // TODO - Map payPal
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
        ca.bestbuy.orders.fraud.model.internal.PurchaseOrder mappedPurchaseOrder = orderDetailsMapper.mapPurchaseOrder(purchaseOrderToMap)

        then: "our internal domain Purchased Order object were correctly mapped to"

        mappedPurchaseOrder.getPurchaseOrderID() == purchaseOrderToMap.getId()
        mappedPurchaseOrder.getPurchaseOrderStatus() == purchaseOrderToMap.getPoSendStatus().getName()
        mappedPurchaseOrder.getShippingOrderRefID() == purchaseOrderToMap.getShippingOrderRefId()

    }


    def "Test Shipping Order Mapper"() {

        given: "a valid shipping order"

        ShippingOrder shippingOrderToMap = new ShippingOrder()
        shippingOrderToMap.setId("shippingOrderId")
        shippingOrderToMap.setGlobalContractRefId("globalContractRefId")

        Status shippingOrderStatus = new Status()
        shippingOrderStatus.setName("shipping order status")
        shippingOrderToMap.setStatus(shippingOrderStatus)

        float unitPrice1 = 1.00f
        float unitPrice2 = 2.00f
        float unitPrice3 = 3.00f

        List<ShippingCharge> shippingCharges = new ArrayList<>()
        ShippingCharge shippingCharge1 = createAShippingCharge(0.07f, 0.07f, unitPrice1)
        ShippingCharge shippingCharge2 = createAShippingCharge(0.08f, 0.08f, unitPrice2)
        ShippingCharge shippingCharge3 = createAShippingCharge(0.09f, 0.09f, unitPrice3)

        shippingCharges.add(shippingCharge1)
        shippingCharges.add(shippingCharge2)
        shippingCharges.add(shippingCharge3)
        shippingOrderToMap.setShippingCharges(shippingCharges)
        shippingOrderToMap.setFulfillmentPartner("fulfillmentPartner")

        RequestedCarrier requestedCarrier = new RequestedCarrier()
        LevelOfService levelOfService = new LevelOfService()
        levelOfService.setId("level of service id")

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        DateTime deliveryDate = formatter.parseDateTime("2018-02-19T09:15:52Z")

        levelOfService.setDeliveryDate(deliveryDate)
        requestedCarrier.setLevelOfService(levelOfService)
        shippingOrderToMap.setRequestedCarrier(requestedCarrier)

        Address shippingAddress = new Address()
        shippingOrderToMap.setShipToAddress(shippingAddress)

        shippingOrderToMap.setShippingOrderLines(Arrays.asList(new ShippingOrderLine(), new ShippingOrderLine()))

        BigDecimal totalUnitPrice = new BigDecimal("6")
        BigDecimal totalTax = new BigDecimal("0.48")
        BigDecimal totalDiscounts = new BigDecimal("9.0")

        when: "the order mapper is called to map the Shipping Order object to our internal domain object"

        ca.bestbuy.orders.fraud.model.internal.ShippingOrder mappedShippingOrder = orderDetailsMapper.mapShippingOrder(shippingOrderToMap)

        then: "our internal domain Shipping Order object were correctly mapped to"

        mappedShippingOrder.getShippingOrderID() == shippingOrderToMap.getId()
        mappedShippingOrder.getGlobalContractID() == shippingOrderToMap.getGlobalContractRefId()
        mappedShippingOrder.getShippingOrderStatus() == shippingOrderToMap.getStatus().getName()
        mappedShippingOrder.getShippingCharge() == totalUnitPrice
        mappedShippingOrder.getShippingTax() == totalTax
        mappedShippingOrder.getShippingDiscount() == totalDiscounts
        mappedShippingOrder.getFulfillmentPartner() == shippingOrderToMap.getFulfillmentPartner()
        mappedShippingOrder.getShippingMethod() == shippingOrderToMap.getRequestedCarrier().getLevelOfService().getId()
        assertDatesAreEqual(mappedShippingOrder.getDeliveryDate(),shippingOrderToMap.getRequestedCarrier().getLevelOfService().getDeliveryDate() )
        mappedShippingOrder.getShippingAddress() != null
        mappedShippingOrder.getShippingOrderLines().size() == shippingOrderToMap.getShippingOrderLines().size()
    }

    def "Test Credit Card Mapper"() {

        given: "A valid FS Order CreditCardInfo object"

        CreditCardInfo creditCardToMap = new CreditCardInfo()
        creditCardToMap.setActive(true)
        creditCardToMap.setInvoicedAmount("125.99")

        Address billingAddress = new Address()
        billingAddress.setFirstName("FIRSTNAME")
        billingAddress.setLastName("LASTNAME")
        billingAddress.setEmail("EMAIL")
        billingAddress.setCity("CITY")
        billingAddress.setProvince("PROVINCE")
        billingAddress.setCountry("COUNTRY")
        billingAddress.setPostalCode("POSTALCODE")
        billingAddress.setPhone("PHONE")
        billingAddress.setPhoneExt("PHONEEXT")
        billingAddress.setPhone2("PHONE2")
        billingAddress.setPhone2Ext("PHONE2EXT")
        billingAddress.setApartmentNumber("APTNUMBER")
        billingAddress.setAddress1("ADDRESS1")
        billingAddress.setAddress2("ADDRESS2")
        billingAddress.setAddress3("ADDRESS3")
        billingAddress.setFaxNumber("FAX")
        creditCardToMap.setBillingAddress(billingAddress)

        creditCardToMap.setCreditCardExpiryDate("EXPIRYDATE")
        creditCardToMap.setCreditCardHolderName("CREDITCARDHOLDERNAME")
        creditCardToMap.setCreditCardNumber("CREDITCARDNUMBER")
        creditCardToMap.setCreditCardType("CREDITCARDTYPE")

        Status avsStatus = new Status()
        avsStatus.setName("AVSSTATUSNAME")
        creditCardToMap.setAvsStatus(avsStatus)

        creditCardToMap.setPriority("1")


        when: "OrderMapper.mapCreditCard() is invoked on the FS Order CreditCardInfo object"

        CreditCard mappedCreditCard = orderDetailsMapper.mapCreditCard(creditCardToMap)

        then: "CreditCard object returned should be mapped correctly"

        mappedCreditCard.creditCardNumber == creditCardToMap.getCreditCardNumber()
        mappedCreditCard.creditCardExpiryDate == creditCardToMap.getCreditCardExpiryDate()
        mappedCreditCard.creditCardType == creditCardToMap.getCreditCardType()
        assertMappedAddress(mappedCreditCard.billingAddress, creditCardToMap.getBillingAddress())

        // TODO - Map totalAuthorizedAmount, creditCard3dSecureValue, creditCardAvsResponse, creditCardCvvResponse
    }


    def "Test Gift Card Mapper"() {

        given: "A valid FS Order GiftCardInfo object"

        GiftCardInfo giftCardToMap = new GiftCardInfo()
        giftCardToMap.setGiftCardNumber("GIFTCARDNUMBER")
        giftCardToMap.setGiftCardType("GIFTCARDTYPE")
        giftCardToMap.setActive(true)
        giftCardToMap.setGiftCardSecureCode("SECURECODE")
        giftCardToMap.setInvoicedAmount("12.99")


        when: "OrderMapper.mapGiftCard() is invoked on the FS Order GiftCardInfo object"

        PaymentDetails.GiftCard mappedGiftCard = orderDetailsMapper.mapGiftCard(giftCardToMap)

        then: "GiftCard object returned should be mapped correctly"

        mappedGiftCard.giftCardNumber == giftCardToMap.getGiftCardNumber()
    }


    def "Test Address Mapper"() {

        given: "A valid FS Order Address object"

        Address addressToMap = new Address()
        addressToMap.setFirstName("FIRSTNAME")
        addressToMap.setLastName("LASTNAME")
        addressToMap.setEmail("EMAIL")
        addressToMap.setCity("CITY")
        addressToMap.setProvince("PROVINCE")
        addressToMap.setCountry("COUNTRY")
        addressToMap.setPostalCode("POSTALCODE")
        addressToMap.setPhone("PHONE")
        addressToMap.setPhoneExt("PHONEEXT")
        addressToMap.setPhone2("PHONE2")
        addressToMap.setPhone2Ext("PHONE2EXT")
        addressToMap.setApartmentNumber("APTNUMBER")
        addressToMap.setAddress1("ADDRESS1")
        addressToMap.setAddress2("ADDRESS2")
        addressToMap.setAddress3("ADDRESS3")
        addressToMap.setFaxNumber("FAX")

        when: "OrderMapper.mapAddress() is invoked on the FS Order Address object"

        ca.bestbuy.orders.fraud.model.internal.Address mappedAddress = orderDetailsMapper.mapAddress(addressToMap)

        then: "Address object returned should be mapped correctly"

        assertMappedAddress(mappedAddress, addressToMap)
    }


    void assertMappedAddress(ca.bestbuy.orders.fraud.model.internal.Address mappedAddress, Address addressToMap) {

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


    void assertDatesAreEqual(ZonedDateTime mappedDate, DateTime dateToMap) {

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


    ShippingCharge createAShippingCharge(float pst, float gst, float unitPrice) {
        ShippingCharge shippingCharge = new ShippingCharge()
        List<Discount> discounts = new ArrayList<>()
        Tax tax = new Tax()
        tax.setPst(Float.toString(pst))
        tax.setGst(Float.toString(gst))

        Discount discount1 = new Discount()
        Discount discount2 = new Discount()
        discount1.setQuantity(1)
        discount1.setUnitValue("2.0")
        discount2.setQuantity(2)
        discount2.setUnitValue("0.50")
        discounts.add(discount1)
        discounts.add(discount2)

        shippingCharge.setTax(tax)
        shippingCharge.setUnitPrice(Float.toString(unitPrice))
        shippingCharge.setDiscounts(discounts)

        return shippingCharge

    }

}
