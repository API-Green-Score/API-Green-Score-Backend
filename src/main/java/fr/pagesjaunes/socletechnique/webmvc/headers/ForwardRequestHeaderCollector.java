package fr.pagesjaunes.socletechnique.webmvc.headers;

import fr.pagesjaunes.socletechnique.core.support.HeadersStorage;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * Request headers collector that collect main headers that are to be forwarded
 * to client http requests further made by the application.
 */
@Slf4j
public class ForwardRequestHeaderCollector implements RequestHeaderCollector {

    /**
     * Default 'system' headers that are always forwarded
     */
    private static final String[] featureFlippingHeaders = {
            "X-PJ-FF-NOCACHE",
            "X-PJ-FF-FLAGS",
            "X-PJ-FF-PROFILE",
            "X-PJ-FF-MODE",
            "X-PJ-FF-SEND-ALL-FEATURES",
            "X-PJ-FF-USE-LOCAL-CACHE",
            "X-PJ-FF-CNTX",
            "X-PJ-FF-OVRDNFTR",
            "X-PJ-REQUEST-ID",
            "X-PJ-CONTEXT-ID"
    };

    /**
     * List of configured headers to be forwarded
     */
    @Value("${confpj.socletechnique.httpclient.forward_headers:#{null}}")
    private String forwardHeaders;

    @Autowired
    @Qualifier(HeadersStorage.REQUEST_HEADERS_TO_FORWARD_STORAGE_BEAN_NAME)
    private HeadersStorage requestHeadersToForwardStorage;

    private final Set<String> forwardHeadersSet = new HashSet<>();

    @PostConstruct
    public void init() {
        if (StringUtils.isNotBlank(forwardHeaders)) {
            String[] values = forwardHeaders.split(",");
            Arrays.stream(values).forEach(v -> forwardHeadersSet.add(v.toUpperCase(Locale.ROOT)));
        }
        forwardHeadersSet.addAll(Arrays.asList(featureFlippingHeaders));
    }

    @Override
    public boolean supports(String headerName) {
        return forwardHeadersSet.contains(headerName.toUpperCase(Locale.ROOT));
    }

    @Override
    public void handle(String headerName, String headerValue) {
        requestHeadersToForwardStorage.add(headerName, headerValue);
    }

    @Override
    public void handle(String headerName, Enumeration<String> headerValues) {
        requestHeadersToForwardStorage.add(headerName, headerValues);
    }

}
