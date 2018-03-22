package ca.bestbuy.orders.fraud.mappers

import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.AddressDetails
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.CaPaymentMethod
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ChargeBack
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ItemStatus
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.PaymentMethodType
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.PaymentMethods
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.PurchaseOrderStatus
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.SalesChannels
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.TransactionData
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.TransactionType
import ca.bestbuy.orders.fraud.model.internal.Address
import ca.bestbuy.orders.fraud.model.internal.Chargeback
import ca.bestbuy.orders.fraud.model.internal.Item
import ca.bestbuy.orders.fraud.model.internal.Order
import ca.bestbuy.orders.fraud.model.internal.PaymentDetails
import ca.bestbuy.orders.fraud.model.internal.PurchaseOrder
import ca.bestbuy.orders.fraud.model.internal.ShippingOrder
import ca.bestbuy.orders.fraud.model.internal.ShippingOrderLine
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.mapstruct.factory.Mappers
import spock.lang.Shared
import spock.lang.Specification

import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar


class TASRequestXMLMapperTest extends Specification {

    @Shared
    TASRequestXMLMapper xmlMapper

    def setupSpec() {
        xmlMapper = Mappers.getMapper(TASRequestXMLMapper.class)
    }


    def "test TransactionData mapper"() {

        given:

        Order order = new Order()
        order.setWebOrderRefID("webOrderRefId")
        DateTime dateTimeToMap = new DateTime().withZone(DateTimeZone.forID("America/Los_Angeles"))
        order.setWebOrderCreationDate(dateTimeToMap)
        order.setCsrSalesRepID("csrSalesRepId")
        order.setIpAddress("ipAddress")
        order.setSalesChannel("CSR_APP")


        //shipping order
        List<ShippingOrder> shippingOrders = new ArrayList<>();
        shippingOrders.add(createAShippingOrder("shippingOrder1", "fsoLineId"));
        shippingOrders.add(createAShippingOrder("shippingOrder2", "fsoLineId"));
        shippingOrders.add(createAShippingOrder("shippingOrder3", "fsoLineId"));
        order.setShippingOrders(shippingOrders)

        //item
        List<Item> items = new ArrayList<>();
        items.add(createAnItem("fsoLineId"));
        items.add(createAnItem("fsoLineId"));
        order.setItems(items)

        //purchase order
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        purchaseOrders.add(createAPurchaseOrder("shippingOrderRefId"))
        purchaseOrders.add(createAPurchaseOrder("shippingOrderRefId"))
        order.setPurchaseOrders(purchaseOrders)


        //payment details
        //create 3 credit card payments
        PaymentDetails paymentDetails = new PaymentDetails();
        List<PaymentDetails.CreditCard> creditCards = new ArrayList<>()
        creditCards.add(createACreditCard())
        creditCards.add(createACreditCard())
        creditCards.add(createACreditCard())

        //create 2 gift card payments
        List<PaymentDetails.GiftCard> giftCards = new ArrayList<>()
        giftCards.add(createAGiftCard())
        giftCards.add(createAGiftCard())

        //create a paypal payment
        PaymentDetails.PayPal paypal = createAPaypal()

        paymentDetails.setCreditCards(creditCards)
        paymentDetails.setGiftCards(giftCards)
//        paymentDetails.setPayPal(paypal) todo: this is now a list of paypals

        order.setPaymentDetails(paymentDetails);


        when:

        TransactionData mappedTxnData = xmlMapper.mapTransactionData(order)


        List<ca.bestbuy.orders.fraud.model.internal.ShippingOrder> shippingOrdersToMap = order.getShippingOrders();
        BigDecimal orderTotalTxnAmt = new BigDecimal(0.00);
        for(ca.bestbuy.orders.fraud.model.internal.ShippingOrder shippingOrderToMap : shippingOrdersToMap){
            orderTotalTxnAmt = orderTotalTxnAmt.add(shippingOrderToMap.getTotalAuthorizedAmount());

        }

        then:

        int amtOfPaypalPayments = 0;
        //todo: paypal should now be a list of paypals
      /*  if(order.getPaymentDetails() != null  && order.getPaymentDetails().getPayPal() != null){
            amtOfPaypalPayments = 1;
        }
        */


        mappedTxnData.getTransactionId() == order.getFsOrderID()
        mappedTxnData.getWebOrderId() == order.getWebOrderRefID()
        mappedTxnData.transactionType == TransactionType.ORDER
        assertGregorianDateAndDateTimeAreEqual(mappedTxnData.getOrderDateTime(),order.getWebOrderCreationDate())
        mappedTxnData.getTransactionTotalAmount() == orderTotalTxnAmt.toString()
        mappedTxnData.getCsrSalesRep() == order.getCsrSalesRepID()
        mappedTxnData.getEnterpriseCutId() == order.getEnterpriseCustomerId()
        mappedTxnData.getSalesChannel() == SalesChannels.valueOf(order.getSalesChannel())
        mappedTxnData.getIpAddress() == order.getIpAddress()
        mappedTxnData.getOrderMessage() == order.getOrderMessage()
        mappedTxnData.getPaymentMethods().getPaymentMethod().size() == order.getPaymentDetails().getCreditCards().size() + order.getPaymentDetails().getGiftCards().size() + amtOfPaypalPayments
        mappedTxnData.getMember().getMemberId() == order.getRewardZoneID()
        mappedTxnData.getShippingOrders().getShippingOrder().size() == order.getShippingOrders().size()
        mappedTxnData.getItems().getItem().size() == order.getItems().size()

        //todo: fix paypal stuff because paypal should now be a list of paypals
        //doExtraPaymentMethodsAssertions(mappedTxnData.getPaymentMethods(), order.getPaymentDetails())
        doChargebacksMapperAssertions(mappedTxnData, order.getShippingOrders())

    }


