package ca.bestbuy.orders.fraud.model.client.resourcesapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter @ToString @EqualsAndHashCode
public class ResourceApiRequest {

    @JsonProperty("requests")
    private List<ResourceApiItem> resourceApiItemList;
}
