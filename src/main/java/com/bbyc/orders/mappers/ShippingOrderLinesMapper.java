package com.bbyc.orders.mappers;

import com.bbyc.orders.model.client.orderdetails.ShippingOrderLine;
import com.bbyc.orders.model.internal.ShippingOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface ShippingOrderLinesMapper {


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
            @Mapping(target = "environmentHandlingFeeTax", ignore = true),


    })
    ShippingOrder.OrderLine mapShippingOrderLine(ShippingOrderLine shippingOrderLineToMap);


}
