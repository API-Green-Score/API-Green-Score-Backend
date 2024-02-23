package fr.pagesjaunes.socletechnique.promethee.autoconfigure;

import fr.pagesjaunes.socletechnique.promethee.PrometheeConfigProperties;
import fr.pagesjaunes.socletechnique.promethee.PrometheeConfigPropertySourceLocator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PrometheeConfigProperties.class)
public class PrometheeConfigBootstrapConfiguration {

    @Bean
    @ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public PrometheeConfigPropertySourceLocator prometheeConfigPropertySourceLocator(
            final PrometheeConfigProperties configProperties,
            final RestTemplateBuilder restTemplateBuilder) {
        return new PrometheeConfigPropertySourceLocator(configProperties, restTemplateBuilder);
    }

}
