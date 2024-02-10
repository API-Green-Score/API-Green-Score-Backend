package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.caching;

import fr.pagesjaunes.socletechnique.webmvc.caching.support.CachingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "socletechnique.web.cachingFilter.enabled", havingValue = "true", matchIfMissing = true)
public class CachingAutoConfiguration {

    @Bean
    public CachingAspect cachingAspect() {
        return new CachingAspect();
    }

}
