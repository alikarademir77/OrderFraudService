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
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@Slf4j
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

    public List<ProductDetail> toProductDetail(List<String> listOfIds,String jsonResult) {

        if(jsonResult == null || jsonResult.trim().equals("")){
            throw new IllegalArgumentException("Response from resource service is empty");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDetail> listOfProductDetail = new ArrayList<>();
        try {
            JsonNode responseJsonNode = objectMapper.readTree(jsonResult);
            for (String id:listOfIds){
                ProductDetail productDetail = getProductDetail(id,responseJsonNode);
                listOfProductDetail.add(productDetail);
            }
        } catch (IOException e) {
            log.error("Response is not valid " + jsonResult , e);
            throw new IllegalArgumentException("Respons is not JSON format", e);
        }

        return listOfProductDetail;

    }

    private ProductDetail getProductDetail(String id,JsonNode responseJsonNode){

        ProductDetail productDetail = new ProductDetail();
        int status = responseJsonNode.path(id).path("status").asInt();
        if(status == 200){
            String sku = responseJsonNode.path(id).path("body").path("id").asText();
            String depatmentId = "" + responseJsonNode.path(id).path("body").path("rmsDeptID").asInt();
            String classId = "" + responseJsonNode.path(id).path("body").path("rmsClassID").asInt();
            String subClassId = "" + responseJsonNode.path(id).path("body").path("rmsSubclassID").asInt();
            productDetail.setSku(sku);
            productDetail.setDepartment(depatmentId);
            productDetail.setItemClass(classId);
            productDetail.setItemSubClass(subClassId);
        }else{
            String errorMessage = responseJsonNode.path(id).path("body").asText();
            log.info("Error  for id :" + id + "error message :" + errorMessage);

        }


        return productDetail;


    }
}
