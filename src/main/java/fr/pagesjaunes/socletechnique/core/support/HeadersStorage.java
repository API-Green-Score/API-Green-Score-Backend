package fr.pagesjaunes.socletechnique.core.support;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Storage for request/response header values.
 */
public interface HeadersStorage {

    String REQUEST_HEADERS_TO_FORWARD_STORAGE_BEAN_NAME = "socletechnique.core.headers.requestHeadersToForward";

    String RESPONSE_HEADERS_TO_AGGREGATE_STORAGE_BEAN_NAME = "socletechnique.core.headers.responseHeadersToAggregate";

    void add(Header header);

    void add(String name, String value);

    void add(String name, Enumeration<String> values);

    void set(Header header);

    void set(String name, String value);

    void set(String name, Enumeration<String> values);

    List<Header> getHeaders();

    List<String> getHeaderValues(String name);

    void clear();

    /**
     * Default implementation
     */
    @Slf4j
    class DefaultRequestHeadersStorage implements HeadersStorage {

        private final Map<String, Set<String>> internal = new ConcurrentHashMap<>();

        @Override
        public void add(Header header) {
            add(header.getName(), header.getValue());
        }

        @Override
        public void add(String name, String value) {
            Set<String> values = internal.computeIfAbsent(name, k -> new ConcurrentSkipListSet<>());
            values.add(value);
        }

        @Override
        public void add(String name, Enumeration<String> values) {
            Collections.list(values).forEach(v -> add(name, v));
        }

        @Override
        public List<Header> getHeaders() {
            List<Header> headers = new ArrayList<>();
            internal.forEach((key, value) -> value.forEach(v -> headers.add(new BasicHeader(key, v))));
            return headers;
        }

        @Override
        public List<String> getHeaderValues(String name) {
            List<String> values = new ArrayList<>();
            internal.forEach((h, v) -> {
                if (StringUtils.equalsIgnoreCase(name, h)) {
                    values.addAll(v);
                }
            });
            return values;
        }

        @Override
        public void clear() {
            internal.clear();
        }

        @Override
        public void set(Header header) {
            set(header.getName(), header.getValue());
        }

        @Override
        public void set(String name, String value) {
            internal.put(name, new ConcurrentSkipListSet<>(Collections.singletonList(value)));
        }

        @Override
        public void set(String name, Enumeration<String> values) {
            internal.put(name, new ConcurrentSkipListSet<>(Collections.list(values)));
        }
    }

    abstract class AbstractTargetHeadersStorage implements HeadersStorage {

        @Override
        public void add(Header header) {
            getTarget().add(header);
        }

        @Override
        public void add(String name, String value) {
            getTarget().add(name, value);
        }

        @Override
        public void add(String name, Enumeration<String> values) {
            getTarget().add(name, values);
        }

        @Override
        public void set(Header header) {
            getTarget().set(header);
        }

        @Override
        public void set(String name, String value) {
            getTarget().set(name, value);
        }

        @Override
        public void set(String name, Enumeration<String> values) {
            getTarget().set(name, values);
        }

        @Override
        public List<Header> getHeaders() {
            return getTarget().getHeaders();
        }

        @Override
        public List<String> getHeaderValues(String name) {
            return getTarget().getHeaderValues(name);
        }

        @Override
        public void clear() {
            getTarget().clear();
        }

        protected abstract HeadersStorage getTarget();
    }

    /**
     * Thread-aware implementation.
     */
    class ThreadLocalRequestHeadersStorage extends AbstractTargetHeadersStorage implements HeadersStorage {

        private static final ThreadLocal<HeadersStorage> storage = ThreadLocal.withInitial(DefaultRequestHeadersStorage::new);

        @PreDestroy
        public void destroy() {
            storage.remove();
        }

        @Override
        protected HeadersStorage getTarget() {
            return storage.get();
        }
    }

    /**
     * Proxied implementation
     */
    @AllArgsConstructor
    class SmartWebProxiedHeadersStorage extends AbstractTargetHeadersStorage implements HeadersStorage {

        private HeadersStorage webHeadersStorage;

        private HeadersStorage tlsHeadersStorage;

        @Override
        protected HeadersStorage getTarget() {
            if (RequestContextHolder.getRequestAttributes() != null) {
                return webHeadersStorage;
            }
            return tlsHeadersStorage;
        }
    }
}
