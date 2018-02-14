package com.bbyc.orders.mappers;

import com.bbyc.orders.model.client.orderdetails.PaymentMethodInfo;
import com.bbyc.orders.model.internal.PaymentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring",uses = {CreditCardMapper.class})
public interface PaymentDetailMapper {


    @Mappings({

            @Mapping(target = "creditCards", source = "creditCards"),
            @Mapping(target = "giftCards", source = "giftCards"),
            @Mapping(target = "payPal", ignore = true)

    })
    PaymentDetails mapPaymentDetails(PaymentMethodInfo paymentDetailsToMap);

}