    def "test shipping order mapping"(){
        given:
        Order orderToMap = new Order()

        List<PurchaseOrder> purchaseOrdersToMap = new ArrayList<>()
        purchaseOrdersToMap.add(createAPurchaseOrder("shippingOrder1"))
        purchaseOrdersToMap.add(createAPurchaseOrder("shippingOrder3"))
        purchaseOrdersToMap.add(createAPurchaseOrder("shippingOrder2"))
        purchaseOrdersToMap.add(createAPurchaseOrder("shippingOrder2"))
        purchaseOrdersToMap.add(createAPurchaseOrder("shippingOrder1"))


        List<ShippingOrder> shippingOrdersToMap = new ArrayList<>()
        shippingOrdersToMap.add(createAShippingOrder("shippingOrder1", "fsoLineId"))
        shippingOrdersToMap.add(createAShippingOrder("shippingOrder2", "fsoLineId"))
        shippingOrdersToMap.add(createAShippingOrder("shippingOrder3", "fsoLineId"))


        orderToMap.setPurchaseOrders(purchaseOrdersToMap)
        orderToMap.setShippingOrders(shippingOrdersToMap)

        when:

        TransactionData mappedTransactionData = xmlMapper.mapTransactionData(orderToMap)
        List<ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ShippingOrder> mappedShippingOrders = mappedTransactionData.getShippingOrders().getShippingOrder()

        then:

        for(int i = 0; i < mappedShippingOrders.size(); i++){
            mappedShippingOrders.get(i).getShippingOrderId() == shippingOrdersToMap.get(i).getShippingOrderID();
            mappedShippingOrders.get(i).getGlobalContractId() == shippingOrdersToMap.get(i).getGlobalContractID();
            mappedShippingOrders.get(i).getShippingOrderStatus().toString() == shippingOrdersToMap.get(i).getShippingOrderStatus();
            mappedShippingOrders.get(i).getShippingCharge().toBigDecimal() == shippingOrdersToMap.get(i).getShippingCharge();
            assertMappedAddress(mappedShippingOrders.get(i).getShippingDetails().getShippingAddress(),shippingOrdersToMap.get(i).getShippingAddress())
            mappedShippingOrders.get(i).getShippingDetails().getShippingDeadline() == shippingOrdersToMap.get(i).getDeliveryDate().toString()
            mappedShippingOrders.get(i).getShippingDetails().getShippingMethod() == shippingOrdersToMap.get(i).getShippingMethod()
            mappedShippingOrders.get(i).getFulfillmentPartner() == mappedShippingOrders.get(i).getFulfillmentPartner();
            doShippingOrder_PurchaseOrderDetailsAssertions(mappedShippingOrders.get(i), purchaseOrdersToMap)

        }

    }

