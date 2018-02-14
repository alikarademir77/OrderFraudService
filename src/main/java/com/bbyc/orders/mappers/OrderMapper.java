package com.bbyc.orders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.bbyc.orders.model.client.orderdetails.CreditCardInfo;
import com.bbyc.orders.model.client.orderdetails.FSOrder;
import com.bbyc.orders.model.client.orderdetails.PaymentMethodInfo;
import com.bbyc.orders.model.client.orderdetails.ShippingOrderLine;
import com.bbyc.orders.model.internal.Address;
import com.bbyc.orders.model.internal.Order;
import com.bbyc.orders.model.internal.PaymentDetails;
import com.bbyc.orders.model.internal.ShippingOrder;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mappings({
            @Mapping(target = "webOrderNumber", source = "webOrderRefId"),
            @Mapping(target = "fsOrderNumber", source = "id"),
            @Mapping(target = "csrSalesRepID", ignore = true),
            @Mapping(target = "ipAddress", source = "ipAddress"),
            @Mapping(target = "orderCreationTime", ignore = true),
            @Mapping(target = "totalAmount", ignore = true),
            @Mapping(target = "rewardZoneID", source = "rewardZone.rewardZoneId"),
            @Mapping(target = "shippingOrders", source = "shippingOrders"),
            @Mapping(target = "shippingAddresses", ignore = true),
            @Mapping(target = "paymentDetails", source = "paymentMethodInfo")
    })
    Order mapOrder(FSOrder fsOrderToMap);


    @Mappings({
        @Mapping(target = "purchaseOrderID", ignore = true),
        @Mapping(target = "shippingOrderID", source = "id"),
        @Mapping(target = "globalContractID", source = "globalContractRefId"),
        @Mapping(target = "shippingOrderStatus", source = "status.name"),
        @Mapping(target = "purchaseOrderStatus", ignore = true),
        @Mapping(target = "shippingCharge", ignore = true),
        @Mapping(target = "shippingChargeTax", ignore = true),
        @Mapping(target = "fulfillmentPartner", source = "fulfillmentPartner"),
        @Mapping(target = "shippingDetails.shippingMethod", ignore = true),
        @Mapping(target = "shippingDetails.carrierCode", source = "actualCarrier.levelOfService.carrierCode"),
        @Mapping(target = "shippingDetails.serviceLevel", source = "actualCarrier.levelOfService.name"),
        @Mapping(target = "shippingDetails.deadline", ignore = true),
        @Mapping(target = "shippingDetails.shippingAddress", source = "shipToAddress"),
        @Mapping(target = "shippingOrderLines", source = "shippingOrderLines"),
        @Mapping(target = "chargebacks", ignore = true)
    })
    ShippingOrder mapShippingOrder(com.bbyc.orders.model.client.orderdetails.ShippingOrder shippingOrderToMap);


    @Mappings({
        @Mapping(target = "lineNumber", source = "id"),
        @Mapping(target = "description", ignore = true),
        @Mapping(target = "status", source = "status.name"),
        @Mapping(target = "price", source = "unitPrice"),
        @Mapping(target = "quantity", source = "qtyOrdered"),
        @Mapping(target = "staffDiscount", ignore = true),
        @Mapping(target = "postCaptureDiscount", ignore = true),
        @Mapping(target = "salesTax", ignore = true),
        @Mapping(target = "itemTax", ignore = true),
        @Mapping(target = "productSalesTax", ignore = true),
        @Mapping(target = "shippingChargeTax", ignore = true),
        @Mapping(target = "environmentHandlingFeeTax", ignore = true)
    })
    ShippingOrder.OrderLine mapShippingOrderLine(ShippingOrderLine shippingOrderLineToMap);


    @Mappings({
        @Mapping(target = "creditCards", source = "creditCards"),
        @Mapping(target = "giftCards", source = "giftCards"),
        @Mapping(target = "payPal", ignore = true)
    })
    PaymentDetails mapPaymentDetails(PaymentMethodInfo paymentDetailsToMap);


    @Mappings({
        @Mapping(target = "totalAuthorizedAmount", ignore = true),
        @Mapping(target = "avsResponse", ignore = true),
        @Mapping(target = "cvvResponse", ignore = true),
        @Mapping(target = "secureValue3D", ignore = true)
    })
    PaymentDetails.CreditCard mapCreditCard(CreditCardInfo creditCardToMap);


    @Mappings({
        @Mapping(target = "phoneNumber", source = "phone"),
        @Mapping(target = "secondaryPhoneNumber", source = "phone2")
    })
    Address mapAddress(com.bbyc.orders.model.client.orderdetails.Address addressToMap);


}
