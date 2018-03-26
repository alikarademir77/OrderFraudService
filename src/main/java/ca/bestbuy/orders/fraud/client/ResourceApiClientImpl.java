package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiRequest;
import ca.bestbuy.orders.fraud.utility.HttpClientUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResourceApiClientImpl {

    @Autowired
    ResourceApiClientConfig config;

    public String getData(ResourceApiRequest request) {

        RestTemplate restTemplate=getRestTemplate();

        String resourceServiceUrl = config.getServiceUrl();
        String resourceServiceEndPoint = config.getEndpoint();

        if(resourceServiceUrl == null || resourceServiceUrl.isEmpty() || resourceServiceEndPoint == null || resourceServiceEndPoint.isEmpty()) {
            throw new IllegalStateException("The URL or endpoint for resource Service is null or empty. Please double check the following properties in the configuration - 'client.resource-service.connection.url' and 'client.resource-service.endpoint'");
        }

        String url = new StringBuilder()
            .append(resourceServiceUrl)
            .append(resourceServiceEndPoint).toString();

        String responseStr = restTemplate.postForObject(url, request, String.class);

        return responseStr;
    }

    public RestTemplate getRestTemplate(){

        HttpClientUtility httpClientUtility = new HttpClientUtility();

        httpClientUtility.setKeystore(config.getKeystorePath());
        httpClientUtility.setTruststore(config.getTruststorePath());

        httpClientUtility.setKeystorePassword(config.getKeystorePassword());
        httpClientUtility.setKeyPassword(config.getKeyPassword());
        httpClientUtility.setTruststorePassword(config.getTruststorePassword());

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClientUtility.createHttpClient(config.getTlsEnabled(),false)));
    }
}
