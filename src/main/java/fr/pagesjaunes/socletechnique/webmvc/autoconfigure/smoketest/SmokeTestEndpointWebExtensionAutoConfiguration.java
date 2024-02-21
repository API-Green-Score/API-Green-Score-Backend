package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.smoketest;

import fr.apithinking.apigreenscore.core.autoconfigure.smoketest.SmokeTestEndPointAutoConfiguration;
import fr.apithinking.apigreenscore.core.smoketest.SmokeTestEndpoint;
import fr.pagesjaunes.socletechnique.webmvc.smoketest.SmokeTestEndpointWebExtension;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(SmokeTestEndPointAutoConfiguration.class)
@ConditionalOnWebApplication
public class SmokeTestEndpointWebExtensionAutoConfiguration {

    private SmokeTestEndpointWebExtensionAutoConfiguration() {
        // Private constructor
    }

    @Configuration
    @ConditionalOnWebApplication(type = Type.SERVLET)
    @ConditionalOnAvailableEndpoint(endpoint = SmokeTestEndpoint.class)
    static class ServletWebSmokeTestConfiguration {

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnBean(SmokeTestEndpoint.class)
        public SmokeTestEndpointWebExtension smokeTestEndpointWebExtension(final SmokeTestEndpoint smokeTestEndpoint) {
            return new SmokeTestEndpointWebExtension(smokeTestEndpoint);
        }
    }
}
