package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.controller;

import fr.pagesjaunes.socletechnique.webmvc.controller.RootController;
import fr.pagesjaunes.socletechnique.webmvc.controller.SwaggerAnnotatedRootController;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootControllerAutoConfiguration {

    @Configuration
    @ConditionalOnMissingBean(RootController.class)
    public static class RootControllerConfiguration {

        @Bean
        public RootController rootController() {
            return new RootController();
        }

    }

    @Configuration
    @ConditionalOnClass(Hidden.class)
    public static class SwaggerAnnotatedRootControllerConfiguration {

        @Bean
        public RootController rootController() {
            return new SwaggerAnnotatedRootController();
        }
    }
}
