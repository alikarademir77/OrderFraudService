package ca.bestbuy.orders.fraud.mappers;

import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.AddressDetails;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.CaPaymentMethod;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ChargeBack;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ChargeBacks;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.CreditCard;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.GiftCard;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.Item;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.PaymentMethodStatus;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.PaymentMethodType;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.PaymentMethods;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.Paypal;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.PurchaseOrderStatus;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ShippingOrder;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.TransactionData;
import ca.bestbuy.orders.fraud.model.internal.Address;
import ca.bestbuy.orders.fraud.model.internal.Chargeback;
import ca.bestbuy.orders.fraud.model.internal.Order;
import ca.bestbuy.orders.fraud.model.internal.PaymentDetails;
import ca.bestbuy.orders.fraud.model.internal.PurchaseOrder;
import ca.bestbuy.orders.fraud.model.internal.ShippingOrderLine;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


@Mapper(uses = ObjectFactory.class, componentModel = "spring")
public abstract class TASRequestXMLMapper {

    /**
     *
     * This mapper will map the fields for the accertify.wsdl.TransactionData object using our internal Order object.
     *
     */
    @Mappings({

            @Mapping(target = "requestVersion", ignore = true), //todo: will be generated when fms flow is implemented. hardcoded now for testing purposes
            @Mapping(target = "transactionId", source = "fsOrderID"),
            @Mapping(target = "webOrderId", source = "webOrderRefID"),
            @Mapping(target = "transactionType", constant = "ORDER"), //will always be ORDER for Fraud Checks
            @Mapping(target = "transactionDateTime", ignore = true), //handled by mapTransactionData_TransactionData custom mapping
            @Mapping(target = "orderDateTime", source = "webOrderCreationDate"),
            @Mapping(target = "transactionTotalAmount", ignore = true), //handled by mapTransactionData_TransactionTotalAmount custom mapping
            @Mapping(target = "csrSalesRep", source = "csrSalesRepID"),
            @Mapping(target = "enterpriseCutId", source = "enterpriseCustomerId"),
            @Mapping(target = "salesChannel", source = "salesChannel"),
            @Mapping(target = "ipAddress", source = "ipAddress"),
            @Mapping(target = "orderMessage", source = "orderMessage"),
            @Mapping(target = "billingDetails", ignore = true), //todo: (requires order details change) handle with a custom Billing Details mapper because we need to implement a check for ACTIVE payment method
            @Mapping(target = "paymentMethods", ignore = true), //handled by mapTransactionData_PaymentMethods() custom mapping
            @Mapping(target = "member.memberId", source = "rewardZoneID"),
            @Mapping(target = "items.item", source = "items"),
            @Mapping(target = "shippingOrders.shippingOrder", source = "shippingOrders"),
            @Mapping(target = "chargeBacks", ignore = true) //handled by mapTransactionData_Chargebacks() custom mapping
    })
    public abstract TransactionData mapTransactionData(Order orderToMap);


    /**
     *
     * This is a custom mapper that will map the Transaction Date Time in the Transaction Data
     */

    @AfterMapping
    public void mapTransactionData_TransactionDateTime(@MappingTarget TransactionData mappedTransactionData){

        if(mappedTransactionData == null){
            return;
        }

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        try {
            XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            mappedTransactionData.setTransactionDateTime(xmlDate);
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException("Date format is incorrect");
        }
    }

    /**
     *
     * This custom mapper will map the transaction total amount field of the Transaction Data object. It will do so by
     * iterating through a list of internal Shipping Order objects, and adding up the Total Authorized Amount value
     * in each one.
     *
     */
    @AfterMapping
    public void mapTransactionData_TransactionTotalAmount(Order orderToMap, @MappingTarget TransactionData mappedTransactionData){

        if(orderToMap == null || mappedTransactionData == null || orderToMap.getShippingOrders() == null){
            if(mappedTransactionData != null) {
                mappedTransactionData.setTransactionTotalAmount("0");
            }
            return;
        }

        List<ca.bestbuy.orders.fraud.model.internal.ShippingOrder> shippingOrdersToMap = orderToMap.getShippingOrders();
        BigDecimal transactionTotalToMap = new BigDecimal(0.00);

        for(ca.bestbuy.orders.fraud.model.internal.ShippingOrder shippingOrderToMap : shippingOrdersToMap){
            if(shippingOrderToMap.getTotalAuthorizedAmount() != null ) {
                transactionTotalToMap = transactionTotalToMap.add(shippingOrderToMap.getTotalAuthorizedAmount());
            }
        }

        mappedTransactionData.setTransactionTotalAmount(transactionTotalToMap.toString());

    }

