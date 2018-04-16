package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiRequest;

import ca.bestbuy.orders.fraud.utility.WebClientUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ResourceApiClientImpl {

    @Autowired
    ResourceApiClientConfig config;


    public ResourceApiClientImpl(ResourceApiClientConfig config){

        this.config = config;

    }
    public String getData(ResourceApiRequest request) {

        String resourceServiceUrl = config.getServiceUrl();
        String resourceServiceEndPoint = config.getEndpoint();

        if(StringUtils.isBlank(resourceServiceUrl) || StringUtils.isBlank(resourceServiceEndPoint)) {
            throw new IllegalStateException("The URL or endpoint for resource Service is null or empty. Please double check the following properties in the configuration - 'client.resource-api.connection.url' and 'client.resource-api.endpoint'");
        }

       RestTemplate restTemplate = WebClientUtility.createRestTemplate(config);

        String url = new StringBuilder()
            .append(resourceServiceUrl)
            .append(resourceServiceEndPoint).toString();

        String responseStr = null;
        try {
            responseStr = restTemplate.postForObject(url, request, String.class);
        } catch (RestClientException e) {
            throw new IllegalArgumentException("Resource API server error", e);
        }

        return responseStr;
    }
}
