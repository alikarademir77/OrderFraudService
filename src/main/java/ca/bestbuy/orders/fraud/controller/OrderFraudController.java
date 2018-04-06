package ca.bestbuy.orders.fraud.controller;

import ca.bestbuy.orders.fraud.callback.model.swagger.api.FraudResult;
import ca.bestbuy.orders.fraud.callback.model.swagger.api.FraudResultDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kundsing on 2018-04-04.
 */

@RestController
@RequestMapping(value="/fraudchecktransactions")
@Api(value="orderfraud", description="Operations for retrieving and update fraud status")
public class OrderFraudController {

    @ApiOperation(value = "Update fraud result for pending review order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/{transactionId}/{versionId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity saveFraudStatus(
            @ApiParam(name="transactionId", required=true, value="Order Number")
            @PathVariable String transactionId,
            @ApiParam(name="versionId", required=true, value="Order Fraud request Vesrion")
            @PathVariable Integer versionId,
            @RequestBody FraudResult fraudResult){

           FraudResultDetail fraudResultDetail = fraudResult.getFraudResultDetail();
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
}
