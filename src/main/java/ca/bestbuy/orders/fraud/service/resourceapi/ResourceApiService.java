package ca.bestbuy.orders.fraud.service.resourceapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.bestbuy.orders.fraud.model.client.resourceapi.ProductDetail;

public class ResourceApiService {
    public Map<String, ProductDetail> getDetail(List<String> sku) {

        Map<String, ProductDetail> result = new HashMap<>();
        //TODO convert list SKU to Json
        //Make https call to get data
        //convert result json to Map

        return result;
    }
}
