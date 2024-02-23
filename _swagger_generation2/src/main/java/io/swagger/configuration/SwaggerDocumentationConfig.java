package io.swagger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-23T21:35:35.651524679Z[GMT]")
@Configuration
public class SwaggerDocumentationConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
                .title("GreenScore API Specification for rules operations and score evaluations")
                .description("A API for GreenScore evaluation that allows some operations on : - rules definitions - score evaluation configuration - score evaluation process")
                .termsOfService("")
                .version("0.0.1-SNAPSHOT")
                .license(new License()
                    .name("GPL 3.0")
                    .url("https://github.com/API-Green-Score/APIGreenScore/blob/main/LICENCE.md"))
                .contact(new io.swagger.v3.oas.models.info.Contact()
                    .email("apiteam@swagger.io")));
    }

}
