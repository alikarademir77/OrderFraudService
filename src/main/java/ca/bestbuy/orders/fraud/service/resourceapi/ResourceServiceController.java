package ca.bestbuy.orders.fraud.service.resourceapi;

import ca.bestbuy.orders.fraud.model.client.resourceapi.data.ResourceApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kundsing on 2018-03-23.
 */
@RestController
public class ResourceServiceController {

    @Autowired
    ResourceApiCaller resourceApiCaller;
    @RequestMapping(value = "/products",method = RequestMethod.GET, produces = "application/json")
    public String getProductDetails(){

        List<String> sku = new ArrayList<>();
        sku.add("12345678");
        sku.add("23456789");
        JsonConverter jsonConverter = new JsonConverter();
        ResourceApiRequest request= jsonConverter.toResourceApiRequest(sku);


        return resourceApiCaller.callForData(request);
    }
}
