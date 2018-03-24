package ca.bestbuy.orders.fraud.service.resourceapi;

import ca.bestbuy.orders.fraud.model.client.resourceapi.data.ResourceApiRequest;
import ca.bestbuy.orders.fraud.utility.HttpClientUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

@Service
public class ResourceApiCaller {


    public String callForData(ResourceApiRequest request) {

        RestTemplate restTemplate=getRestTemplate();

        String responseStr = restTemplate.postForObject("https://di-bbyca-resourceservice-corp.ca.bestbuy.com/aggregator",request,String.class);

        return responseStr;
    }

    public RestTemplate getRestTemplate(){

        HttpClientUtility httpClientUtility = new HttpClientUtility();
        try {
            httpClientUtility.setKeystore(new UrlResource("file:/Users/kundsing/projects/order-fraud-service/src/main/resources/secure/oms-client.pfx"));
            httpClientUtility.setTruststore(new UrlResource("file:/Users/kundsing/projects/order-fraud-service/src/main/resources/secure/cacerts"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        httpClientUtility.setKeystorePassword("mccp123");
        httpClientUtility.setKeyPassword("mccp123");
        httpClientUtility.setTruststorePassword("changeit");
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClientUtility.createHttpClient(true,false)));
    }
}
