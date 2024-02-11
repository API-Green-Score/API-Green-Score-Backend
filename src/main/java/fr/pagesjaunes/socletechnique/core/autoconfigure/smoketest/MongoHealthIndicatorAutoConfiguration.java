package fr.pagesjaunes.socletechnique.core.autoconfigure.smoketest;

import fr.pagesjaunes.socletechnique.core.smoketest.mongo.MongoHealthIndicator;
import fr.pagesjaunes.socletechnique.lang.utils.CIStringUtils;
import org.springframework.boot.actuate.autoconfigure.data.mongo.MongoHealthContributorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.data.mongo.MongoReactiveHealthContributorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Map;

@Configuration
@ConditionalOnClass(MongoTemplate.class)
@ConditionalOnBean(MongoTemplate.class)
@ConditionalOnEnabledHealthIndicator("mongo")
@AutoConfigureBefore(MongoHealthContributorAutoConfiguration.class)
@AutoConfigureAfter({MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
        MongoReactiveHealthContributorAutoConfiguration.class})
public class MongoHealthIndicatorAutoConfiguration
        extends CompositeHealthContributorConfiguration<MongoHealthIndicator, MongoTemplate> {

    @Bean
    public HealthContributor mongoHealthContributor(Map<String, MongoTemplate> mongoTemplates, MongoProperties mongoProperties) {
        HealthContributor hc = createContributor(mongoTemplates);

        // When only 1 MongoTemplate was found, we suppose that the uri (if any) found in MongoProperties is the Mongo DB URI for
        // this template
        // Otherwise (more than 1 MongoTemplate), we can not determine the DBs URIs
        String uri = mongoProperties.determineUri();
        if (mongoTemplates.size() == 1 && CIStringUtils.isNotBlank(uri) && MongoHealthIndicator.class.isAssignableFrom(hc.getClass())) {
            ((MongoHealthIndicator) hc).setUri(uri);
        }

        return hc;
    }
}
