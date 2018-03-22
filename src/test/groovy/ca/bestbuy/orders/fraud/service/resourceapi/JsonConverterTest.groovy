package ca.bestbuy.orders.fraud.service.resourceapi

import ca.bestbuy.orders.fraud.model.client.resourceapi.data.ResourceApiItem
import ca.bestbuy.orders.fraud.model.client.resourceapi.data.ResourceApiRequest
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

/**
 * Created by kundsing on 2018-03-22.
 */
class JsonConverterTest extends Specification{


    def "Test success for convert list of sku to resourceApiRequest"() {

        given:

        List<String> sku = new ArrayList<>()
        sku.add("12345678")
        sku.add("23456789")
        JsonConverter jsonConverter = new JsonConverter()
        when:
        ResourceApiRequest result= jsonConverter.toResourceApiRequest(sku)

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

        List<String> sku = new ArrayList<>()
        sku.add("12345678")
        sku.add("23456789")
        JsonConverter jsonConverter = new JsonConverter()
        when:
        ResourceApiRequest result= jsonConverter.toResourceApiRequest(sku)
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(result)
        then:

        jsonString != null
        jsonString == "{\"requests\":[{\"id\":\"catalog/products/12345678/details\",\"headers\":{\"Accept\":\"application/vnd.bestbuy.productdetails+json\"}},{\"id\":\"catalog/products/23456789/details\",\"headers\":{\"Accept\":\"application/vnd.bestbuy.productdetails+json\"}}]}"

    }
    def "Test exception if skulist is empty"() {

        given:

        List<String> sku = new ArrayList<>()
        JsonConverter jsonConverter = new JsonConverter()
        when:
        ResourceApiRequest result= jsonConverter.toResourceApiRequest(sku)

        then:

        IllegalArgumentException ex = thrown()
        ex.message ==  "skuList cann't be empty"
    }

}
