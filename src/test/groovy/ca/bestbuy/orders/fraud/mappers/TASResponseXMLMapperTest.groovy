package ca.bestbuy.orders.fraud.mappers

import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ActionCode
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ArrayOfString1
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ManageOrderResponse
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.ResponseData
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.Transaction
import ca.bestbuy.orders.fraud.model.client.accertify.wsdl.TransactionResults
import ca.bestbuy.orders.fraud.model.internal.FraudResult
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
        responseData.setResponseVersion("1")

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

        FraudResult fraudResult = xmlMapper.mapManageOrderResult(manageOrderResponse)

        then:

        fraudResult.getActionCode() == manageOrderResponse.getActionCode().toString()
        fraudResult.getErrorDescription() == manageOrderResponse.getErrorDescription()
        fraudResult.getTransactionId() == transactionResults.getTransactionId()
        fraudResult.getCrossReference() == transactionResults.getCrossReference()
        fraudResult.getRulesTripped() == transactionResults.getRulesTripped()
        fraudResult.getTotalScore() == transactionResults.getTotalScore()
        fraudResult.getRecommendationCode() == transactionResults.getRecommendationCode()
        fraudResult.getRemarks() == transactionResults.getRemarks()
        fraudResult.getVersion() == transactionResults.getResponseData().getResponseVersion()
        fraudResult.getReasonCode() == transactionResults.getResponseData().getTransaction().getReasonCode()
        fraudResult.getReasonDescription() == transactionResults.getResponseData().getTransaction().getReasonDescription()
        if(fraudResult.getTransactionDetails().size() == transactionDetails.getTransactionDetail().size()) {
            for (int i = 0; i < fraudResult.getTransactionDetails().size(); i++){
                String transactionDetailToMap = transactionDetails.getTransactionDetail().get(i)
                fraudResult.getTransactionDetails().get(i).toString() == transactionDetailToMap
            }
        }else{
            assert false
        }



    }

}
