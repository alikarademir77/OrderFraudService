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

    @Autowired
    ResourceServiceClientConfig config;
    public String callForData(ResourceApiRequest request) {

        RestTemplate restTemplate=getRestTemplate();

        String resourceServiceUrl = config.getServiceUrl();
        String resourceServiceEndPoint = config.getEndpoint();

        if(resourceServiceUrl == null || resourceServiceUrl.isEmpty() || resourceServiceEndPoint == null || resourceServiceEndPoint.isEmpty()) {
            throw new IllegalStateException("The URL or endpoint for resource Service is null or empty. Please double check the following properties in the configuration - 'client.resource-service.connection.url' and 'client.resource-service.endpoint'");
        }

        String url = resourceServiceUrl + resourceServiceEndPoint;

        String responseStr = restTemplate.postForObject(url,request,String.class);

        return responseStr;
    }

    public RestTemplate getRestTemplate(){

        HttpClientUtility httpClientUtility = new HttpClientUtility();
        try {
            httpClientUtility.setKeystore(new UrlResource(config.getKeystorePath()));
            httpClientUtility.setTruststore(new UrlResource(config.getTruststorePath()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        httpClientUtility.setKeystorePassword(config.getKeystorePassword());
        httpClientUtility.setKeyPassword(config.getKeyPassword());
        httpClientUtility.setTruststorePassword(config.getTruststorePassword());
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClientUtility.createHttpClient(config.getTlsEnabled(),false)));
    }
}
