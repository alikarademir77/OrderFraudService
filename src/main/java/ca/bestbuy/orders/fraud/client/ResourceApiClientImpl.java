package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResourceApiClientImpl {

    @Autowired
    ResourceApiClientConfig config;

    public String getData(ResourceApiRequest request) {

        RestTemplate restTemplate = config.restTemplate();

        String resourceServiceUrl = config.getServiceUrl();
        String resourceServiceEndPoint = config.getEndpoint();

        if(StringUtils.isBlank(resourceServiceUrl) || StringUtils.isBlank(resourceServiceEndPoint)) {
            throw new IllegalStateException("The URL or endpoint for resource Service is null or empty. Please double check the following properties in the configuration - 'client.resource-service.connection.url' and 'client.resource-service.endpoint'");
        }

        String url = new StringBuilder()
            .append(resourceServiceUrl)
            .append(resourceServiceEndPoint).toString();

        String responseStr = restTemplate.postForObject(url, request, String.class);

        return responseStr;
    }
}
