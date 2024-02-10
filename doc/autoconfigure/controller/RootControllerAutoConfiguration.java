package fr.apithinking.apigreenscore.coreweb.autoconfigure.controller;

import fr.apithinking.apigreenscore.coreweb.controller.RootController;
import fr.apithinking.apigreenscore.coreweb.controller.SwaggerAnnotatedRootController;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "web.rootcontroller.enabled", havingValue = "true", matchIfMissing = true)
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
