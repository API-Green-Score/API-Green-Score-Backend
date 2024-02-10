package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.controller;

import fr.pagesjaunes.socletechnique.webmvc.controller.PingController;
import fr.pagesjaunes.socletechnique.webmvc.controller.SwaggerAnnotatedPingController;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PingControllerAutoConfiguration {

    @Configuration
    @ConditionalOnMissingBean(PingController.class)
    public static class PingControllerConfiguration {

        @Bean
        public PingController pingController() {
            return new PingController();
        }

    }

    @Configuration
    @ConditionalOnClass(Hidden.class)
    public static class SwaggerAnnotatedPingControllerConfiguration {

        @Bean
        public PingController pingController() {
            return new SwaggerAnnotatedPingController();
        }

    }

}
