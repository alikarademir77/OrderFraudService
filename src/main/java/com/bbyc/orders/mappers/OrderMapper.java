package com.bbyc.orders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.bbyc.orders.model.client.orderdetails.CreditCardInfo;
import com.bbyc.orders.model.client.orderdetails.FSOrder;
import com.bbyc.orders.model.client.orderdetails.FSOrderLine;
import com.bbyc.orders.model.client.orderdetails.PaymentMethodInfo;
import com.bbyc.orders.model.client.orderdetails.PurchaseOrder;
import com.bbyc.orders.model.client.orderdetails.ShippingOrderLine;
import com.bbyc.orders.model.internal.Address;
import com.bbyc.orders.model.internal.Item;
import com.bbyc.orders.model.internal.Order;
import com.bbyc.orders.model.internal.PaymentDetails;
import com.bbyc.orders.model.internal.ShippingOrder;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mappings({
            @Mapping(target = "webOrderRefID", source = "webOrderRefId"),
            @Mapping(target = "fsOrderID", source = "id"),
            @Mapping(target = "csrSalesRepID", ignore = true),
            @Mapping(target = "ipAddress", source = "ipAddress"),
            @Mapping(target = "orderCreationTime", ignore = true),
            @Mapping(target = "rewardZoneID", source = "rewardZone.rewardZoneId"),
            @Mapping(target = "items", source = "fsOrderLines"),
            @Mapping(target = "purchaseOrders", source = "purchaseOrders"),
            @Mapping(target = "shippingOrders", source = "shippingOrders"),
            @Mapping(target = "paymentDetails", source = "paymentMethodInfo")
    })
    Order mapOrder(FSOrder fsOrderToMap);


    @Mappings({
        @Mapping(target = "name", source = "product.name"),
        @Mapping(target = "category", ignore = true),
        @Mapping(target = "itemPrice", source = "itemCharge.unitPrice"),
        @Mapping(target = "itemTax", expression = "java(fsOrderLineToMap.getItemCharge().getTax().getGst() + fsOrderLineToMap.getItemCharge().getTax().getPst())"),
        @Mapping(target = "itemDiscounts", source = "itemCharge.discounts")
    })
    Item mapItem(FSOrderLine fsOrderLineToMap);


    @Mappings({
        @Mapping(target = "purchaseOrderID", source = "id"),
        @Mapping(target = "status", source = "poSendStatus.name"),
        @Mapping(target = "shippingOrderRefID", source = "shippingOrderRefId")
    })
    com.bbyc.orders.model.internal.PurchaseOrder mapPurchaseOrder(PurchaseOrder purchaseOrderToMap);


    @Mappings({
            @Mapping(target = "shippingOrderID", source = "id"),
            @Mapping(target = "globalContractID", source = "globalContractRefId"),
            @Mapping(target = "status", source = "status.name"),
            @Mapping(target = "shippingCharge", ignore = true),
            @Mapping(target = "fulfillmentPartner", source = "fulfillmentPartner"),
            @Mapping(target = "shippingMethod", source = "requestedCarrier.levelOfService.id"),
            @Mapping(target = "deliveryDate", ignore = true),
            @Mapping(target = "shippingAddress", source = "shipToAddress"),
            @Mapping(target = "shippingOrderLines", source = "shippingOrderLines"),
            @Mapping(target = "chargebacks", ignore = true)
    })
    ShippingOrder mapShippingOrder(com.bbyc.orders.model.client.orderdetails.ShippingOrder shippingOrderToMap);


    @Mappings({
            @Mapping(target = "shippingOrderLineID", source = "id"),
            @Mapping(target = "status", source = "status.name"),
            @Mapping(target = "quantity", source = "qtyOrdered"),
            @Mapping(target = "shippingCharge", ignore = true),
            @Mapping(target = "shippingTax", ignore = true),
            @Mapping(target = "shippingDiscount", ignore = true),
            @Mapping(target = "ehf", ignore = true),
            @Mapping(target = "ehfTax", ignore = true)
    })
    com.bbyc.orders.model.internal.ShippingOrderLine mapShippingOrderLine(ShippingOrderLine shippingOrderLineToMap);


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
