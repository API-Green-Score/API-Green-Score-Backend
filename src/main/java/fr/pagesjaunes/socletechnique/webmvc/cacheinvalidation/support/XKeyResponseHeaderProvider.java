package fr.pagesjaunes.socletechnique.webmvc.cacheinvalidation.support;

import fr.pagesjaunes.socletechnique.core.support.HeadersStorage;
import fr.pagesjaunes.socletechnique.webmvc.headers.ResponseHeaderProvider;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * Response header provider for cache management. Handles the xkey header.
 */
@Slf4j
@NoArgsConstructor
public class XKeyResponseHeaderProvider implements ResponseHeaderProvider {

    @Autowired
    @Qualifier(HeadersStorage.RESPONSE_HEADERS_TO_AGGREGATE_STORAGE_BEAN_NAME)
    private HeadersStorage aggregatedHeadersStorage;

    @Value("${confpj.socletechnique.xkey.max_size:-1}")
    private int xkeyMaxSize;

    public XKeyResponseHeaderProvider(HeadersStorage pAggregatedHeadersStorage) {
        this.aggregatedHeadersStorage = pAggregatedHeadersStorage;
    }

    @Override
    public List<String> getHeaderNames() {
        if (aggregatedHeadersStorage != null) {
            return aggregatedHeadersStorage.getHeaderValues(XKeyConstants.XKEY_HEADER_NAME).isEmpty() ? Collections.emptyList() : Collections.singletonList(XKeyConstants.XKEY_HEADER_NAME);
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getHeaderValues(String headerName) {
        if (aggregatedHeadersStorage != null && XKeyConstants.XKEY_HEADER_NAME.equals(headerName)) {
            List<String> headerValues = aggregatedHeadersStorage.getHeaderValues(headerName);
            if (!headerValues.isEmpty()) {
                // use a (tree)set for ordering and value deduplication
                Set<String> ts = new TreeSet<>();
                headerValues.forEach(v -> ts.addAll(Arrays.stream(v.split(" ")).toList()));
                String hv = String.join(" ", ts);
                if (xkeyMaxSize <= 0 || hv.length() <= xkeyMaxSize) {
                    return Collections.singletonList(hv);
                }
                LOGGER.warn("Header xkey has not been generated, size too long ({} chars, {} allowed)", hv.length(), xkeyMaxSize);
            }
        }
        return Collections.emptyList();
    }
}
