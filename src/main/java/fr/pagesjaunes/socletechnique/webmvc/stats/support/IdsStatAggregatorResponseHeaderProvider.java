package fr.pagesjaunes.socletechnique.webmvc.stats.support;

import fr.pagesjaunes.socletechnique.core.support.HeadersStorage;
import fr.pagesjaunes.socletechnique.lang.utils.CIStringUtils;
import fr.pagesjaunes.socletechnique.webmvc.headers.ResponseHeaderProvider;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
public class IdsStatAggregatorResponseHeaderProvider implements ResponseHeaderProvider {

    public static final String IDS_STAT_HEADER_NAME = "X-PJ-IDS-STAT";


    @Autowired
    @Qualifier(HeadersStorage.RESPONSE_HEADERS_TO_AGGREGATE_STORAGE_BEAN_NAME)
    private HeadersStorage aggregatedHeadersStorage;

    public IdsStatAggregatorResponseHeaderProvider(HeadersStorage pAggregatedHeadersStorage) {
        this.aggregatedHeadersStorage = pAggregatedHeadersStorage;
    }

    @Override
    public List<String> getHeaderNames() {
        if (aggregatedHeadersStorage != null) {
            return aggregatedHeadersStorage.getHeaderValues(IDS_STAT_HEADER_NAME).isEmpty() ? Collections.emptyList() : Collections.singletonList(IDS_STAT_HEADER_NAME);
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getHeaderValues(String headerName) {
        if (aggregatedHeadersStorage != null && IDS_STAT_HEADER_NAME.equals(headerName)) {
            return Collections.singletonList(CIStringUtils.join(aggregatedHeadersStorage.getHeaderValues(headerName), ','));
        }
        return Collections.emptyList();
    }
}
