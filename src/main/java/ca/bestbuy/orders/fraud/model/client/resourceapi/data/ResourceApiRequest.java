package ca.bestbuy.orders.fraud.model.client.resourceapi.data;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourceApiRequest {

    @JsonProperty("id")
    String id;

    @JsonProperty("headers")
    RequestHeaders headers;


}
