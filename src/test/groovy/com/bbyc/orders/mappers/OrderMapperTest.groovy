package com.bbyc.orders.mappers

import com.bbyc.orders.model.client.orderdetails.ActualCarrier
import com.bbyc.orders.model.client.orderdetails.CreditCardInfo
import com.bbyc.orders.model.client.orderdetails.FSOrder
import com.bbyc.orders.model.client.orderdetails.GiftCardInfo
import com.bbyc.orders.model.client.orderdetails.LevelOfService
import com.bbyc.orders.model.client.orderdetails.PaymentMethodInfo
import com.bbyc.orders.model.client.orderdetails.RewardZone
import com.bbyc.orders.model.client.orderdetails.ShippingOrder
import com.bbyc.orders.model.client.orderdetails.ShippingOrderLine
import com.bbyc.orders.model.client.orderdetails.Status
import com.bbyc.orders.model.internal.Order
import com.bbyc.orders.model.internal.PaymentDetails
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import org.mapstruct.factory.Mappers
import spock.lang.Shared
import spock.lang.Specification


class OrderMapperTest extends Specification {


    @Shared
    FSOrder orderToMap

    @Shared
    Order mappedOrder

    def setupSpec(){

        OrderMapper orderDetailsMapper = Mappers.getMapper(OrderMapper.class);

//        Status status = new Status()
//        status.setName("status")
//        RewardZone rewardZone = new RewardZone();
//        rewardZone.setRewardZoneId("rewardZoneId")
//        orderToMap = new FSOrder();
//        orderToMap.setId("id");
//        orderToMap.setIpAddress("ipAddress")
//        orderToMap.setRewardZone(rewardZone)
//        orderToMap.setWebOrderRefId("webOrderRefId")
//        orderToMap.setStatus(status)
//        orderToMap.setBundles()
//        orderToMap.set
//
//
//        CreditCardInfo creditCard1 = new CreditCardInfo()
//        creditCard1.setCreditCardNumber("creditcardnumber")
//        creditCard1.setCreditCardExpiryDate("expiryDate")
//        creditCard1.setCreditCardType("type")
//
//
//        GiftCardInfo giftCard1 = new GiftCardInfo()
//        giftCard1.setGiftCardNumber("giftCardNumber")
//
//
//        PaymentMethodInfo paymentMethodInfo = new PaymentMethodInfo();
//        paymentMethodInfo.setCreditCards(Arrays.asList(creditCard1))
//        paymentMethodInfo.setGiftCards(Arrays.asList(giftCard1))
//
//        ShippingOrderLine shippingOrderLine1 = new ShippingOrderLine();
//        shippingOrderLine1.setId("id")
//        shippingOrderLine1.setQtyOrdered("2")
//        shippingOrderLine1.setStatus(status)
//        shippingOrderLine1.setUnitPrice("1.23")
//
//
//        ShippingOrder shippingOrder1 = new ShippingOrder();
//        shippingOrder1.setShippingOrderLines(Arrays.asList(shippingOrderLine1))
//        shippingOrder1.setId("id")
//        shippingOrder1.setStatus(status)
//        shippingOrder1.setFulfillmentPartner("fulfillment partner")
//
//
//        ActualCarrier carrier = new ActualCarrier()
//        carrier.setId("id")
//        carrier.setShipmentType("shipment type")
//
//        LevelOfService levelOfService = new LevelOfService()
//        levelOfService.setId("id")
//        levelOfService.setCarrierCode("carrier code")
//        levelOfService.setName("name")
//        carrier.setLevelOfService(levelOfService)
//
//        shippingOrder1.setActualCarrier(carrier)
//
//        orderToMap.setPaymentMethodInfo(paymentMethodInfo)
//        orderToMap.setShippingOrders(Arrays.asList(shippingOrder1))
//
//        mappedOrder = orderDetailsMapper.mapOrder(orderToMap);

        String orderDetailsResponse = new File('src/test/resources/order-details-response.json').text

        ObjectMapper objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JodaModule())
        FSOrder orderToMap = objectMapper.readValue(orderDetailsResponse, FSOrder.class)

