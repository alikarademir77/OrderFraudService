package ca.bestbuy.orders.fraud.model.client.resourceapi.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by kundsing on 2018-03-22.
 */
public class ResourceApiRequest {



    @JsonProperty("requests")
    @Getter
    @Setter
    List<ResourceApiItem> resourceApiItemList;
}
