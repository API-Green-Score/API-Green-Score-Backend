package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.headers;

import fr.pagesjaunes.socletechnique.webmvc.cacheinvalidation.support.XKeyConstants;
import fr.pagesjaunes.socletechnique.webmvc.cacheinvalidation.support.XKeyResponseHeaderProvider;
import fr.pagesjaunes.socletechnique.webmvc.featureflipping.support.FFFlagsResponseHeaderProvider;
import fr.pagesjaunes.socletechnique.webmvc.headers.AggregatedResponseHeadersCollector;
import fr.pagesjaunes.socletechnique.webmvc.headers.ForwardRequestHeaderCollector;
import fr.pagesjaunes.socletechnique.webmvc.headers.HeadersFilter;
import fr.pagesjaunes.socletechnique.webmvc.stats.support.IdsStatAggregatorResponseHeaderProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

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

    @Bean
    public FFFlagsResponseHeaderProvider ffFlagsResponseHeaderProvider() {
        return new FFFlagsResponseHeaderProvider();
    }

    @Bean
    public IdsStatAggregatorResponseHeaderProvider idsStatAggregatorResponseHeaderProvider() {
        return new IdsStatAggregatorResponseHeaderProvider();
    }

    @Bean
    public AggregatedResponseHeadersCollector aggregatedResponseHeadersCollector() {
        return new AggregatedResponseHeadersCollector(Arrays.asList(XKeyConstants.XKEY_HEADER_NAME, FFFlagsResponseHeaderProvider.FF_FLAGS_HEADER_NAME, IdsStatAggregatorResponseHeaderProvider.IDS_STAT_HEADER_NAME));
    }

}
