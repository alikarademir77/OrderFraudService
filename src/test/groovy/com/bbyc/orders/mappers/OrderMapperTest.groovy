package com.bbyc.orders.mappers

import com.bbyc.orders.model.client.orderdetails.*
import com.bbyc.orders.model.internal.PaymentDetails
import com.bbyc.orders.model.internal.PaymentDetails.CreditCard
import org.joda.time.DateTime
import org.mapstruct.factory.Mappers
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

class OrderMapperTest extends Specification {

    @Shared
    OrderMapper orderDetailsMapper


    def setupSpec(){
        orderDetailsMapper = Mappers.getMapper(OrderMapper.class)
    }


    def "Test mapShippingOrderLine()"() {

        given: "A valid FS Order ShippingOrderLine object"

        ShippingOrderLine shippingOrderLineToMap = new ShippingOrderLine()

        Status status = new Status()
        status.setName("STATUS")
        status.setDate(DateTime.now())
        status.setDescription("DESCRIPTION")
        shippingOrderLineToMap.setStatus(status)

        shippingOrderLineToMap.setId("SHIPPINGORDERLINEID")
        shippingOrderLineToMap.setFsoLineRefId("FSOLINEREFID")
        shippingOrderLineToMap.setQtyOrdered(1)
        shippingOrderLineToMap.setUnitPrice(1.09F)
        shippingOrderLineToMap.setAvailability("AVAILABLE")
        shippingOrderLineToMap.setQtyCancelled(3)
        shippingOrderLineToMap.setQtyShipped(2)
        shippingOrderLineToMap.setEtaDate(DateTime.now())
        shippingOrderLineToMap.setInventoryReservationRefId("INVENTORYRESERVATIONREFID")
        shippingOrderLineToMap.setCancellable(true)

        // Shipping charge 1
        ShippingCharge shippingCharge = new ShippingCharge()
        shippingCharge.setUnitPrice(2.54F)

        Tax tax = new Tax()
        tax.setGst(1.23F)
        tax.setPst(4.56F)
        shippingCharge.setTax(tax)

        Discount discount = new Discount()
        discount.setUnitValue(15.99F)
        discount.setQuantity(1)

        Discount discount2 = new Discount()
        discount2.setUnitValue(1.45F)
        discount2.setQuantity(2)

        Discount discount3 = new Discount()
        discount3.setUnitValue(34.56F)
        discount3.setQuantity(1)

        shippingCharge.setDiscounts(Arrays.asList(discount, discount2))

        // Shipping charge 2
        ShippingCharge shippingCharge2 = new ShippingCharge()
        shippingCharge2.setUnitPrice(34.56F)

        Tax tax2 = new Tax()
        tax2.setGst(1.12F)
        tax2.setPst(2.12F)
        shippingCharge2.setTax(tax2)

        shippingCharge2.setDiscounts(Arrays.asList(discount3))

        shippingOrderLineToMap.setShippingCharges(Arrays.asList(shippingCharge, shippingCharge2))


        // Surcharge 1
        Surcharge surcharge = new Surcharge()
        surcharge.setTax(tax)
        surcharge.setTotalValue(15.66F)

        Surcharge surcharge2 = new Surcharge()
        surcharge2.setTax(tax)
        surcharge2.setTotalValue(14.67F)

        shippingOrderLineToMap.setSurcharges(Arrays.asList(surcharge, surcharge, surcharge2))

        float totalShippingCharge = shippingCharge.getUnitPrice() + shippingCharge2.getUnitPrice()
        float totalShippingDiscount = (discount.getUnitValue() * discount.getQuantity()) + (discount2.getUnitValue() * discount2.getQuantity()) + (discount3.getUnitValue() * discount3.getQuantity())
        float totalShippingTax = (tax.getPst() + tax.getGst()) + (tax2.getPst() + tax2.getGst())
        float totalEhf = surcharge.getTotalValue() + surcharge.getTotalValue() + surcharge2.getTotalValue()
        float totalEhfTax = (tax.getPst() + tax.getGst())*3


        when: "OrderMapper.mapShippingOrderLine() is invoked on the FS Order ShippingOrderLine object"

        com.bbyc.orders.model.internal.ShippingOrderLine mappedShippingOrderLine = orderDetailsMapper.mapShippingOrderLine(shippingOrderLineToMap)

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


    def "Test mapPaymentDetails()"() {

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


    def "Test mapCreditCard()"() {

        given: "A valid FS Order CreditCardInfo object"

        CreditCardInfo creditCardToMap = new CreditCardInfo()
        creditCardToMap.setActive(true)
        creditCardToMap.setInvoicedAmount(125.99F)

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


    def "Test mapGiftCard()"() {

        given: "A valid FS Order GiftCardInfo object"

        GiftCardInfo giftCardToMap = new GiftCardInfo()
        giftCardToMap.setGiftCardNumber("GIFTCARDNUMBER")
        giftCardToMap.setGiftCardType("GIFTCARDTYPE")
        giftCardToMap.setActive(true)
        giftCardToMap.setGiftCardSecureCode("SECURECODE")
        giftCardToMap.setInvoicedAmount(12.99F)


        when: "OrderMapper.mapGiftCard() is invoked on the FS Order GiftCardInfo object"

        PaymentDetails.GiftCard mappedGiftCard = orderDetailsMapper.mapGiftCard(giftCardToMap)

        then: "GiftCard object returned should be mapped correctly"

        mappedGiftCard.giftCardNumber == giftCardToMap.getGiftCardNumber()
    }


    def "Test mapAddress()"() {

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

        com.bbyc.orders.model.internal.Address mappedAddress = orderDetailsMapper.mapAddress(addressToMap)

        then: "Address object returned should be mapped correctly"

        assertMappedAddress(mappedAddress, addressToMap)
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
