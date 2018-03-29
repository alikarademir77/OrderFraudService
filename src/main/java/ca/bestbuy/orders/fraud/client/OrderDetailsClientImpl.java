package ca.bestbuy.orders.fraud.client;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ca.bestbuy.orders.fraud.model.client.generated.orderdetails.swagger.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import ca.bestbuy.orders.fraud.mappers.OrderMapper;
import ca.bestbuy.orders.fraud.model.internal.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDetailsClientImpl implements OrderDetailsClient {

    private OrderMapper orderMapper;
    private RestTemplate restTemplate;
    private String orderDetailsServiceBaseUrl;
    private String getOrderDetailsEndpoint;


    public OrderDetailsClientImpl(OrderMapper orderMapper, RestTemplate restTemplate) {

        if(orderMapper == null) {
            throw new IllegalArgumentException("OrderMapper provided to OrderDetailsClientImpl must not be null");
        }

        if(restTemplate == null) {
            throw new IllegalArgumentException("RestTemplate provided to OrderDetailsClientImpl must not be null");
        }

        this.orderMapper = orderMapper;
        this.restTemplate = restTemplate;
    }


    public void setOrderDetailsServiceBaseUrl(String orderDetailsServiceBaseUrl) {
        this.orderDetailsServiceBaseUrl = orderDetailsServiceBaseUrl;
    }

    public void setGetOrderDetailsEndpoint(String getOrderDetailsEndpoint) {
        this.getOrderDetailsEndpoint = getOrderDetailsEndpoint;
    }


    @Override
    public Order getOrderDetails(String fsOrderId) {

        if(fsOrderId == null || fsOrderId.isEmpty()) {
            throw new IllegalArgumentException("FsOrderID provided to getOrderDetails() must not be null or empty");
        }

        if(orderDetailsServiceBaseUrl == null || orderDetailsServiceBaseUrl.isEmpty()) {
            throw new IllegalStateException("OrderDetailsServiceBaseUrl must not be null or empty. Please ensure this is set on the client object before invoking getOrderDetails()");
        }

        if(getOrderDetailsEndpoint == null || getOrderDetailsEndpoint.isEmpty()) {
            throw new IllegalStateException("GetOrderDetailsEndpoint must not be null or empty. Please ensure this is set on the client object before invoking getOrderDetails()");
        }

        String url = orderDetailsServiceBaseUrl + getOrderDetailsEndpoint + "/" + fsOrderId;

        log.info("Sending request to Order Details Service: GET " + url);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        log.info("Received response from Order Details Service: " + response.getStatusCode() + " - " + response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        FSOrder orderToMap = null;
        Order mappedOrder = null;
        try {
            orderToMap = mapper.readValue(response.getBody(), FSOrder.class);
            mappedOrder = orderMapper.mapOrder(orderToMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mappedOrder;
    }

}
