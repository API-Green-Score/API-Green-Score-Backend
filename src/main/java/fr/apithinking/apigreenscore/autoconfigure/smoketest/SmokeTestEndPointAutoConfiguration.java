package fr.apithinking.apigreenscore.autoconfigure.smoketest;

import fr.apithinking.apigreenscore.core.smoketest.configure.SmokeTestEndPointConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(HealthEndpointAutoConfiguration.class)
@AutoConfigureAfter(HealthEndpointAutoConfiguration.class)
@Import({ SmokeTestEndPointConfiguration.class })
public class SmokeTestEndPointAutoConfiguration {
}