        mappedOrder = orderDetailsMapper.mapOrder(orderToMap);

    }




    def "order mapper test"(){

        given: "a valid order object mapped from the order object from order details"

        expect:
        mappedOrder.getFsOrderNumber() == orderToMap.getId();
        mappedOrder.getIpAddress() == orderToMap.getIpAddress();
        mappedOrder.getRewardZoneID() == orderToMap.getRewardZone().getRewardZoneId();
        mappedOrder.getWebOrderNumber() == orderToMap.getWebOrderRefId();
    }


    def "test credit card"(){

        expect:
        mappedOrder.getPaymentDetails().getCreditCards().size() == orderToMap.getPaymentMethodInfo().getCreditCards().size();
        for(int i = 0; i < orderToMap.getPaymentMethodInfo().getCreditCards().size() ; i++){

            CreditCardInfo creditCardToMap = orderToMap.getPaymentMethodInfo().getCreditCards().get(i)
            PaymentDetails.CreditCard mappedCreditCard = mappedOrder.getPaymentDetails().getCreditCards().get(i)

            mappedCreditCard.creditCardNumber == creditCardToMap.getCreditCardNumber()
            mappedCreditCard.creditCardExpiryDate == creditCardToMap.getCreditCardExpiryDate()
            mappedCreditCard.creditCardType == creditCardToMap.getCreditCardType()
            //todo: check address stuff


        }


    }

    def "test gift card"(){
        expect:
        mappedOrder.getPaymentDetails().getGiftCards().size() == orderToMap.getPaymentMethodInfo().getGiftCards().size();
        for(int i = 0; i < orderToMap.getPaymentMethodInfo().getGiftCards().size() ; i++){

            GiftCardInfo giftCardToMap = orderToMap.getPaymentMethodInfo().getGiftCards().get(i)
            PaymentDetails.GiftCard mappedGiftCard = mappedOrder.getPaymentDetails().getGiftCards().get(i)

            mappedGiftCard.giftCardNumber == giftCardToMap.getGiftCardNumber()

        }


    }


    def "test pay pal"(){

    }


    def "test shipping order and order lines"(){

        expect:
        mappedOrder.getShippingOrders().size() == orderToMap.getShippingOrders().size()

        for(int i = 0; i < orderToMap.getShippingOrders().size(); i++){

            ShippingOrder shippingOrderToMap = orderToMap.getShippingOrders().get(i)
            com.bbyc.orders.model.internal.ShippingOrder mappedShippingOrder =  mappedOrder.getShippingOrders().get(i)

            mappedShippingOrder.getFulfillmentPartner() == shippingOrderToMap.getFulfillmentPartner()
            mappedShippingOrder.getGlobalContractID() == shippingOrderToMap.getGlobalContractRefId()
            mappedShippingOrder.getShippingOrderID() == shippingOrderToMap.getId()
            mappedShippingOrder.getShippingOrderStatus() == shippingOrderToMap.getStatus().getName()

            for(int j = 0; j < shippingOrderToMap.getShippingOrderLines().size() ; j++){

                ShippingOrderLine shippingOrderLineToMap = shippingOrderToMap.getShippingOrderLines().get(j)
                com.bbyc.orders.model.internal.ShippingOrder.OrderLine mappedShippingOrderLine = mappedShippingOrder.getShippingOrderLines().get(j)

                mappedShippingOrderLine.lineNumber == shippingOrderLineToMap.getId()
                mappedShippingOrderLine.price == shippingOrderLineToMap.getUnitPrice().toDouble()
                mappedShippingOrderLine.quantity == shippingOrderLineToMap.getQtyOrdered().toInteger()
                mappedShippingOrderLine.status == shippingOrderLineToMap.getStatus().getName()

            }


        }
    }


    def "test Actual Carrier"(){



    }


}
