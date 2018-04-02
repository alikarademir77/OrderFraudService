package ca.bestbuy.orders.fraud.service;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.l;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.bestbuy.orders.fraud.client.ResourceApiClientImpl;
import ca.bestbuy.orders.fraud.mappers.ResourceApiJsonConverter;
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ProductDetail;
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResourceApiService {

    ResourceApiJsonConverter resourceApiJsonConverter;

    ResourceApiClientImpl resourceApiClientImpl;
    public ResourceApiService(ResourceApiJsonConverter resourceApiJsonConverter,ResourceApiClientImpl resourceApiClientImpl){

        this.resourceApiJsonConverter = resourceApiJsonConverter;

        this.resourceApiClientImpl = resourceApiClientImpl;

    }

    public Map<String, ProductDetail> getProductDetail(List<String> skuList) {

        ResourceApiRequest resourceApiRequest =  resourceApiJsonConverter.toResourceApiRequest(skuList);

        logJson("Request to post to Resource API : ", resourceApiRequest);

        String responseJson = resourceApiClientImpl.getData(resourceApiRequest);

        logJson("Response we get from Resource API : ", responseJson);

        Map<String, ProductDetail> result = resourceApiJsonConverter.toProductDetail(skuList,responseJson);

        return result;
    }

    private void logJson(String message, Object data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(message + " " + objectMapper.writeValueAsString(data));
        } catch (Exception e) {
            log.error("Fail to  convert an object to json when we try logging json data");
        }
    }

}