    void doShippingOrder_PurchaseOrderDetailsAssertions(ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ShippingOrder shippingOrder, List<PurchaseOrder> purchaseOrdersToMap) {
        for(PurchaseOrder purchaseOrder : purchaseOrdersToMap){

            if(shippingOrder.getShippingOrderId().equals(purchaseOrder.getShippingOrderRefID())){
                assert shippingOrder.getPurchaseOrderId() == purchaseOrder.getPurchaseOrderID()
                assert shippingOrder.getPurchaseOrderStatus() == PurchaseOrderStatus.valueOf(purchaseOrder.getPurchaseOrderStatus())
            }

        }

    }


    def "test item mapper"(){

        given:

        Order orderToMap = new Order()
        List<Item> itemsToMap = new ArrayList<>()
        itemsToMap.add(createAnItem("fsoLine1"))
        itemsToMap.add(createAnItem("fsoLine3"))
        itemsToMap.add(createAnItem("fsoLine2"))


        orderToMap.setItems(itemsToMap)


        List<ShippingOrder> shippingOrdersToMap = new ArrayList<>()
        shippingOrdersToMap.add(createAShippingOrder("shippingOrder1", "fsoLine1"))
        shippingOrdersToMap.add(createAShippingOrder("shippingOrder2", "fsoLine2"))
        shippingOrdersToMap.add(createAShippingOrder("shippingOrder3", "fsoLine3"))
        orderToMap.setShippingOrders(shippingOrdersToMap)

        when:
        TransactionData mappedTransactionData = xmlMapper.mapTransactionData(orderToMap)
        List<ca.bestbuy.orders.fraud.model.client.accertify.wsdl.Item> mappedItems = mappedTransactionData.getItems().getItem()

        then:
        for(int i = 0; i < mappedItems.size() ; i++){
            mappedItems.get(i).getFsOrderLineId() == itemsToMap.get(i).getFsoLineID()
            mappedItems.get(i).getItemStatus() == ItemStatus.valueOf(itemsToMap.get(i).getItemStatus())
            mappedItems.get(i).getItemSkuNumber() == itemsToMap.get(i).getItemSkuNumber()
            mappedItems.get(i).getItemSkuDescription() == itemsToMap.get(i).getItemSkuDescription()
            mappedItems.get(i).getItemPrice() == itemsToMap.get(i).getItemUnitPrice().toString()
            mappedItems.get(i).getItemQuantity() == Integer.toString(itemsToMap.get(i).getItemQuantity())
            mappedItems.get(i).getItemSkuCategory() == itemsToMap.get(i).getCategory()
            mappedItems.get(i).getStaffDiscount() == itemsToMap.get(i).getStaffDiscount().toString()
            mappedItems.get(i).getPostCaptureDiscount() == itemsToMap.get(i).getPostCaptureDiscount().toString()
            mappedItems.get(i).getItemSaleTax() == itemsToMap.get(i).getItemTax().toString()
            doItemShippingOrderIdAssertion(mappedItems.get(i), orderToMap.getShippingOrders())
        }


    }


    def "test empty order object"(){
        given:

        Order order = new Order()

        when:

        TransactionData mappedTxnData = xmlMapper.mapTransactionData(order)

        then:

        mappedTxnData.getTransactionId() == null
        mappedTxnData.getWebOrderId() == null
        mappedTxnData.transactionType == TransactionType.ORDER
        mappedTxnData.getOrderDateTime() == null
        mappedTxnData.getTransactionTotalAmount() == "0"
        mappedTxnData.getCsrSalesRep() == null
        mappedTxnData.getEnterpriseCutId() == null
        mappedTxnData.getSalesChannel() == null
        mappedTxnData.getIpAddress() == null
        mappedTxnData.getOrderMessage() == null
        mappedTxnData.getMember().getMemberId() == null
        mappedTxnData.getPaymentMethods() == null
        mappedTxnData.getChargeBacks() == null

    }

