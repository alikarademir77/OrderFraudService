package ca.bestbuy.orders.fraud.model.client.resourceapi.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class ResourceApiItem {

    @JsonProperty("id")
    @Getter @Setter String id;

    @JsonProperty("headers")
    @Getter @Setter RequestHeaders headers;


}