    /**
     * Each accertify.wsdl.Item needs to be associated to a shipping order via a shipping order id.
     * This mapper will iterate through a list of our internal shipping order domain object, and for each shipping order, it will compare its
     * FSOLINEREFID to the accertify.wsdl.Item object's (the mapping target) FSOLINEID.
     * Once it finds a match, it will set the Item object's SHIPPING ORDER ID with the id of the shipping order that contained the shipping order line
     * with the matching FSOLINEID
     */

    @AfterMapping
    public void mapTransactionData_Item_ShippingOrderId(Order orderToMap, @MappingTarget TransactionData mappedTransactionData){


        if(orderToMap == null || mappedTransactionData == null || orderToMap.getShippingOrders() == null
                || orderToMap.getShippingOrders().isEmpty()
                || mappedTransactionData.getItems() == null
                || mappedTransactionData.getItems().getItem().isEmpty()
                || mappedTransactionData.getItems().getItem() == null){
            return;
        }

        List<ca.bestbuy.orders.fraud.model.internal.ShippingOrder> shippingOrderList = orderToMap.getShippingOrders();
        List<Item> itemsList = mappedTransactionData.getItems().getItem();

        for(int i = 0; i < shippingOrderList.size(); i++) {

            if(shippingOrderList.get(i).getShippingOrderLines() != null && !(shippingOrderList.get(i).getShippingOrderLines().isEmpty())) {

                List<ShippingOrderLine> shippingOrderLineList = shippingOrderList.get(i).getShippingOrderLines();
                String shippingOrderIdToMap = shippingOrderList.get(i).getShippingOrderID();

                for (int j = 0; j < shippingOrderLineList.size(); j++) {
                    for (int k = 0; k < itemsList.size(); k++) {
                        if (shippingOrderLineList.get(j).getFsoLineRefID().equals(itemsList.get(k).getFsOrderLineId())) {
                            itemsList.get(k).setShippingOrderId(shippingOrderIdToMap);
                        }
                    }
                }
            }
        }

    }


    /**
     *
     * This mapper will iterate through a list of our internal Purchase Order domain object. For each Purchase Order, it will
     * iterate through a list of accertify.wsdl.ShippingOrder objects. It will then compare the Shipping Order Id of both objects.
     * When it finds a match, it will map the purchase order information (from the internal Purchase Order object) to the accertify.wsdl.ShippingOrder
     * object.
     */
    @AfterMapping
    public void mapTransactionData_ShippingOrder_PurchaseOrderInfo(Order orderToMap, @MappingTarget TransactionData mappedTransactionData) {

        if(orderToMap == null || mappedTransactionData == null ||orderToMap.getPurchaseOrders() == null
                || orderToMap.getPurchaseOrders().isEmpty() || mappedTransactionData.getShippingOrders() == null
                || mappedTransactionData.getShippingOrders().getShippingOrder().isEmpty()
                || mappedTransactionData.getShippingOrders().getShippingOrder() == null){
            return;
        }

        List<PurchaseOrder> purchaseOrders = orderToMap.getPurchaseOrders();
        List<ShippingOrder> shippingOrders = mappedTransactionData.getShippingOrders().getShippingOrder();


        for (int i = 0; i < purchaseOrders.size(); i++) {
            for (int j = 0; j < shippingOrders.size(); j++) {

                if(purchaseOrders.get(i) != null && purchaseOrders.get(i).getShippingOrderRefID() != null && shippingOrders.get(j) != null) {
                    PurchaseOrder purchaseOrder = purchaseOrders.get(i);
                    ShippingOrder shippingOrder = shippingOrders.get(j);

                    if (purchaseOrder.getShippingOrderRefID().equals(shippingOrder.getShippingOrderId())) {

                        shippingOrder.setPurchaseOrderId(purchaseOrder.getPurchaseOrderID());
                        shippingOrder.setPurchaseOrderStatus(PurchaseOrderStatus.valueOf(purchaseOrder.getPurchaseOrderStatus()));

                    }
                }
            }
        }

    }