    def "test empty lists for Transaction Data"(){
        given:

        Order order = new Order()
        order.setWebOrderRefID("webOrderRefId")
        DateTime dateTimeToMap = new DateTime().withZone(DateTimeZone.forID("America/Los_Angeles"))
        order.setWebOrderCreationDate(dateTimeToMap)
        order.setCsrSalesRepID("csrSalesRepId")
        order.setIpAddress("ipAddress")
        order.setSalesChannel("CSR_APP")


        //shipping order
        List<ShippingOrder> shippingOrders = new ArrayList<>();
        order.setShippingOrders(shippingOrders)

        //item
        List<Item> items = new ArrayList<>();
        order.setItems(items)

        //purchase order
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        order.setPurchaseOrders(purchaseOrders)


        //payment details
        //create 3 credit card payments
        PaymentDetails paymentDetails = new PaymentDetails();
        List<PaymentDetails.CreditCard> creditCards = new ArrayList<>()


        //create 2 gift card payments
        List<PaymentDetails.GiftCard> giftCards = new ArrayList<>()


        //create a paypal payment
        PaymentDetails.PayPal paypal = createAPaypal()

        paymentDetails.setCreditCards(creditCards)
        paymentDetails.setGiftCards(giftCards)
//        paymentDetails.setPayPal(paypal) //        paymentDetails.setPayPal(paypal) todo: this is now a list of paypals


        order.setPaymentDetails(paymentDetails);

        when:

        TransactionData mappedTxnData = xmlMapper.mapTransactionData(order)


        List<ca.bestbuy.orders.fraud.model.internal.ShippingOrder> shippingOrdersToMap = order.getShippingOrders();
        BigDecimal orderTotalTxnAmt = new BigDecimal(0.00);
        for(ca.bestbuy.orders.fraud.model.internal.ShippingOrder shippingOrderToMap : shippingOrdersToMap){
            orderTotalTxnAmt = orderTotalTxnAmt.add(shippingOrderToMap.getTotalAuthorizedAmount());

        }

        then:

        int amtOfPaypalPayments = 0;
        /* //todo: paypal should now be a list of paypals
         if(order.getPaymentDetails() != null && order.getPaymentDetails().getPayPal() != null){
            amtOfPaypalPayments = 1;
        }*/


        mappedTxnData.getTransactionId() == order.getFsOrderID()
        mappedTxnData.getWebOrderId() == order.getWebOrderRefID()
        mappedTxnData.transactionType == TransactionType.ORDER
        assertGregorianDateAndDateTimeAreEqual(mappedTxnData.getOrderDateTime(),order.getWebOrderCreationDate())
        mappedTxnData.getTransactionTotalAmount() == orderTotalTxnAmt.toString()
        mappedTxnData.getCsrSalesRep() == order.getCsrSalesRepID()
        mappedTxnData.getEnterpriseCutId() == order.getEnterpriseCustomerId()
        mappedTxnData.getSalesChannel() == SalesChannels.valueOf(order.getSalesChannel())
        mappedTxnData.getIpAddress() == order.getIpAddress()
        mappedTxnData.getOrderMessage() == order.getOrderMessage()
        mappedTxnData.getPaymentMethods().getPaymentMethod().size() == order.getPaymentDetails().getCreditCards().size() + order.getPaymentDetails().getGiftCards().size() + amtOfPaypalPayments
        mappedTxnData.getMember().getMemberId() == order.getRewardZoneID()
        mappedTxnData.getShippingOrders().getShippingOrder().size() == order.getShippingOrders().size()
        mappedTxnData.getItems().getItem().size() == order.getItems().size()

        //todo: fix paypal stuff because paypal should now be a list of paypals
        //doExtraPaymentMethodsAssertions(mappedTxnData.getPaymentMethods(), order.getPaymentDetails())
        doChargebacksMapperAssertions(mappedTxnData, order.getShippingOrders())
    }


