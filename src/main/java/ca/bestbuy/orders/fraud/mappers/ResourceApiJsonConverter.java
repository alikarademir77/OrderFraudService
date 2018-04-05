package ca.bestbuy.orders.fraud.mappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.bestbuy.orders.fraud.model.client.resourcesapi.ProductDetail;
import ca.bestbuy.orders.fraud.model.client.resourcesapi.RequestHeaders;
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiItem;
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
public class ResourceApiJsonConverter {

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
            resourceApiItem.setId(formResourceApiIdBySku(sku));
            resourceApiItemList.add(resourceApiItem);
        }
        resourceApiRequest.setResourceApiItemList(resourceApiItemList);

        return resourceApiRequest;
    }

    public Map<String,ProductDetail> toProductDetail(List<String> skuList,String jsonResult) {

        if(StringUtils.isBlank(jsonResult)){
            throw new IllegalArgumentException("Response from resource service is empty");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, ProductDetail> productDetailMap = new HashMap<>();
        try {
            JsonNode responseJsonNode = objectMapper.readTree(jsonResult);
            for (String sku : skuList){
                ProductDetail productDetail = getProductDetail(formResourceApiIdBySku(sku),responseJsonNode);
                productDetail.setSku(sku);
                productDetailMap.put(sku, productDetail);
            }
        } catch (IOException e) {
            log.error("Response is not valid " + jsonResult , e);
            throw new IllegalArgumentException("Response is not JSON format", e);
        }

        return productDetailMap;

    }

    private ProductDetail getProductDetail(String id, JsonNode responseJsonNode){

        //If resource API is up, but the sku is not in the response, we will return null data
        //without throwing exceptions
        ProductDetail productDetail = new ProductDetail();
        int status = responseJsonNode.path(id).path("status").asInt();
        if (status == 200) {
            JsonNode bodyNode = responseJsonNode.path(id).path("body");
            if (bodyNode.has("rmsDeptID")) {
                String departmentId = Integer.toString(bodyNode.path("rmsDeptID").asInt());
                productDetail.setDepartment(departmentId);
            }

            if (bodyNode.has("rmsClassID")) {
                String classId = Integer.toString(bodyNode.path("rmsClassID").asInt());
                productDetail.setItemClass(classId);
            }

            if (bodyNode.has("rmsSubclassID")) {
                String subClassId = Integer.toString(bodyNode.path("rmsSubclassID").asInt());
                productDetail.setItemSubClass(subClassId);
            }

        } else {

            String errorMessage = responseJsonNode.path(id).path("body").asText();
            log.info("Error for id :" + id + " error message :" + errorMessage);

        }
        return productDetail;
    }

    private String formResourceApiIdBySku(String sku) {
        return new StringBuilder().append("catalog/products/").append(sku).append("/details").toString();
    }
}
