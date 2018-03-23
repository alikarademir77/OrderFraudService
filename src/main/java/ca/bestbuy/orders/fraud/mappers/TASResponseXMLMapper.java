package ca.bestbuy.orders.fraud.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;

import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ActionCode;
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse;
import ca.bestbuy.orders.fraud.model.internal.FraudAssesmentResult;


@Mapper(uses = ObjectFactory.class, componentModel = "spring")
public abstract class TASResponseXMLMapper {


    @Mappings({
            @Mapping(target="fraudResponseStatus", ignore=true),
            @Mapping(target="orderNumber", source="transactionResults.transactionId"),
            @Mapping(target="requestVersion", source="transactionResults.responseData.responseVersion"),
            @Mapping(target="totalFraudScore", source="transactionResults.totalScore"),
            @Mapping(target="recommendationCode", source="transactionResults.recommendationCode"),
            @Mapping(target="accertifyUser", ignore=true),
            @Mapping(target="accertifyUserActionTime", ignore=true),
    })

    public abstract FraudAssesmentResult mapManageOrderResult(ManageOrderResponse manageOrderResponseToMap);


    @AfterMapping
    public void mapManageOrderResult_FraudResponseStatus(ManageOrderResponse manageOrderResponseToMap, @MappingTarget FraudAssesmentResult mappedFraudAssessmentResult ){

        if(manageOrderResponseToMap.getActionCode().equals(ActionCode.SUCCESS)){
            mappedFraudAssessmentResult.setFraudResponseStatus(FraudAssesmentResult.FraudResponseStatusCodes.ACCEPTED);
        }else if(manageOrderResponseToMap.getActionCode().equals(ActionCode.CALLBANK)) {
            mappedFraudAssessmentResult.setFraudResponseStatus(FraudAssesmentResult.FraudResponseStatusCodes.PENDING_REVIEW);
        }else if(manageOrderResponseToMap.getActionCode().equals(ActionCode.SOFTDECLINE)) {
            mappedFraudAssessmentResult.setFraudResponseStatus(FraudAssesmentResult.FraudResponseStatusCodes.SOFT_DECLINE);
        }else if(manageOrderResponseToMap.getActionCode().equals(ActionCode.DECLINED)) {
            mappedFraudAssessmentResult.setFraudResponseStatus(FraudAssesmentResult.FraudResponseStatusCodes.HARD_DECLINE);
        }

    }

}
