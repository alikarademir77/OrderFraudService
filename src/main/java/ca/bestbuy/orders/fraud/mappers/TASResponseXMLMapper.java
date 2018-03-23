package ca.bestbuy.orders.fraud.mappers;

import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse;
import ca.bestbuy.orders.fraud.model.internal.FraudResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;


@Mapper(uses = ObjectFactory.class, componentModel = "spring")
public abstract class TASResponseXMLMapper {


    @Mappings({
            @Mapping(target="fraudResponseStatus", source="actionCode"),
            @Mapping(target="orderNumber", source="transactionResults.transactionId"),
            @Mapping(target="requestVersion", source="transactionResults.responseData.responseVersion"),
            @Mapping(target="totalFraudScore", source="transactionResults.totalScore"),
            @Mapping(target="recommendationCode", source="transactionResults.recommendationCode"),
            @Mapping(target="accertifyUser", ignore=true),
            @Mapping(target="accertifyUserActionTime", ignore=true),
    })
    public abstract FraudResult mapManageOrderResult(ManageOrderResponse manageOrderResponseToMap);




}
