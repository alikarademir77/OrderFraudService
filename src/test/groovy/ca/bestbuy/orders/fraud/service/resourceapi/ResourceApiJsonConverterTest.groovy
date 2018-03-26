package ca.bestbuy.orders.fraud.service.resourceapi

import ca.bestbuy.orders.fraud.mappers.ResourceApiJsonConverter
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ProductDetail
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiItem
import ca.bestbuy.orders.fraud.model.client.resourcesapi.ResourceApiRequest
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class ResourceApiJsonConverterTest extends Specification{

    def "Test success for convert list of sku to resourceApiRequest"() {

        given:

        List<String> skus;
        skus = new ArrayList<>()
        skus.add("12345678")
        skus.add("23456789")
        ResourceApiJsonConverter jsonConverter = new ResourceApiJsonConverter()
        when:

        ResourceApiRequest result= jsonConverter.toResourceApiRequest(skus)

        then:

        result != null
        result.getResourceApiItemList().size() == 2
        List<ResourceApiItem> resourceApiItemList = result.getResourceApiItemList()


        resourceApiItemList.get(0).id == "catalog/products/12345678/details"
        resourceApiItemList.get(1).id == "catalog/products/23456789/details"
        resourceApiItemList.get(0).headers.accept == "application/vnd.bestbuy.productdetails+json"
        resourceApiItemList.get(1).headers.accept == "application/vnd.bestbuy.productdetails+json"

    }

    def "Test success for convert object to json"() {

        given:

        List<String> skus;
        skus = new ArrayList<>()
        skus.add("12345678")
        skus.add("23456789")

        ResourceApiJsonConverter jsonConverter = new ResourceApiJsonConverter()

        when:

        ResourceApiRequest result= jsonConverter.toResourceApiRequest(skus)
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(result)

        then:

        jsonString != null
        jsonString == "{\"requests\":[{\"id\":\"catalog/products/12345678/details\",\"headers\":{\"Accept\":\"application/vnd.bestbuy.productdetails+json\"}},{\"id\":\"catalog/products/23456789/details\",\"headers\":{\"Accept\":\"application/vnd.bestbuy.productdetails+json\"}}]}"

    }
    def "Test exception if skulist is empty"() {

        given:

        List<String> sku = new ArrayList<>()
        ResourceApiJsonConverter jsonConverter = new ResourceApiJsonConverter()

        when:

        ResourceApiRequest result= jsonConverter.toResourceApiRequest(sku)

        then:

        IllegalArgumentException ex = thrown()
        ex.message ==  "skuList cann't be empty"
    }
    def "Test product detail for success department class subclass"() {

        given:

        List<String> skus = new ArrayList<>()
        skus.add("10362263")

        String response = new File("src/test/resources/resourceapi/resource-api-response-with-one-notfound.json").text
        ResourceApiJsonConverter jsonConverter = new ResourceApiJsonConverter()

        when:

        Map<String, ProductDetail> result= jsonConverter.toProductDetail(skus,response)

        then:

        result.get("10362263").sku == "10362263"
        result.get("10362263").department == "49"
        result.get("10362263").itemClass == "1"
        result.get("10362263").itemSubClass == "8"
    }
    def "Test product detail department class subclass for sku not found"() {

        given:

        String response = new File("src/test/resources/resourceapi/resource-api-response-with-one-notfound.json").text
        ResourceApiJsonConverter jsonConverter = new ResourceApiJsonConverter()
        List<String> skus = new ArrayList<>()
        skus.add("10362264")

        when:

        Map<String, ProductDetail> result= jsonConverter.toProductDetail(skus,response)

        then:

        result.isEmpty() == true

    }
    def "Test exception for product detail with empty response"() {

        given:

        List<String> skus = new ArrayList<>()
        skus.add("10362264")
        String response = ""
        ResourceApiJsonConverter jsonConverter = new ResourceApiJsonConverter()

        when:

        Map<String, ProductDetail> result= jsonConverter.toProductDetail(skus,response)

        then:

        IllegalArgumentException ex = thrown()
        ex.message ==  "Response from resource service is empty"

    }
    def "Test exception for product detail with invalid json response"() {

        given:

        List<String> skus = new ArrayList<>()
        skus.add("10362264")
        String response = "abc"
        ResourceApiJsonConverter jsonConverter = new ResourceApiJsonConverter()

        when:

        Map<String, ProductDetail> result= jsonConverter.toProductDetail(skus,response)

        then:

        IllegalArgumentException ex = thrown()
        ex.message ==  "Response is not JSON format"

    }


}
