package ca.bestbuy.orders.fraud.model.client.resourceapi.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestHeaders {

    @JsonProperty("Accept")
    private String accept;

}
