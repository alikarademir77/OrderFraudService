package ca.bestbuy.orders.fraud.service.resourceapi;

import ca.bestbuy.orders.fraud.model.client.resourceapi.data.ResourceApiRequest;
import ca.bestbuy.orders.fraud.utilities.HttpsClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class ResourceApiCaller {

    @Autowired
    HttpsClientBuilder httpClinetBuider;
    public String callForData(ResourceApiRequest request) {

        RestTemplate restTemplate=httpClinetBuider.getRestTemplate();

        String responseStr = restTemplate.postForObject("https://di-bbyca-resourceservice-corp.ca.bestbuy.com/aggregator",request,String.class);

        return responseStr;
    }
}
