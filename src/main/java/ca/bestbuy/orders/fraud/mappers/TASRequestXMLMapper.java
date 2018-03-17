package ca.bestbuy.orders.fraud.mappers;

import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.AddressDetails;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.BillingDetails;
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
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.Transaction;
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


    @Mappings({

            @Mapping(target = "requestVersion", defaultValue = "1.0.0"), //todo: will be generated when fms flow is implemented. hardcoded now for testing purposes
            @Mapping(target = "transactionId", source = "fsOrderID"),
            @Mapping(target = "webOrderId", source = "webOrderRefID"),
            @Mapping(target = "transactionType", constant = "ORDER"), //will be mapped when we call doFraudCheck() in TAS client
            @Mapping(target = "transactionDateTime", ignore = true), //handled by mapTransactionData_TransactionData custom mapping
            @Mapping(target = "orderDateTime", source = "webOrderCreationDate"),
            @Mapping(target = "transactionTotalAmount", ignore = true), //handled by mapTransactionData_TransactionTotalAmount custom mapping
            @Mapping(target = "csrSalesRep", source = "csrSalesRepID"),
            @Mapping(target = "enterpriseCutId", source = "enterpriseCustomerId"),
            @Mapping(target = "salesChannel", source = "salesChannel"),
            @Mapping(target = "ipAddress", source = "ipAddress"),
            @Mapping(target = "orderMessage", source = "orderMessage"),
            @Mapping(target = "billingDetails", ignore = true), //will be handled by mapTransactionData_BillingDetails custom mapping **will need to be worked on in the future
            @Mapping(target = "paymentMethods", ignore = true), //handled by mapTransactionData_PaymentMethods() custom mapping
            @Mapping(target = "member.memberId", source = "rewardZoneID"),
            @Mapping(target = "items.item", source = "items"),
            @Mapping(target = "shippingOrders.shippingOrder", source = "shippingOrders"),
            @Mapping(target = "chargeBacks", ignore = true) //handled by mapTransactionData_Chargebacks() custom mapping
    })
    public abstract TransactionData mapTransactionData(Order orderToMap);



    @AfterMapping
    public void mapTransactionData_TransactionData(@MappingTarget TransactionData mappedTransactionData){

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

    //TODO: hardcoding billing details for now. In the future, we will need to iterate through our payment methods to identify
    //TODO: the ACTIVE payment method. We should then use the details of that payment method to map billing details.
    @AfterMapping()
    public void mapTransactionData_BillingDetails(Order orderToMap, @MappingTarget TransactionData mappedTransactionData){

        BillingDetails mappedBillingDetails = new BillingDetails();

        mappedBillingDetails.setBillingEmailAddress("billing@bestbuycanada.com");
        mappedBillingDetails.setCurrencyCode("CAN");
        mappedBillingDetails.setTotalNumberOfFailedCcAttempts("0");

        mappedTransactionData.setBillingDetails(mappedBillingDetails);


    }



    @AfterMapping
    public void mapTransactionData_TransactionTotalAmount(Order orderToMap, @MappingTarget TransactionData mappedTransactionData){

        if(orderToMap == null || orderToMap.getShippingOrders() == null){
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

    @AfterMapping
    public void mapTransactionData_Item_ShippingOrderId(Order orderToMap, @MappingTarget TransactionData mappedTransactionData){


        if(orderToMap.getShippingOrders() == null || mappedTransactionData.getItems() == null
                || mappedTransactionData.getItems().getItem() == null){
            return;
        }

        List<ca.bestbuy.orders.fraud.model.internal.ShippingOrder> shippingOrderList = orderToMap.getShippingOrders();
        List<Item> itemsList = mappedTransactionData.getItems().getItem();

        for(int i = 0; i < shippingOrderList.size(); i++) {

            if(shippingOrderList.get(i).getShippingOrderLines() != null) {

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


    @AfterMapping
    public void mapTransactionData_ShippingOrder_PurchaseOrderInfo(Order orderToMap, @MappingTarget TransactionData mappedTransactionData) {

        if(orderToMap.getPurchaseOrders() == null || mappedTransactionData.getShippingOrders() == null || mappedTransactionData.getShippingOrders().getShippingOrder() == null){
            return;
        }

        List<PurchaseOrder> purchaseOrders = orderToMap.getPurchaseOrders();
        List<ShippingOrder> shippingOrders = mappedTransactionData.getShippingOrders().getShippingOrder();


        for (int i = 0; i < purchaseOrders.size(); i++) {
            for (int j = 0; j < shippingOrders.size(); j++) {

                if(purchaseOrders.get(i) != null) {

                    if (purchaseOrders.get(i).getShippingOrderRefID().equals(shippingOrders.get(j))) {

                        shippingOrders.get(j).setPurchaseOrderId(purchaseOrders.get(i).getPurchaseOrderID());
                        shippingOrders.get(j).setPurchaseOrderStatus(PurchaseOrderStatus.valueOf(purchaseOrders.get(i).getPurchaseOrderStatus()));

                    }
                }
            }
        }

    }


    //payment method will only have the payment method type listed as its paymentMethodType.
    //i.e. if type os CREDITCARD, only creditcard should be mapped and the other payment types should be null
    @AfterMapping
    public void mapTransactionData_PaymentMethods(Order orderToMap, @MappingTarget TransactionData mappedTransactionData) {

        PaymentDetails paymentDetailsToMap = orderToMap.getPaymentDetails();
        PaymentMethods mappedPaymentMethods = new PaymentMethods();

        if (paymentDetailsToMap == null) {
            return;
        }

        if (paymentDetailsToMap.getCreditCards() != null) {
            //Credit Card Payment Methods
            for (PaymentDetails.CreditCard creditCardToMap : paymentDetailsToMap.getCreditCards()) {

                CaPaymentMethod mappedCreditCardPaymentMethod = new CaPaymentMethod();
                CreditCard mappedCreditCard = new CreditCard();

                //todo: hardcoded for now as our internal payment details object is currently not storing the payment method status
                //todo: in the future, use the following instead of hardcoding:
                //todo: mappedCreditCardPaymentMethod.setPaymentMethodStatus(creditCardToMap.status);
                mappedCreditCardPaymentMethod.setPaymentMethodStatus(PaymentMethodStatus.INACTIVE);
                mappedCreditCardPaymentMethod.setPaymentMethodType(PaymentMethodType.CREDITCARD);

                mappedCreditCard.setBillingAddress(setBillingAddressForCreditCardMapping(creditCardToMap));
                mappedCreditCard.setCreditCardType(creditCardToMap.creditCardType);
                mappedCreditCard.setCreditCardNumber(creditCardToMap.creditCardNumber);
                //parsing just the month -- should i based parsing cutoff on a delimiter instead?
                mappedCreditCard.setCreditCardExpireMonth(creditCardToMap.creditCardExpiryDate.substring(0,2));
                //parsing just the year
                mappedCreditCard.setCreditCardExpireYear(creditCardToMap.creditCardExpiryDate.substring(3));
                mappedCreditCard.setCreditCardAvsResponse(creditCardToMap.creditCardAvsResponse);
                mappedCreditCard.setCreditCardCvvResponse(creditCardToMap.creditCardCvvResponse);
                mappedCreditCard.setCreditCard3DSecureValue(creditCardToMap.creditCard3dSecureValue);

                //todo: needs to be mapped in internal domain object
                mappedCreditCard.setTotalCreditCardAuthAmount(creditCardToMap.totalAuthorizedAmount.toString());

                mappedCreditCardPaymentMethod.setCreditCard(mappedCreditCard);
                mappedPaymentMethods.getPaymentMethod().add(mappedCreditCardPaymentMethod);

            }
        }

        if (paymentDetailsToMap.getGiftCards() != null) {
            for (PaymentDetails.GiftCard giftCardToMap : paymentDetailsToMap.getGiftCards()) {

                CaPaymentMethod mappedGiftCardPaymentMethod = new CaPaymentMethod();
                GiftCard mappedGiftCard = new GiftCard();

                //todo: need to identify active payment method
                mappedGiftCardPaymentMethod.setPaymentMethodStatus(PaymentMethodStatus.INACTIVE);
                mappedGiftCardPaymentMethod.setPaymentMethodType(PaymentMethodType.GIFTCARD);

                mappedGiftCard.setGiftCardNumber(giftCardToMap.giftCardNumber);

                //not used currently -- will be null for now
                mappedGiftCard.setGiftCardDigital(null);

                //todo: need to get total gift card amount in internal domain object -- should be same as total credit card amt
                mappedGiftCard.setTotalGiftCardAuthAmount(giftCardToMap.totalAuthorizedAmount.toString());

                mappedGiftCardPaymentMethod.setGiftCard(mappedGiftCard);
                mappedPaymentMethods.getPaymentMethod().add(mappedGiftCardPaymentMethod);

            }
        }

        if (paymentDetailsToMap.getPayPal() != null) {
            CaPaymentMethod mappedPaypalPaymentMethod = new CaPaymentMethod();
            Paypal mappedPaypal = new Paypal();

            //todo: need to identify active payment method
            mappedPaypalPaymentMethod.setPaymentMethodStatus(PaymentMethodStatus.INACTIVE);
            mappedPaypalPaymentMethod.setPaymentMethodType(PaymentMethodType.PAYPAL);
            mappedPaypal.setPaypalEmail(paymentDetailsToMap.getPayPal().email);
            mappedPaypal.setPaypalRequestId(paymentDetailsToMap.getPayPal().requestID);
            mappedPaypal.setPaypalStatus(paymentDetailsToMap.getPayPal().verifiedStatus);

            //todo: needs to be mapped in internal domain object
            mappedPaypal.setTotalPaypalAuthAmt(paymentDetailsToMap.getPayPal().totalAuthorizedAmount.toString());

            mappedPaypalPaymentMethod.setPaypals(mappedPaypal);
            mappedPaymentMethods.getPaymentMethod().add(mappedPaypalPaymentMethod);
        }

        mappedTransactionData.setPaymentMethods(mappedPaymentMethods);
    }


    @AfterMapping
    public void mapTransactionData_Chargebacks(Order orderToMap, @MappingTarget TransactionData mappedTransactionData){

        ChargeBacks mappedChargebacks = new ChargeBacks();

        if(orderToMap.getShippingOrders() != null) {
            for (int i = 0; i < orderToMap.getShippingOrders().size(); i++) {
                ca.bestbuy.orders.fraud.model.internal.ShippingOrder shippingOrderToMap = orderToMap.getShippingOrders().get(i);
                if(shippingOrderToMap.getChargebacks() != null) {
                    for (int j = 0; j < shippingOrderToMap.getChargebacks().size(); j++) {
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
        mappedTransactionData.setChargeBacks(mappedChargebacks);
    }

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
