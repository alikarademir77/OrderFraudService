package com.bbyc.orders.mappers;

import com.bbyc.orders.model.internal.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface AddressMapper {


    @Mappings({

            @Mapping(target = "phoneNumber", source = "phone"),
            @Mapping(target = "secondaryPhoneNumber", source = "phone2")
    })
    Address mapAddress(com.bbyc.orders.model.client.orderdetails.Address addressToMap);


}
