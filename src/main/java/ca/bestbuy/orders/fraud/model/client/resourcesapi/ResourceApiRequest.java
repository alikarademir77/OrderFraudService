package ca.bestbuy.orders.fraud.model.client.resourcesapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class ResourceApiRequest {

    @JsonProperty("requests")
    private List<ResourceApiItem> resourceApiItemList;
}
