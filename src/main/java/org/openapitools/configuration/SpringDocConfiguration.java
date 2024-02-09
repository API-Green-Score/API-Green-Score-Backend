package org.openapitools.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("GreenScore API Specification for rules operations and score evaluations")
                                .description("A API for GreenScore evaluation that allows some operations on : - rules definitions - score evaluation configuration - score evaluation process")
                                .termsOfService("https://github.com/API-Green-Score/APIGreenScore")
                                .contact(
                                        new Contact()
                                                .email("apiteam@swagger.io")
                                )
                                .license(
                                        new License()
                                                .name("GPL 3.0")
                                                .url("https://github.com/API-Green-Score/APIGreenScore/blob/main/LICENCE.md")
                                )
                                .version("0.0.1-SNAPSHOT")
                )
        ;
    }
}