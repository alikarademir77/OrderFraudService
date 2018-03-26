package ca.bestbuy.orders.fraud.model.client.resourceapi.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResourceApiItem {

    @JsonProperty("id")
    private String id;

    @JsonProperty("headers")
    private RequestHeaders headers;


}
