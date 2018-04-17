package ca.bestbuy.orders.fraud.client

import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import spock.lang.Ignore
import spock.lang.Specification

// TODO - Fix this test
@Ignore
class ResourceApiClientImplTest extends Specification {

    def config = Mock(ResourceApiClientConfig)

    def "test happy path to make API call and return response json " () {
        given:

        ResourceApiRequest resourceApiRequest = new ResourceApiRequest()


        RestTemplate restTemplate = new RestTemplate()
        config.serviceUrl >> "/resource/api"
        config.endpoint >> "/endpoint"
        config.sslEnabled() >> false

        ResourceApiClientImpl resourceApiClient = new ResourceApiClientImpl(config)

        String response = new File("src/test/resources/resourceapi/resource-api-response-with-one-notfound.json").text

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(ExpectedCount.once(),
                MockRestRequestMatchers.requestTo("/resource/api/endpoint"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withSuccess(response, MediaType.APPLICATION_JSON));

        when:

        String jsonResponse =  resourceApiClient.getData(resourceApiRequest)

        ObjectMapper objectMapper = new ObjectMapper()


        then:

        jsonResponse != null
        objectMapper.readTree(jsonResponse)
                .path("catalog/products/10362263/details")
                .path("body").path("id").asText() == "10362263"
    }

    def "test to make API call and response 500 " () {
        given:

        ResourceApiRequest resourceApiRequest = new ResourceApiRequest()
        ResourceApiClientImpl resourceApiClient = new ResourceApiClientImpl()

        RestTemplate restTemplate = new RestTemplate()
        config.serviceUrl >> "/resource/api"
        config.endpoint >> "/endpoint"
        config.restTemplate() >> restTemplate
        resourceApiClient.config = config

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(ExpectedCount.once(),
                MockRestRequestMatchers.requestTo("/resource/api/endpoint"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withServerError());

        when:

        resourceApiClient.getData(resourceApiRequest)

        then:

        IllegalArgumentException ex = thrown()
        ex.message == "Resource API server error"
    }

}
