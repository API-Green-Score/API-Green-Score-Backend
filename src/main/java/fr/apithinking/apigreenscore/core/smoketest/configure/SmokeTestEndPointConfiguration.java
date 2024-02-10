package fr.apithinking.apigreenscore.core.smoketest.configure;

import fr.apithinking.apigreenscore.core.smoketest.SmokeTestEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = SmokeTestEndpoint.class)
@Slf4j
public class SmokeTestEndPointConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SmokeTestEndpoint smokeTestEndPoint(HealthEndpoint healthEndPoint) {
        LOGGER.info("AUTO-CONFIG initialization (import)");
        return new SmokeTestEndpoint(healthEndPoint);
    }
}