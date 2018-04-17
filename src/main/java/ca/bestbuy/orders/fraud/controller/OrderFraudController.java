package ca.bestbuy.orders.fraud.controller;

import ca.bestbuy.orders.fraud.callback.model.swagger.api.FraudResult;
import ca.bestbuy.orders.fraud.callback.model.swagger.api.FraudResultDetail;
import ca.bestbuy.orders.fraud.exception.ErrorHandler;
import ca.bestbuy.orders.fraud.exception.ValidationException;
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ProductDetail;
import ca.bestbuy.orders.fraud.model.internal.FraudAssessmentResult;
import ca.bestbuy.orders.fraud.service.ResourceApiService;
import com.google.common.base.Enums;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by kundsing on 2018-04-04.
 */

@RestController
@RequestMapping(value="/fraudchecktransactions")
@Api(value="orderfraud", description="Operations for retrieving and update fraud status")
@Slf4j
public class OrderFraudController {

    @Autowired
    ResourceApiService resourceApiService;
    @ApiOperation(value = "Update fraud result for pending review order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation"),
            @ApiResponse(code = 400, message = "Bad Request",response = ErrorHandler.ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error",response = ErrorHandler.ErrorResponse.class)
    })
    @RequestMapping(value = "/{transactionId}/{versionId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity saveFraudStatus(
            @ApiParam(name="transactionId", required=true, value="Order Number")
            @PathVariable String transactionId,
            @ApiParam(name="versionId", required=true, value="Order Fraud request Vesrion")
            @PathVariable Integer versionId,
            @RequestBody FraudResult fraudResult){

            validateCallBackrequest(fraudResult);
            /** TO DO implementation **/

        return new ResponseEntity("Fraud result saved successfully", HttpStatus.OK);
    }



//    @RequestMapping(value="/list/{orderId}", method = RequestMethod.GET, produces = "application/json")
//    public ResponseEntity getFraudStatus(
//            @ApiParam(name="orderId", required=true, value="FS Order Id")
//            @PathVariable String orderId
//    ) {
//
//
//        return new ResponseEntity("Fraud result saved successfully", HttpStatus.OK);
//    }

    private void validateCallBackrequest(FraudResult fraudResult) throws ValidationException {

        String exceptionMessage = "";
        if (fraudResult == null){
            exceptionMessage = "The request body is null or empty";
        } else if (StringUtils.isBlank(fraudResult.getUpdateUser())){
            exceptionMessage = "User name in request is null or empty";
        } else if (fraudResult.getFraudResultDetail() == null) {
            exceptionMessage = "Result detail in fruad call is null";
        } else if (!decisionMadeResponseStatusCodes().contains(fraudResult.getFraudResultDetail().getActionCode().getValue())) {
            exceptionMessage = "Action code in fraud call back result is having invalid action code ";
        }

        if (StringUtils.isNotBlank(exceptionMessage)) {
            log.error("Validation failed on fraud call back request - {}", exceptionMessage);
            throw new ValidationException(exceptionMessage);
        }

    }

    private List<String> decisionMadeResponseStatusCodes(){
        return Arrays.asList(
                FraudAssessmentResult.FraudResponseStatusCodes.ACCEPTED.toString(),
                FraudAssessmentResult.FraudResponseStatusCodes.HARD_DECLINE.toString(),
                FraudAssessmentResult.FraudResponseStatusCodes.SOFT_DECLINE.toString()
        );
    }
}
