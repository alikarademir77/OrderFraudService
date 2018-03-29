package ca.bestbuy.orders.fraud.utility

import org.springframework.oxm.Marshaller
import org.springframework.oxm.Unmarshaller
import spock.lang.Specification

class WebClientUtilityTest extends Specification {


    def "Test createRestTemplate(WebClientConfig) with null argument"() {

        when:

        WebClientUtility.createRestTemplate(null)

        then:

        thrown IllegalArgumentException
    }


    def "Test createWebServiceTemplate() with null config"() {

        when:

        WebClientUtility.createWebServiceTemplate(null, Stub(Marshaller.class), Stub(Unmarshaller.class))

        then:

        thrown IllegalArgumentException
    }




}
