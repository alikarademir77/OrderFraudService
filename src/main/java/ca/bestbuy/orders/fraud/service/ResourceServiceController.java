package ca.bestbuy.orders.fraud.service;

import ca.bestbuy.orders.fraud.client.ResourceApiClientImpl;
import ca.bestbuy.orders.fraud.mappers.ResourceApiJsonConverter;
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ResourceServiceController {

    @Autowired
    ResourceApiClientImpl resourceApiClientImpl;
    @RequestMapping(value = "/products",method = RequestMethod.GET, produces = "application/json")
    public String getProductDetails(){

        List<String> sku = new ArrayList<>();
        sku.add("12345678");
        sku.add("23456789");
        ResourceApiJsonConverter resourceApiJsonConverter = new ResourceApiJsonConverter();
        ResourceApiRequest request= resourceApiJsonConverter.toResourceApiRequest(sku);


        return resourceApiClientImpl.getData(request);
    }
}
