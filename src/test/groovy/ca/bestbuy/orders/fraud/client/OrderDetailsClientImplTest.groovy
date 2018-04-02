package ca.bestbuy.orders.fraud.client

import ca.bestbuy.orders.fraud.mappers.OrderMapper
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class OrderDetailsClientImplTest extends Specification {


    def "Test that exception is thrown if OrderMapper is null when constructing OrderDetailsClientImpl"() {

        when:

        new OrderDetailsClientImpl(null, createValidRestTemplate())

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("OrderMapper")

    }


    def "Test that exception is thrown if RestTemplate is null when constructing OrderDetailsClientImpl"() {

        when:

        new OrderDetailsClientImpl(createValidOrderMapper(), null)

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("RestTemplate")

    }



    def "Test that exception is thrown when calling getOrderDetails() with a null fsoId"() {

        setup:

        OrderDetailsClientImpl client = new OrderDetailsClientImpl(createValidOrderMapper(), createValidRestTemplate())

        when:

        client.getOrderDetails(null)

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("FsOrderID")

    }


    def "Test that exception is thrown when calling getOrderDetails() with an empty fsoId"() {

        setup:

        OrderDetailsClientImpl client = new OrderDetailsClientImpl(createValidOrderMapper(), createValidRestTemplate())

        when:

        client.getOrderDetails("")

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("FsOrderID")

    }


    def "Test that exception is thrown when calling getOrderDetails() when orderDetailsServiceBaseUrl is null"() {

        setup:

        OrderDetailsClientImpl client = new OrderDetailsClientImpl(createValidOrderMapper(), createValidRestTemplate())
        client.setGetOrderDetailsEndpoint("endpoint")
        client.setOrderDetailsServiceBaseUrl(null)

        when:

        client.getOrderDetails("1234")

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("OrderDetailsServiceBaseUrl")

    }


    def "Test that exception is thrown when calling getOrderDetails() when orderDetailsServiceBaseUrl is empty"() {

        setup:

        OrderDetailsClientImpl client = new OrderDetailsClientImpl(createValidOrderMapper(), createValidRestTemplate())
        client.setGetOrderDetailsEndpoint("endpoint")
        client.setOrderDetailsServiceBaseUrl("")

        when:

        client.getOrderDetails("1234")

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("OrderDetailsServiceBaseUrl")

    }


    def "Test that exception is thrown when calling getOrderDetails() when getOrderDetailsEndpoint is null"() {

        setup:

        OrderDetailsClientImpl client = new OrderDetailsClientImpl(createValidOrderMapper(), createValidRestTemplate())
        client.setGetOrderDetailsEndpoint(null)
        client.setOrderDetailsServiceBaseUrl("url")

        when:

        client.getOrderDetails("1234")

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("GetOrderDetailsEndpoint")

    }


    def "Test that exception is thrown when calling getOrderDetails() when getOrderDetailsEndpoint is is empty"() {

        setup:

        OrderDetailsClientImpl client = new OrderDetailsClientImpl(createValidOrderMapper(), createValidRestTemplate())
        client.setGetOrderDetailsEndpoint("")
        client.setOrderDetailsServiceBaseUrl("url")

        when:

        client.getOrderDetails("1234")

        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("GetOrderDetailsEndpoint")

    }



    private OrderMapper createValidOrderMapper() {
        return Stub(OrderMapper.class)
    }


    private static RestTemplate createValidRestTemplate() {
        RestTemplate restTemplate = new RestTemplate()
        return restTemplate
    }


}
