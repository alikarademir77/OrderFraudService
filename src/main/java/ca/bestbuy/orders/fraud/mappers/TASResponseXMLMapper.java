package ca.bestbuy.orders.fraud.mappers;

import ca.bestbuy.orders.fraud.model.client.generated.tas.wsdl.ManageOrderResponse;
import ca.bestbuy.orders.fraud.model.internal.FraudResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;


@Mapper(uses = ObjectFactory.class, componentModel = "spring")
public abstract class TASResponseXMLMapper {


    @Mappings({
            @Mapping(target="actionCode", source="actionCode"),
            @Mapping(target="errorDescription", source="errorDescription"),
            @Mapping(target="transactionId", source="transactionResults.transactionId"),
            @Mapping(target="version", source="transactionResults.responseData.responseVersion"),
            @Mapping(target="crossReference", source="transactionResults.crossReference"),
            @Mapping(target="rulesTripped", source="transactionResults.rulesTripped"),
            @Mapping(target="totalScore", source="transactionResults.totalScore"),
            @Mapping(target="recommendationCode", source="transactionResults.recommendationCode"),
            @Mapping(target="remarks", source="transactionResults.remarks"),
            @Mapping(target="reasonCode", source="transactionResults.responseData.transaction.reasonCode"),
            @Mapping(target="reasonDescription", source="transactionResults.responseData.transaction.reasonDescription"),
            @Mapping(target="transactionDetails", source="transactionResults.responseData.transaction.transactionDetails.transactionDetail")
    })
    public abstract FraudResult mapManageOrderResult(ManageOrderResponse manageOrderResponseToMap);




}