    /**
     *
     * This mapper will map into a list of PaymentMethods, with all the credit card payment methods mapped first, then gift cards, and then paypal
     * eg. {Credit Card Payment 1, Credit Card Payment 2, Credit Card Payment 3, GiftCard Payment 1, Gift Card Payment 2, Paypal Payment}
     *
     */
    @AfterMapping
    public void mapTransactionData_PaymentMethods(Order orderToMap, @MappingTarget TransactionData mappedTransactionData) {

        //payment method will only have the payment method type listed as its paymentMethodType.
        //i.e. if type os CREDITCARD, only creditcard should be mapped and the other payment types should be null
        if (orderToMap == null || mappedTransactionData == null || orderToMap.getPaymentDetails() == null) {
            return;
        }

        PaymentDetails paymentDetailsToMap = orderToMap.getPaymentDetails();
        PaymentMethods mappedPaymentMethods = new PaymentMethods();

        //Map all of the credit card payment methods.
        if (paymentDetailsToMap.getCreditCards() != null && !(paymentDetailsToMap.getCreditCards().isEmpty()) ) {
            //Credit Card Payment Methods
            for (PaymentDetails.CreditCard creditCardToMap : paymentDetailsToMap.getCreditCards()) {

                CaPaymentMethod mappedCreditCardPaymentMethod = new CaPaymentMethod();
                CreditCard mappedCreditCard = new CreditCard();

                //todo: (requires order details change) hardcoded for now as our internal payment details object is currently not storing the payment method status
                //todo: (requires order details change) in the future, use the following instead of hardcoding:
                //todo: (requires order details change) mappedCreditCardPaymentMethod.setPaymentMethodStatus(creditCardToMap.status);
                mappedCreditCardPaymentMethod.setPaymentMethodStatus(PaymentMethodStatus.INACTIVE);
                mappedCreditCardPaymentMethod.setPaymentMethodType(PaymentMethodType.CREDITCARD);

                mappedCreditCard.setBillingAddress(setBillingAddressForCreditCardMapping(creditCardToMap));
                mappedCreditCard.setCreditCardType(creditCardToMap.creditCardType);
                mappedCreditCard.setCreditCardNumber(creditCardToMap.creditCardNumber);
                //parsing just the month
                mappedCreditCard.setCreditCardExpireMonth(creditCardToMap.creditCardExpiryDate.substring(0,2));
                //parsing just the year
                mappedCreditCard.setCreditCardExpireYear(creditCardToMap.creditCardExpiryDate.substring(3));
                mappedCreditCard.setCreditCardAvsResponse(creditCardToMap.creditCardAvsResponse);
                mappedCreditCard.setCreditCardCvvResponse(creditCardToMap.creditCardCvvResponse);
                mappedCreditCard.setCreditCard3DSecureValue(creditCardToMap.creditCard3dSecureValue);

                //todo: (requires order details change) needs to be mapped in internal domain object
                mappedCreditCard.setTotalCreditCardAuthAmount(creditCardToMap.totalAuthorizedAmount.toString());

                mappedCreditCardPaymentMethod.setCreditCard(mappedCreditCard);
                mappedPaymentMethods.getPaymentMethod().add(mappedCreditCardPaymentMethod);

            }
        }

        //Map all of the gift card payments
        if (paymentDetailsToMap.getGiftCards() != null && !(paymentDetailsToMap.getGiftCards().isEmpty()) ) {
            for (PaymentDetails.GiftCard giftCardToMap : paymentDetailsToMap.getGiftCards()) {

                CaPaymentMethod mappedGiftCardPaymentMethod = new CaPaymentMethod();
                GiftCard mappedGiftCard = new GiftCard();

                //todo: (requires order details change) need to identify active payment method
                mappedGiftCardPaymentMethod.setPaymentMethodStatus(PaymentMethodStatus.INACTIVE);
                mappedGiftCardPaymentMethod.setPaymentMethodType(PaymentMethodType.GIFTCARD);

                mappedGiftCard.setGiftCardNumber(giftCardToMap.giftCardNumber);

                //not used currently -- will be null for now
                mappedGiftCard.setGiftCardDigital(null);

                //todo: (requires order details change) need to get total gift card amount in internal domain object -- should be same as total credit card amt
                mappedGiftCard.setTotalGiftCardAuthAmount(giftCardToMap.totalAuthorizedAmount.toString());

                mappedGiftCardPaymentMethod.setGiftCard(mappedGiftCard);
                mappedPaymentMethods.getPaymentMethod().add(mappedGiftCardPaymentMethod);

            }
        }

        //Map the paypal payment method
        if (paymentDetailsToMap.getPayPal() != null) {
            CaPaymentMethod mappedPaypalPaymentMethod = new CaPaymentMethod();
            Paypal mappedPaypal = new Paypal();

            //todo: (requires order details change) need to identify active payment method
            mappedPaypalPaymentMethod.setPaymentMethodStatus(PaymentMethodStatus.INACTIVE);
            mappedPaypalPaymentMethod.setPaymentMethodType(PaymentMethodType.PAYPAL);
            mappedPaypal.setPaypalEmail(paymentDetailsToMap.getPayPal().email);
            mappedPaypal.setPaypalRequestId(paymentDetailsToMap.getPayPal().requestID);
            mappedPaypal.setPaypalStatus(paymentDetailsToMap.getPayPal().verifiedStatus);

            //todo: (requires order details change) needs to be mapped in internal domain object
            if(paymentDetailsToMap.getPayPal().totalAuthorizedAmount != null) {
                mappedPaypal.setTotalPaypalAuthAmt(paymentDetailsToMap.getPayPal().totalAuthorizedAmount.toString());
            }

            mappedPaypalPaymentMethod.setPaypals(mappedPaypal);
            mappedPaymentMethods.getPaymentMethod().add(mappedPaypalPaymentMethod);
        }

        mappedTransactionData.setPaymentMethods(mappedPaymentMethods);
    }

