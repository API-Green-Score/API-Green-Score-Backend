package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.cacheinvalidation;

import fr.pagesjaunes.socletechnique.webmvc.autoconfigure.headers.HeadersAutoConfiguration;
import fr.pagesjaunes.socletechnique.webmvc.cacheinvalidation.support.XKeyAspect;
import fr.pagesjaunes.socletechnique.webmvc.headers.HeadersFilter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(HeadersFilter.class)
@ConditionalOnProperty(value = "socletechnique.web.xkeyFilter.enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter(HeadersAutoConfiguration.class)
public class XKeyAutoConfiguration {

    @Bean
    public XKeyAspect xKeyAspect() throws NoSuchMethodException {
        return new XKeyAspect();
    }

}
