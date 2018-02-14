package com.bbyc.orders.mappers;

import com.bbyc.orders.model.client.orderdetails.CreditCardInfo;
import com.bbyc.orders.model.internal.PaymentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",uses = {AddressMapper.class})
public interface CreditCardMapper {



    @Mappings({

            @Mapping(target = "totalAuthorizedAmount", ignore = true),
            @Mapping(target = "avsResponse", ignore = true),
            @Mapping(target = "cvvResponse", ignore = true),
            @Mapping(target = "secureValue3D", ignore = true)

    })
    PaymentDetails.CreditCard mapCreditCard(CreditCardInfo creditCardToMap);



}
