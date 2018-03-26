package ca.bestbuy.orders.fraud.service.resourceapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bestbuy.orders.fraud.model.client.resourceapi.ProductDetail;
import ca.bestbuy.orders.fraud.model.client.resourceapi.data.ResourceApiRequest;

@Service
public class ResourceApiService {

    @Autowired
    JsonConverter jsonConverter;

    @Autowired
    ResourceApiCaller resourceApiCaller;

    public Map<String, ProductDetail> getDetail(List<String> skuList) {

        Map<String, ProductDetail> result = new HashMap<>();

        ResourceApiRequest resourceApiRequest =  jsonConverter.toResourceApiRequest(skuList);
        String responseJson = resourceApiCaller.callForData(resourceApiRequest);
        jsonConverter.toProductDetail(skuList,responseJson);
        //TODO convert list SKU to Json
        //Make https call to get data
        //convert result json to Map

        return result;
    }
}
