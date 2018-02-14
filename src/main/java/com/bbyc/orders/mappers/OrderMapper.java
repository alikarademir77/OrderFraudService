package com.bbyc.orders.mappers;

import com.bbyc.orders.model.client.orderdetails.FSOrder;
import com.bbyc.orders.model.internal.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",uses = {ShippingOrderMapper.class, PaymentDetailMapper.class})
public interface OrderMapper {

    //map Order

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
            @Mapping(target = "paymentDetails", ignore = true)
            //@Mapping(target = "paymentDetails", source = "paymentMethodInfo")
    })
    Order mapOrder(FSOrder fsOrderToMap);

    //


}
