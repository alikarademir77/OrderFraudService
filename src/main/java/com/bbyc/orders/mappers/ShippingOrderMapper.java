package com.bbyc.orders.mappers;

import com.bbyc.orders.model.internal.ShippingOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring",uses = {AddressMapper.class, ShippingOrderLinesMapper.class})
public interface ShippingOrderMapper {



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


}
