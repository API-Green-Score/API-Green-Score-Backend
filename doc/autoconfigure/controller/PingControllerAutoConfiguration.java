package fr.apithinking.apigreenscore.coreweb.autoconfigure.controller;

import fr.apithinking.apigreenscore.coreweb.controller.PingController;
import fr.apithinking.apigreenscore.coreweb.controller.SwaggerAnnotatedPingController;
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