    def "test null values Transaction Data"(){
        given:

        Order order = new Order()
        order.setWebOrderRefID("webOrderRefId")
        DateTime dateTimeToMap = new DateTime().withZone(DateTimeZone.forID("America/Los_Angeles"))
        order.setWebOrderCreationDate(dateTimeToMap)
        order.setCsrSalesRepID("csrSalesRepId")
        order.setIpAddress("ipAddress")
        order.setSalesChannel("CSR_APP")


        //shipping order
        List<ShippingOrder> shippingOrders = null
        order.setShippingOrders(shippingOrders)

        //item
        List<Item> items = null
        order.setItems(items)

        //purchase order
        List<PurchaseOrder> purchaseOrders = null
        order.setPurchaseOrders(purchaseOrders)


        //payment details
        //create 3 credit card payments
        PaymentDetails paymentDetails = new PaymentDetails()
        List<PaymentDetails.CreditCard> creditCards = null


        //create 2 gift card payments
        List<PaymentDetails.GiftCard> giftCards = null


        //create a paypal payment
        PaymentDetails.PayPal paypal = null

        paymentDetails.setCreditCards(creditCards)
        paymentDetails.setGiftCards(giftCards)
  //      paymentDetails.setPayPal(paypal) //        paymentDetails.setPayPal(paypal) todo: this is now a list of paypals


        order.setPaymentDetails(paymentDetails);

        when:

        TransactionData mappedTxnData = xmlMapper.mapTransactionData(order)




        then:

        mappedTxnData.getTransactionId() == order.getFsOrderID()
        mappedTxnData.getWebOrderId() == order.getWebOrderRefID()
        mappedTxnData.transactionType == TransactionType.ORDER
        assertGregorianDateAndDateTimeAreEqual(mappedTxnData.getOrderDateTime(),order.getWebOrderCreationDate())
        mappedTxnData.getTransactionTotalAmount() == "0"
        mappedTxnData.getCsrSalesRep() == order.getCsrSalesRepID()
        mappedTxnData.getEnterpriseCutId() == order.getEnterpriseCustomerId()
        mappedTxnData.getSalesChannel() == SalesChannels.valueOf(order.getSalesChannel())
        mappedTxnData.getIpAddress() == order.getIpAddress()
        mappedTxnData.getOrderMessage() == order.getOrderMessage()
        mappedTxnData.getPaymentMethods().getPaymentMethod().isEmpty()
        mappedTxnData.getMember().getMemberId() == order.getRewardZoneID()
        mappedTxnData.getShippingOrders().getShippingOrder().isEmpty()
        mappedTxnData.getItems().getItem().isEmpty()
        mappedTxnData.getChargeBacks() == null
    }



    void assertGregorianDateAndDateTimeAreEqual(XMLGregorianCalendar xmlGregorianCalendar, DateTime dateTime) {


        GregorianCalendar c = new GregorianCalendar()
        c.setTime(dateTime.toDate())
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c)

