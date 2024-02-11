package fr.pagesjaunes.socletechnique.core.autoconfigure.headers;

import fr.pagesjaunes.socletechnique.core.support.HeadersStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@ConditionalOnProperty(value = "socletechnique.core.headersstorage.enabled", havingValue = "true", matchIfMissing = true)
public class HeadersAutoConfiguration {

    @ConditionalOnWebApplication
    @ConditionalOnMissingBean(name = HeadersStorage.REQUEST_HEADERS_TO_FORWARD_STORAGE_BEAN_NAME)
    @Configuration
    public static class WebRequestHeadersToForwardConfiguration {

        @Bean(HeadersStorage.REQUEST_HEADERS_TO_FORWARD_STORAGE_BEAN_NAME)
        public HeadersStorage requestHeadersToForwardWeb() {
            return new HeadersStorage.SmartWebProxiedHeadersStorage(internalRequestHeadersToForwardWeb(), internalRequestHeadersToForwardTls());
        }

        @RequestScope
        @Bean
        public HeadersStorage internalRequestHeadersToForwardWeb() {
            return new HeadersStorage.DefaultRequestHeadersStorage();
        }

        @Bean
        public HeadersStorage internalRequestHeadersToForwardTls() {
            return new HeadersStorage.ThreadLocalRequestHeadersStorage();
        }
    }

    @ConditionalOnWebApplication
    @ConditionalOnMissingBean(name = HeadersStorage.RESPONSE_HEADERS_TO_AGGREGATE_STORAGE_BEAN_NAME)
    @Configuration
    public static class WebResponseHeadersToForwardConfiguration {

        @Bean(HeadersStorage.RESPONSE_HEADERS_TO_AGGREGATE_STORAGE_BEAN_NAME)
        public HeadersStorage responseHeadersToAggregateWeb() {
            return new HeadersStorage.SmartWebProxiedHeadersStorage(internalResponseHeadersToAggregateWeb(), internalResponseHeadersToAggregateTls());
        }

        @RequestScope
        @Bean
        public HeadersStorage internalResponseHeadersToAggregateWeb() {
            return new HeadersStorage.DefaultRequestHeadersStorage();
        }

        @Bean
        public HeadersStorage internalResponseHeadersToAggregateTls() {
            return new HeadersStorage.ThreadLocalRequestHeadersStorage();
        }
    }


    @Bean(HeadersStorage.REQUEST_HEADERS_TO_FORWARD_STORAGE_BEAN_NAME)
    @ConditionalOnNotWebApplication
    public HeadersStorage requestHeadersToForward() {
        return new HeadersStorage.ThreadLocalRequestHeadersStorage();
    }

    @Bean(HeadersStorage.RESPONSE_HEADERS_TO_AGGREGATE_STORAGE_BEAN_NAME)
    @ConditionalOnNotWebApplication
    public HeadersStorage responseHeadersToAggregate() {
        return new HeadersStorage.ThreadLocalRequestHeadersStorage();
    }

}