    /**
     *
     * This mapper will iterate through a list of our internal shipping order objects. For each shipping order, it will then
     * iterate through a list of its chargebacks. THe mapper then maps the approriate fields to the accertify.wsdl.Chargeback object.
     */

    @AfterMapping
    public void mapTransactionData_Chargebacks(Order orderToMap, @MappingTarget TransactionData mappedTransactionData){

        if(orderToMap == null || mappedTransactionData == null){
            return;
        }

        ChargeBacks mappedChargebacks = new ChargeBacks();

        if(orderToMap.getShippingOrders() != null && !(orderToMap.getShippingOrders().isEmpty()) ) {
            //iterate through list of internal shipping order objects
            for (int i = 0; i < orderToMap.getShippingOrders().size(); i++) {
                ca.bestbuy.orders.fraud.model.internal.ShippingOrder shippingOrderToMap = orderToMap.getShippingOrders().get(i);

                if(shippingOrderToMap.getChargebacks() != null || !(shippingOrderToMap.getChargebacks().isEmpty())) {
                    //iterate through the shipping order's list of chargebacks
                    for (int j = 0; j < shippingOrderToMap.getChargebacks().size(); j++) {
                        //for each internal chargeback object, map a accertify.wsdl.Chargeback object
                        ChargeBack mappedChargeBack = new ChargeBack();
                        Chargeback chargebackToMap = shippingOrderToMap.getChargebacks().get(j);

                        mappedChargeBack.setShippingOrderId(shippingOrderToMap.getShippingOrderID());

                        if(chargebackToMap != null) {
                            mappedChargeBack.setChargeBackReasonCode(chargebackToMap.getReasonCode());

                            if (chargebackToMap.getAmount() != null) {
                                mappedChargeBack.setChargeBackAmount(chargebackToMap.getAmount().toString());
                            }

                            if (chargebackToMap.getReceiveDate() != null) {
                                mappedChargeBack.setChargeBackReciveDate(chargebackToMap.getReceiveDate().toString());
                            }

                            mappedChargebacks.getChargeBack().add(mappedChargeBack);
                        }
                    }
                }
            }
        }


        if(!mappedChargebacks.getChargeBack().isEmpty()) {
            mappedTransactionData.setChargeBacks(mappedChargebacks);
        }
    }

