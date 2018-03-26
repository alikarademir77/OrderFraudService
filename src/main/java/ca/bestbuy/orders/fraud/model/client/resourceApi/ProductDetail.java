package ca.bestbuy.orders.fraud.model.client.resourceapi;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDetail {

    private String sku;
    private String department;
    private String itemClass;
    private String itemSubClass;
}
