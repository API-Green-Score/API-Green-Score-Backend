package fr.pagesjaunes.socletechnique.promethee.autoconfigure;

import fr.pagesjaunes.socletechnique.promethee.PrometheeConfigProperties;
import fr.pagesjaunes.socletechnique.promethee.PrometheeConfigPropertySourceLocator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ConditionalOnClass(PropertySourceLocator.class)
@EnableConfigurationProperties(PrometheeConfigProperties.class)
public class PrometheeConfigBootstrapConfiguration {

    @Bean
    @ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
    public RestTemplateBuilder restTemplateBuilder() {

        return new RestTemplateBuilder();
    }

    @Bean
    @ConditionalOnProperty(prefix = "socletechnique.config.promethee", name = "enabled", matchIfMissing = true)
    public PrometheeConfigPropertySourceLocator prometheeConfigPropertySourceLocator(
            final PrometheeConfigProperties configProperties,
            final RestTemplateBuilder restTemplateBuilder) {

        // Instanciate and initialize a private task scheduler
        ThreadPoolTaskScheduler taskScheduler = new TaskSchedulerBuilder().threadNamePrefix("prometheeReloadScheduler").poolSize(2).build();
        taskScheduler.initialize();

        return new PrometheeConfigPropertySourceLocator(configProperties, restTemplateBuilder, taskScheduler);
    }

}
