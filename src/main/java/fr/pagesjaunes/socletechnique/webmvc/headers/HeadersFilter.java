package fr.pagesjaunes.socletechnique.webmvc.headers;

import fr.pagesjaunes.socletechnique.core.support.HeadersStorage;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Headers filter:
 * - collects incoming request headers (collectors)
 * - generated outgoing response headers (providers)
 */
@Slf4j
public class HeadersFilter implements Filter {


    @Autowired
    private List<RequestHeaderCollector> requestHeaderCollectors = Collections.emptyList();

    @Autowired
    private List<HeadersStorage> headersStorages = Collections.emptyList();

    @Autowired
    private List<ResponseHeaderProvider> responseHeaderProviders = Collections.emptyList();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Initialization of request scope header storages
        headersStorages.forEach(HeadersStorage::clear);

        if (request instanceof HttpServletRequest httpRequest) {

            Enumeration<String> headerNames = httpRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                requestHeaderCollectors.stream().filter(c -> c.supports(headerName)).forEach(c -> c.handle(headerName, httpRequest.getHeaders(headerName)));
            }

            HttpServletResponse httpResponse = (HttpServletResponse) response;
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResponse);
            chain.doFilter(request, responseWrapper);

            responseHeaderProviders.forEach(p -> p.getHeaderNames().forEach(n -> p.getHeaderValues(n).forEach(v -> responseWrapper.addHeader(n, v))));

            responseWrapper.copyBodyToResponse();
        } else {
            chain.doFilter(request, response);
        }
    }

}
