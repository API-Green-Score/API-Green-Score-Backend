package fr.pagesjaunes.socletechnique.core.smoketest.configure;

import fr.pagesjaunes.socletechnique.core.smoketest.SmokeTestEndpoint;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = SmokeTestEndpoint.class)
public class SmokeTestEndPointConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SmokeTestEndpoint smokeTestEndPoint(HealthEndpoint healthEndPoint) {
        return new SmokeTestEndpoint(healthEndPoint);
    }
}