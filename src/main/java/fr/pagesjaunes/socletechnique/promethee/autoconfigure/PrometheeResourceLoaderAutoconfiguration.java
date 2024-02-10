package fr.pagesjaunes.socletechnique.promethee.autoconfigure;

import fr.pagesjaunes.socletechnique.promethee.PrometheeResourceLoadBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "socletechnique.config.promethee", name = "enabled", matchIfMissing = true)
public class PrometheeResourceLoaderAutoconfiguration {

    @Bean
    public PrometheeResourceLoadBeanPostProcessor prometheeResourceLoadBeanPostProcessor() {
        return new PrometheeResourceLoadBeanPostProcessor();
    }
}
