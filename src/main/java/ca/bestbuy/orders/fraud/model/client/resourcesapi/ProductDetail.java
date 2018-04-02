package ca.bestbuy.orders.fraud.model.client.resourcesapi;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class ProductDetail {

    private String sku;
    private String department;
    private String itemClass;
    private String itemSubClass;
}
