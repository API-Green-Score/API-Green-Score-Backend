package fr.pagesjaunes.socletechnique.webmvc.featureflipping.support;

import fr.pagesjaunes.socletechnique.core.support.HeadersStorage;
import fr.pagesjaunes.socletechnique.lang.utils.CIStringUtils;
import fr.pagesjaunes.socletechnique.webmvc.headers.ResponseHeaderProvider;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Response header provider for feature flipping. Handles the X-PJ-FF-FLAGS header.
 */
@Slf4j
@NoArgsConstructor
public class FFFlagsResponseHeaderProvider implements ResponseHeaderProvider {

    public static final String FF_FLAGS_HEADER_NAME = "X-PJ-FF-FLAGS";

    @Autowired
    @Qualifier(HeadersStorage.RESPONSE_HEADERS_TO_AGGREGATE_STORAGE_BEAN_NAME)
    private HeadersStorage aggregatedHeadersStorage;

    public FFFlagsResponseHeaderProvider(HeadersStorage pAggregatedHeadersStorage) {
        this.aggregatedHeadersStorage = pAggregatedHeadersStorage;
    }

    @Override
    public List<String> getHeaderNames() {
        if (aggregatedHeadersStorage != null) {
            return aggregatedHeadersStorage.getHeaderValues(FF_FLAGS_HEADER_NAME).isEmpty() ? Collections.emptyList() : Collections.singletonList(FF_FLAGS_HEADER_NAME);
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getHeaderValues(String headerName) {
        if (aggregatedHeadersStorage != null && FF_FLAGS_HEADER_NAME.equals(headerName)) {
            List<String> headerValues = aggregatedHeadersStorage.getHeaderValues(headerName);
            if (!headerValues.isEmpty()) {
                List<String> keys = new ArrayList<>();
                headerValues.forEach(hv -> keys.addAll(Arrays.stream(CIStringUtils.split(hv, '|')).toList()));
                return Collections.singletonList(keys.stream().distinct().sorted().collect(Collectors.joining("|")));
            }
        }
        return Collections.emptyList();
    }
}
