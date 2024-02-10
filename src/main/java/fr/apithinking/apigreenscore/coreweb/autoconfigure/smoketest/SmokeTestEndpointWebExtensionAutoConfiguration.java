package fr.apithinking.apigreenscore.coreweb.autoconfigure.smoketest;

import fr.apithinking.apigreenscore.core.autoconfigure.smoketest.SmokeTestEndPointAutoConfiguration;
import fr.apithinking.apigreenscore.core.smoketest.SmokeTestEndpoint;
import fr.apithinking.apigreenscore.coreweb.smoketest.SmokeTestEndpointWebExtension;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SmokeTestEndpointWebExtensionAutoConfiguration {

    private SmokeTestEndpointWebExtensionAutoConfiguration() {
        LOGGER.info("AUTO-CONFIG initialization");
    }

    @Configuration
    @ConditionalOnWebApplication(type = Type.SERVLET)
    @ConditionalOnAvailableEndpoint(endpoint = SmokeTestEndpoint.class)
    @Slf4j
    static class ServletWebSmokeTestConfiguration {

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnBean(SmokeTestEndpoint.class)
        public SmokeTestEndpointWebExtension smokeTestEndpointWebExtension(final SmokeTestEndpoint smokeTestEndpoint) {
            LOGGER.info("AUTO-CONFIG initialization");
            return new SmokeTestEndpointWebExtension(smokeTestEndpoint);
        }
    }
}
