package fr.pagesjaunes.socletechnique.webmvc.headers;

import fr.pagesjaunes.socletechnique.core.support.HeadersStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpResponseInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Collector of client http response headers.
 */
@Slf4j
public class AggregatedResponseHeadersCollector implements HttpResponseInterceptor {

    private final List<String> headersToCollect;

    @Autowired
    @Qualifier(HeadersStorage.RESPONSE_HEADERS_TO_AGGREGATE_STORAGE_BEAN_NAME)
    private HeadersStorage headersStorage;

    public AggregatedResponseHeadersCollector(@NonNull List<String> pHeadersToCollect) {
        this.headersToCollect = pHeadersToCollect;
    }

    public AggregatedResponseHeadersCollector(@NonNull List<String> pHeadersToCollect, HeadersStorage pHeadersStorage) {
        this(pHeadersToCollect);
        this.headersStorage = pHeadersStorage;
    }

    @Override
    public void process(HttpResponse response, EntityDetails entity, HttpContext context) throws HttpException, IOException {
        headersToCollect.forEach(n -> Arrays.stream(response.getHeaders(n)).forEach(headersStorage::add));
    }
}
