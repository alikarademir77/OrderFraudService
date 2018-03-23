package ca.bestbuy.orders.fraud.service.resourceapi

import ca.bestbuy.orders.fraud.model.client.resourceapi.data.ResourceApiItem
import ca.bestbuy.orders.fraud.model.client.resourceapi.data.ResourceApiRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

/**
 * Created by kundsing on 2018-03-23.
 */
@SpringBootTest
class ResourceApiCallerTest extends Specification{

    @Autowired
    ResourceApiCaller resourceApiCaller
    def "Test success for resource service call"() {
        given:

        List<String> sku = new ArrayList<>()
        sku.add("12345678")
        sku.add("23456789")
        JsonConverter jsonConverter = new JsonConverter()
        ResourceApiRequest request= jsonConverter.toResourceApiRequest(sku)
        when:
        String result=resourceApiCaller.callForData(request)

        then:

        result != null


    }
}
