package fr.pagesjaunes.socletechnique.promethee.autoconfigure;

import fr.pagesjaunes.socletechnique.promethee.PrometheeConfigPropertySourceLocator;
import fr.pagesjaunes.socletechnique.promethee.smoketest.PrometheeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "socletechnique.config.promethee", name = "enabled", matchIfMissing = true)
public class PrometheeHealthConfiguration {

    @Bean
    public HealthContributor prometheeHealthIndicator(PrometheeConfigPropertySourceLocator prometheeConfigPropertySourceLocator) {
        return new PrometheeHealthContributor(prometheeConfigPropertySourceLocator);
    }
}