    /**
     *
     * This mapper will map to an accertify.wsdl.Item object, using the internal Item domain object
     *
     */
    @Mappings({

            @Mapping(target = "shippingOrderId", ignore = true), // handled in mapTransactionData_Item_ShippingOrderId custom mapping
            @Mapping(target = "fsOrderLineId", source = "fsoLineID"),
            @Mapping(target = "itemStatus", source = "itemStatus"),
            @Mapping(target = "itemSkuNumber", source = "itemSkuNumber"),
            @Mapping(target = "itemSkuDescription", source = "itemSkuDescription"),
            @Mapping(target = "itemPrice", source = "itemUnitPrice"),
            @Mapping(target = "itemQuantity", source = "itemQuantity"),
            @Mapping(target = "itemSkuCategory", source = "category"),
            @Mapping(target = "staffDiscount", source = "staffDiscount"),
            @Mapping(target = "postCaptureDiscount", source = "postCaptureDiscount"),
            @Mapping(target = "itemSaleTax", source = "itemTax")
    })
    public abstract Item mapItem(ca.bestbuy.orders.fraud.model.internal.Item itemToMap);


    /**
     *
     * This mapper will map a accertify.wsdl.ShippingOrder object using an internal shipping order object
     *
     */
    @Mappings({
            @Mapping(target = "shippingOrderId", source = "shippingOrderID"),
            @Mapping(target = "purchaseOrderId", ignore = true), //handled by mapShippingOrder_PurchaseOrderInfo() custom mapping
            @Mapping(target = "globalContractId", source = "globalContractID"),
            @Mapping(target = "shippingOrderStatus", source = "shippingOrderStatus"),
            @Mapping(target = "purchaseOrderStatus", ignore = true), //handled by mapShippingOrder_PurchaseOrderInfo() custom mapping
            @Mapping(target = "shippingCharge", source = "shippingCharge"),
            @Mapping(target = "shippingDetails.shippingMethod", source = "shippingMethod"),
            @Mapping(target = "shippingDetails.shippingDeadline", source = "deliveryDate"),
            @Mapping(target = "shippingDetails.shippingAddress", source = "shippingAddress"),
            @Mapping(target = "fulfillmentPartner", source = "fulfillmentPartner")
    })
    public abstract ShippingOrder mapShippingOrder(ca.bestbuy.orders.fraud.model.internal.ShippingOrder shippingOrderToMap);


    /**
     *
     * This will map an address object
     *
     */
    @Mappings({
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "emailAddress", source = "email"),
            @Mapping(target = "address1", source = "address1"),
            @Mapping(target = "address2", source = "address2"),
            @Mapping(target = "city", source = "city"),
            @Mapping(target = "state", source = "province"),
            @Mapping(target = "postalCode", source = "postalCode"),
            @Mapping(target = "country", source = "country"),
            @Mapping(target = "phoneNumber", source = "phoneNumber"),
            @Mapping(target = "secondaryPhoneNumber", source = "secondaryPhoneNumber")
    })
    protected abstract AddressDetails mapAddress(Address addressToMap);


    public AddressDetails setBillingAddressForCreditCardMapping(PaymentDetails.CreditCard creditCardToMap){


        AddressDetails addressDetails = new AddressDetails();
        Address billingAddress = creditCardToMap.billingAddress;

        addressDetails.setFirstName(billingAddress.getFirstName());
        addressDetails.setLastName(billingAddress.getLastName());
        addressDetails.setMiddleName(null);
        addressDetails.setEmailAddress(billingAddress.getEmail());
        addressDetails.setAddress1(billingAddress.getAddress1());
        addressDetails.setAddress2(billingAddress.getAddress2());
        addressDetails.setCity(billingAddress.getCity());
        addressDetails.setState(billingAddress.getProvince());
        addressDetails.setPostalCode(billingAddress.getPostalCode());
        addressDetails.setCountry(billingAddress.getCountry());
        addressDetails.setPhoneNumber(billingAddress.getPhoneNumber());
        addressDetails.setSecondaryPhoneNumber(billingAddress.getSecondaryPhoneNumber());

        return addressDetails;
    }


}
