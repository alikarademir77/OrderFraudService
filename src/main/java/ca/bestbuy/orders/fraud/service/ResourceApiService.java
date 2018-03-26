package ca.bestbuy.orders.fraud.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bestbuy.orders.fraud.client.ResourceApiClientImpl;
import ca.bestbuy.orders.fraud.mappers.ResourceApiJsonConverter;
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ProductDetail;
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiRequest;

@Service
public class ResourceApiService {

    @Autowired
    ResourceApiJsonConverter resourceApiJsonConverter;

    @Autowired
    ResourceApiClientImpl resourceApiClientImpl;

    public Map<String, ProductDetail> getProductDetail(List<String> skuList) {

        ResourceApiRequest resourceApiRequest =  resourceApiJsonConverter.toResourceApiRequest(skuList);
        String responseJson = resourceApiClientImpl.getData(resourceApiRequest);
        Map<String, ProductDetail> result = resourceApiJsonConverter.toProductDetail(skuList,responseJson);

        return result;
    }
}
