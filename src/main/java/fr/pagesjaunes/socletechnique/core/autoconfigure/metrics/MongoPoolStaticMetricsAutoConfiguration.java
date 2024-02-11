package fr.pagesjaunes.socletechnique.core.autoconfigure.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConditionalOnClass(MongoTemplate.class)
@ConditionalOnBean(MongoTemplate.class)
@AutoConfigureAfter({MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class MongoPoolStaticMetricsAutoConfiguration {

    @Bean
    public MongoClientSettingsBuilderCustomizer metricsCustomizer(MeterRegistry meterRegistry) {
        return clientSettingsBuilder -> clientSettingsBuilder
                .applyToConnectionPoolSettings(builder -> builder
                        .addConnectionPoolListener(new MongoPoolStaticMetricsConnectionPoolListener(meterRegistry)));
    }
}
