package ca.bestbuy.orders.fraud.utility

import spock.lang.Specification

class WebClientUtilityTest extends Specification {


    def "Test createRestTemplate(WebClientConfig) with null argument"() {

        when:

        WebClientUtility.createRestTemplate(null)

        then:

        thrown IllegalArgumentException
    }




}
