package ca.bestbuy.orders.fraud.service.resourceapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.bestbuy.orders.fraud.model.client.resourceapi.ProductDetail;
import ca.bestbuy.orders.fraud.model.client.resourceapi.data.RequestHeaders;
import ca.bestbuy.orders.fraud.model.client.resourceapi.data.ResourceApiItem;
import ca.bestbuy.orders.fraud.model.client.resourceapi.data.ResourceApiRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.CollectionUtils;

public class JsonConverter {

    private String accept="application/vnd.bestbuy.productdetails+json";

    public ResourceApiRequest toResourceApiRequest(List<String> skuList) {

        if (CollectionUtils.isEmpty(skuList)){
            throw new IllegalArgumentException("skuList cann't be empty");
        }

        ResourceApiRequest resourceApiRequest = new ResourceApiRequest();

        List<ResourceApiItem> resourceApiItemList = new ArrayList<>();

        for (String sku: skuList){
            RequestHeaders requestHeaders= new RequestHeaders();
            requestHeaders.setAccept(accept);
            ResourceApiItem resourceApiItem = new ResourceApiItem();
            resourceApiItem.setHeaders(requestHeaders);
            resourceApiItem.setId("catalog/products/"+sku +"/details");
            resourceApiItemList.add(resourceApiItem);
        }
        resourceApiRequest.setResourceApiItemList(resourceApiItemList);

        return resourceApiRequest;
    }

    public ProductDetail toProductDetail(String jsonResult) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode responseJsonNode = objectMapper.readTree(jsonResult);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
