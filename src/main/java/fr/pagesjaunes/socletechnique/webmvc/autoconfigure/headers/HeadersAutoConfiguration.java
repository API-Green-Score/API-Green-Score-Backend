package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.headers;

import fr.pagesjaunes.socletechnique.webmvc.cacheinvalidation.support.XKeyResponseHeaderProvider;
import fr.pagesjaunes.socletechnique.webmvc.headers.ForwardRequestHeaderCollector;
import fr.pagesjaunes.socletechnique.webmvc.headers.HeadersFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "socletechnique.web.headersFilter.enabled", havingValue = "true", matchIfMissing = true)
public class HeadersAutoConfiguration {

    @Bean
    public HeadersFilter headersFilter() {
        return new HeadersFilter();
    }

    @Bean
    public ForwardRequestHeaderCollector forwardRequestHeaderCollector() {
        return new ForwardRequestHeaderCollector();
    }

    @Bean
    public XKeyResponseHeaderProvider xKeyResponseHeaderProvider() {
        return new XKeyResponseHeaderProvider();
    }

}
