package ca.bestbuy.orders.fraud.model.client.resourcesapi;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class RequestHeaders {

    @JsonProperty("Accept")
    private String accept;

}
