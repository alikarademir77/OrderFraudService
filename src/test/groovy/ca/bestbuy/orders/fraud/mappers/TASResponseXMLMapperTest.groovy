package ca.bestbuy.orders.fraud.mappers

import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ActionCode
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ArrayOfString1
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ResponseData
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.Transaction
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.TransactionResults
import ca.bestbuy.orders.fraud.model.internal.FraudAssesmentResult
import org.mapstruct.factory.Mappers
import spock.lang.Shared
import spock.lang.Specification

class TASResponseXMLMapperTest extends Specification{

    @Shared
    TASResponseXMLMapper xmlMapper

    def setupSpec() {
        xmlMapper = Mappers.getMapper(TASResponseXMLMapper.class)
    }


    def "test Transaction Results mapper"(){
        given:

        ManageOrderResponse manageOrderResponse = new ManageOrderResponse()
        manageOrderResponse.setActionCode(ActionCode.SUCCESS)
        manageOrderResponse.setErrorDescription("errorDesc")

        TransactionResults transactionResults = new TransactionResults()
        transactionResults.setTransactionId("transactionId");
        transactionResults.setCrossReference("crossRef");
        transactionResults.setRulesTripped("rulesTripped");
        transactionResults.setTotalScore("totalScore");
        transactionResults.setRecommendationCode("recCode");
        transactionResults.setRemarks("remarks");

        ResponseData responseData = new ResponseData()
        responseData.setResponseVersion(1)

        Transaction transaction = new Transaction();
        transaction.setReasonCode("reasonCode")
        transaction.setReasonDescription("reasonDescription")
        ArrayOfString1 transactionDetails = new ArrayOfString1()
        transactionDetails.getTransactionDetail().add("detail1")
        transactionDetails.getTransactionDetail().add("detail2")
        transaction.setTransactionDetails(transactionDetails)
        responseData.setTransaction(transaction)

        transactionResults.setResponseData(responseData);
        manageOrderResponse.setTransactionResults(transactionResults)


        when:

        FraudAssesmentResult fraudResult = xmlMapper.mapManageOrderResult(manageOrderResponse)

        then:

        fraudResult.getFraudResponseStatus().toString() == "ACCEPTED"
        fraudResult.getOrderNumber() == transactionResults.getTransactionId()
        fraudResult.getTotalFraudScore() == transactionResults.getTotalScore()
        fraudResult.getRecommendationCode() == transactionResults.getRecommendationCode()
        fraudResult.getRequestVersion() == transactionResults.getResponseData().getResponseVersion()

    }


    def "test FraudResponseStatus mapper"(){

        given:
        ManageOrderResponse manageOrderResponse1 = new ManageOrderResponse()
        manageOrderResponse1.setActionCode(ActionCode.SUCCESS)

        ManageOrderResponse manageOrderResponse2 = new ManageOrderResponse()
        manageOrderResponse2.setActionCode(ActionCode.CALLBANK)

        ManageOrderResponse manageOrderResponse3 = new ManageOrderResponse()
        manageOrderResponse3.setActionCode(ActionCode.SOFTDECLINE)

        ManageOrderResponse manageOrderResponse4 = new ManageOrderResponse()
        manageOrderResponse4.setActionCode(ActionCode.DECLINED)


        when:
        FraudAssesmentResult fraudResult1 = xmlMapper.mapManageOrderResult(manageOrderResponse1)
        FraudAssesmentResult fraudResult2 = xmlMapper.mapManageOrderResult(manageOrderResponse2)
        FraudAssesmentResult fraudResult3 = xmlMapper.mapManageOrderResult(manageOrderResponse3)
        FraudAssesmentResult fraudResult4 = xmlMapper.mapManageOrderResult(manageOrderResponse4)

        then:

        fraudResult1.getFraudResponseStatus().toString() == "ACCEPTED"
        fraudResult2.getFraudResponseStatus().toString() == "PENDING_REVIEW"
        fraudResult3.getFraudResponseStatus().toString() == "SOFT_DECLINE"
        fraudResult4.getFraudResponseStatus().toString() == "HARD_DECLINE"



    }


}
