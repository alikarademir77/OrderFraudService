package ca.bestbuy.orders.fraud.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Created by kundsing on 2018-04-05.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket orderFrandApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ca.bestbuy.orders.fraud.controller"))
                .paths(PathSelectors.regex("/fraudchecktransactions.*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        @SuppressWarnings("rawtypes")
        ApiInfo apiInfo = new ApiInfo(
                "Fraud  service REST API",
                "REST API for retrive and update fraud request",
                "1",
                null,
                new Contact("EComm Orders Team", "https://confluence.ca.bestbuy.com/display/OR/Orders", "eCommCAOrders@bestbuy.com"),
                null,
                null,
                new ArrayList<VendorExtension>());
        return apiInfo;
    }
}