        assert xmlGregorianCalendar.equals(date)

    }

    void assertMappedAddress(AddressDetails mappedAddress, Address addressToMap) {

        if(addressToMap != null) {
            assert mappedAddress != null
        }

        assert mappedAddress.getAddress1() == addressToMap.getAddress1()
        assert mappedAddress.getAddress2() == addressToMap.getAddress2()
        assert mappedAddress.getCity() == addressToMap.getCity()
        assert mappedAddress.getCountry() == addressToMap.getCountry()
        assert mappedAddress.getEmailAddress() == addressToMap.getEmail()
        assert mappedAddress.getFirstName() == addressToMap.getFirstName()
        assert mappedAddress.getLastName() == addressToMap.getLastName()
        assert mappedAddress.getPhoneNumber() == addressToMap.getPhoneNumber()
        assert mappedAddress.getPostalCode() == addressToMap.getPostalCode()
        assert mappedAddress.getState() == addressToMap.getProvince()
        assert mappedAddress.getSecondaryPhoneNumber() == addressToMap.getSecondaryPhoneNumber()
    }


    def createAShippingOrder(String shippingOrderId, String fsoLineId){

        def shippingOrder = new ShippingOrder()

        shippingOrder.setShippingOrderID(shippingOrderId)
        shippingOrder.setShippingOrderStatus("OPEN")
        shippingOrder.setGlobalContractID("globalContractId")
        shippingOrder.setTotalAuthorizedAmount(400)
        shippingOrder.setShippingCharge(1.23)
        shippingOrder.setShippingTax(0.20)
        shippingOrder.setShippingDiscount(0.03)
        shippingOrder.setFulfillmentPartner("fulfillmentPartner")
        shippingOrder.setShippingMethod("shippingMethod")

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        DateTime deliveryDate = formatter.parseDateTime("2018-02-19T09:15:52Z")

        List<ShippingOrderLine> shippingOrderLines = new ArrayList<>();
        shippingOrderLines.add(createAShippingOrderLine(fsoLineId))

        List<Chargeback> chargebacks = new ArrayList<>();
        chargebacks.add(createAChargeback())

        shippingOrder.setDeliveryDate(deliveryDate)
        shippingOrder.setShippingAddress(createAnAddress())
        shippingOrder.setShippingOrderLines(shippingOrderLines)
        shippingOrder.setChargebacks(chargebacks)

        return shippingOrder

    }

    def createAnItem(String fsoLineId){

        Item item = new Item()
        item.setCategory("category")
        item.setFsoLineID(fsoLineId)
        item.setItemQuantity(4)
        item.setItemTax(0.07)
        item.setItemTotalDiscount(100)
        item.setItemUnitPrice(200)
        item.setName("name")
        item.setStaffDiscount(1.11)
        item.setItemStatus("OPEN")
        item.setItemSkuNumber("skunumber")
        item.setItemSkuDescription("skuDescription")
        item.setPostCaptureDiscount(new BigDecimal(200))


        return item
    }

    def createAPurchaseOrder(String shippingOrderRefId){

        PurchaseOrder purchaseOrder = new PurchaseOrder()
        purchaseOrder.setPurchaseOrderID("purchaseOrderId")
        purchaseOrder.setPurchaseOrderStatus("OPEN")
        purchaseOrder.setShippingOrderRefID(shippingOrderRefId)

        return purchaseOrder

    }


    def createAnAddress(){

        Address address = new Address();

        address.setFirstName("first")
        address.setLastName("last")
        address.setEmail("email")
        address.setAddress1("address1")
        address.setAddress2("address2")
        address.setCity("city")
        address.setProvince("province")
        address.setPostalCode("postalcode")
        address.setCountry("country")
        address.setPhoneNumber("phoneNumber")
        address.setSecondaryPhoneNumber("secondaryPhoneNumber")

        return address;
    }

    def createAShippingOrderLine(String fsolineRefId){

        ShippingOrderLine shippingOrderLine = new ShippingOrderLine()

        shippingOrderLine.setEhf(1.23)
        shippingOrderLine.setEhfTax(1.11)
        shippingOrderLine.setFsoLineRefID(fsolineRefId)
        shippingOrderLine.setQuantity(1)
        shippingOrderLine.setShippingCharge(1.11)
        shippingOrderLine.setShippingDiscount(1.23)
        shippingOrderLine.setShippingOrderLineID("shippingOrderLineId")
        shippingOrderLine.setShippingOrderLineStatus("status")
        shippingOrderLine.setShippingTax(1.11)

        return shippingOrderLine;
    }

    def createAChargeback(){

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        DateTime deliveryDate = formatter.parseDateTime("2018-02-19T09:15:52Z")

        Chargeback chargeback = new Chargeback();
        chargeback.setAmount(1.11)
        chargeback.setReasonCode("reasonCode")
        chargeback.setReceiveDate(deliveryDate)

        return chargeback

    }

    def createACreditCard(){

        PaymentDetails.CreditCard creditCard = new PaymentDetails.CreditCard();

        creditCard.billingAddress = createAnAddress()
        creditCard.creditCard3dSecureValue = "3dssecurevalue"
        creditCard.creditCardAvsResponse = "avsresponse"
        creditCard.creditCardCvvResponse = "cvvresponse"

        creditCard.creditCardExpiryDate = "01/19"
        creditCard.creditCardNumber = "creditCardNumber"
        creditCard.totalAuthorizedAmount = 100

        return creditCard
    }

    def createAGiftCard(){

        PaymentDetails.GiftCard giftCard = new PaymentDetails.GiftCard();
        giftCard.giftCardNumber = "giftCardNumber"
        giftCard.totalAuthorizedAmount = 100
        return giftCard

    }


    def createAPaypal(){

        PaymentDetails.PayPal payPal= new PaymentDetails.PayPal();
        payPal.totalAuthorizedAmount = 100
        payPal.email = "email"
        payPal.requestID = "requestId"
        payPal.verifiedStatus = "status"

        return payPal

    }

    void doItemShippingOrderIdAssertion(ca.bestbuy.orders.fraud.model.client.accertify.wsdl.Item item, List<ShippingOrder> shippingOrders) {

        for(int i = 0; i < shippingOrders.size(); i++){
            List<ShippingOrderLine> shippingOrderLinesToMap = shippingOrders.get(i).getShippingOrderLines()

            for(int j = 0; j < shippingOrderLinesToMap.size(); j++){

                if(item.getFsOrderLineId().equals(shippingOrderLinesToMap.get(j).getFsoLineRefID())){

                    assert item.getShippingOrderId() == shippingOrders.get(i).getShippingOrderID()

                }

            }

        }

    }

    void doExtraPaymentMethodsAssertions(PaymentMethods paymentMethods, PaymentDetails paymentDetails) {


        int numOfCreditCardPayments = paymentDetails.getCreditCards().size()
        int numOfGiftCardPayments = paymentDetails.getGiftCards().size()

        List<CaPaymentMethod> paymentMethodsList = paymentMethods.getPaymentMethod()
        List<PaymentDetails.CreditCard> creditCardsList = paymentDetails.getCreditCards()
        List<PaymentDetails.GiftCard> giftCardList = paymentDetails.getGiftCards()
        //todo: paypal should now be a list of paypals
//        PaymentDetails.PayPal payPal = paymentDetails.getPayPal()
        int creditCardPaymentIndex;
        int giftCardPaymentIndex;

        for(creditCardPaymentIndex = 0; creditCardPaymentIndex < numOfCreditCardPayments; creditCardPaymentIndex++){
            assert paymentMethodsList.get(creditCardPaymentIndex).getPaymentMethodType() == PaymentMethodType.CREDITCARD
            assert paymentMethodsList.get(creditCardPaymentIndex).getCreditCard().getCreditCardType() == creditCardsList.get(creditCardPaymentIndex).creditCardType
            assert paymentMethodsList.get(creditCardPaymentIndex).getCreditCard().getCreditCardType() == creditCardsList.get(creditCardPaymentIndex).creditCardType
            assert paymentMethodsList.get(creditCardPaymentIndex).getCreditCard().getCreditCardNumber() == creditCardsList.get(creditCardPaymentIndex).creditCardNumber
            assert paymentMethodsList.get(creditCardPaymentIndex).getCreditCard().getCreditCardExpireMonth() == creditCardsList.get(creditCardPaymentIndex).creditCardExpiryDate.substring(0,2)
            assert paymentMethodsList.get(creditCardPaymentIndex).getCreditCard().getCreditCardExpireYear() == creditCardsList.get(creditCardPaymentIndex).creditCardExpiryDate.substring(3)
            assert paymentMethodsList.get(creditCardPaymentIndex).getCreditCard().getCreditCardAvsResponse() == creditCardsList.get(creditCardPaymentIndex).creditCardAvsResponse
            assert paymentMethodsList.get(creditCardPaymentIndex).getCreditCard().getCreditCardCvvResponse() == creditCardsList.get(creditCardPaymentIndex).creditCardCvvResponse
            assert paymentMethodsList.get(creditCardPaymentIndex).getCreditCard().getCreditCard3DSecureValue() == creditCardsList.get(creditCardPaymentIndex).creditCard3dSecureValue
            assert paymentMethodsList.get(creditCardPaymentIndex).getCreditCard().getTotalCreditCardAuthAmount() == creditCardsList.get(creditCardPaymentIndex).totalAuthorizedAmount.toString()

            assert paymentMethodsList.get(creditCardPaymentIndex).getGiftCard() == null
            assert paymentMethodsList.get(creditCardPaymentIndex).getPaypals() == null

        }

        for(giftCardPaymentIndex = creditCardPaymentIndex;  giftCardPaymentIndex < (numOfCreditCardPayments+numOfGiftCardPayments); giftCardPaymentIndex++){

            assert paymentMethodsList.get(giftCardPaymentIndex).getPaymentMethodType() == PaymentMethodType.GIFTCARD
            assert paymentMethodsList.get(giftCardPaymentIndex).getGiftCard().getGiftCardNumber() == giftCardList.get(giftCardPaymentIndex-numOfCreditCardPayments).giftCardNumber
            assert paymentMethodsList.get(giftCardPaymentIndex).getGiftCard().getTotalGiftCardAuthAmount() == giftCardList.get(giftCardPaymentIndex-numOfCreditCardPayments).totalAuthorizedAmount.toString()


            assert paymentMethodsList.get(giftCardPaymentIndex).getCreditCard() == null
            assert paymentMethodsList.get(giftCardPaymentIndex).getPaypals() == null
        }


        assert paymentMethodsList.get(giftCardPaymentIndex).getPaymentMethodType() == PaymentMethodType.PAYPAL
        assert paymentMethodsList.get(giftCardPaymentIndex).getPaypals().getPaypalEmail() == payPal.email
        assert paymentMethodsList.get(giftCardPaymentIndex).getPaypals().getPaypalRequestId() == payPal.requestID
        assert paymentMethodsList.get(giftCardPaymentIndex).getPaypals().getPaypalStatus() == payPal.verifiedStatus
        assert paymentMethodsList.get(giftCardPaymentIndex).getPaypals().getTotalPaypalAuthAmt() == payPal.totalAuthorizedAmount.toString()

        assert paymentMethodsList.get(giftCardPaymentIndex).getCreditCard() == null
        assert paymentMethodsList.get(giftCardPaymentIndex).getGiftCard() == null


    }

    void doChargebacksMapperAssertions(TransactionData mappedTxnData, List<ShippingOrder> shippingOrdersToMap) {

        if(shippingOrdersToMap == null || shippingOrdersToMap.isEmpty()){
            return;
        }

        List<ChargeBack> mappedChargebacks = mappedTxnData.getChargeBacks().getChargeBack()
        List<Chargeback> chargebacksToMap = new ArrayList<>()

        for(int i = 0; i < shippingOrdersToMap.size(); i++) {

            ShippingOrder shippingOrder = shippingOrdersToMap.get(i)

            for(int j = 0; j < shippingOrder.getChargebacks().size(); j++) {
                Chargeback chargeback = shippingOrder.getChargebacks().get(j)
                chargebacksToMap.add(chargeback)
            }

        }


        if(mappedChargebacks.size() != chargebacksToMap.size()) {
            assert false
            return;
        }
        for(int k = 0; k < mappedChargebacks.size(); k++) {
            assert mappedChargebacks.get(k).getChargeBackAmount() == chargebacksToMap.get(k).getAmount().toString()
            assert mappedChargebacks.get(k).getChargeBackReasonCode() == chargebacksToMap.get(k).getReasonCode()
            assert mappedChargebacks.get(k).getChargeBackReciveDate() == chargebacksToMap.get(k).getReceiveDate().toString()
        }
        assert mappedChargebacks.get(0).getChargeBackAmount() == chargebacksToMap.get(0).getAmount().toString()
        assert mappedChargebacks.get(0).getChargeBackReasonCode() == chargebacksToMap.get(0).getReasonCode()
        assert mappedChargebacks.get(0).getChargeBackReciveDate() == chargebacksToMap.get(0).getReceiveDate().toString()

    }

    void assertDatesAreEqual(DateTime mappedDate, DateTime dateToMap) {

        if (dateToMap != null) {
            assert mappedDate != null
        }

        assert mappedDate.dayOfMonth().get() == dateToMap.dayOfMonth().get()
        assert mappedDate.monthOfYear().get() == dateToMap.monthOfYear().get()
        assert mappedDate.year().get() == dateToMap.year().get()
        assert mappedDate.hourOfDay().get() == dateToMap.hourOfDay().get()
        assert mappedDate.minuteOfHour().get() == dateToMap.minuteOfHour().get()
        assert mappedDate.secondOfMinute().get() == dateToMap.secondOfMinute().get()

    }


}
