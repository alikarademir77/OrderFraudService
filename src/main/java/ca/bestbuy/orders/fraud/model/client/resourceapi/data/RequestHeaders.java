package ca.bestbuy.orders.fraud.model.client.resourceapi.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class RequestHeaders {

    @JsonProperty("Accept")
    @Getter @Setter private String accept;

}
