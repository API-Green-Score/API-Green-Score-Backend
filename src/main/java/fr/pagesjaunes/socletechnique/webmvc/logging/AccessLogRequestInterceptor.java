package fr.pagesjaunes.socletechnique.webmvc.logging;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pagesjaunes.socletechnique.lang.utils.CIStringUtils;
import fr.pagesjaunes.socletechnique.logging.LoggingConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Component
public class AccessLogRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger ACCESS_LOGGER = LoggerFactory.getLogger("accesslog");

    @Override
    public boolean preHandle(final HttpServletRequest request, @NonNull final HttpServletResponse response, @NonNull final Object handler) throws Exception {

        MDC.put(LoggingConstants.REQUEST_MILLIS, Long.toString(System.currentTimeMillis()));
        MDC.put(LoggingConstants.REQUEST_ID, request.getHeader("x-pj-request-id"));
        MDC.put(LoggingConstants.CONTEXT_ID, request.getHeader("x-pj-context-id"));
        MDC.put(LoggingConstants.REQUEST_URL, getRequestUrl(request));
        MDC.put(LoggingConstants.REQUEST_HEADERS, objectMapper.writeValueAsString(getRequestHeaders(request)));
        MDC.put(LoggingConstants.REQUEST_PROTOCOL, request.getProtocol());
        MDC.put(LoggingConstants.REQUEST_METHOD, request.getMethod());
        MDC.put(LoggingConstants.REQUEST_USERAGENT, request.getHeader("user-agent"));
        MDC.put(LoggingConstants.REQUEST_REFERER, request.getHeader("referer"));
        MDC.put(LoggingConstants.REQUEST_REMOTEIP, getRequestRemoteAddress(request));

        return true;
    }

    @Override
    public void afterCompletion(@NonNull final HttpServletRequest request, final HttpServletResponse response, @NonNull final Object handler, final Exception ex) throws Exception {

        int status = response.getStatus();
        MDC.put(LoggingConstants.RESPONSE_STATUS, Integer.toString(status));

        long begin = Long.parseLong(MDC.get(LoggingConstants.REQUEST_MILLIS));
        long latencyMillis = System.currentTimeMillis() - begin;
        MDC.put(LoggingConstants.RESPONSE_LATENCY, Long.toString(latencyMillis));
        MDC.put(LoggingConstants.RESPONSE_HEADERS, objectMapper.writeValueAsString(getResponseHeaders(response)));
        MDC.put(LoggingConstants.RESPONSE_SIZE, response.getHeader("Content-Length"));

        writeAccessLog(status);
    }

    /**
     * Méthode permettant de log en access-log
     *
     * @param status
     */
    protected void writeAccessLog(final int status) {

        if (isSuccess(status)) {
            ACCESS_LOGGER.info("");
        } else if (isWarn(status)) {
            ACCESS_LOGGER.warn("");
        } else if (isError(status)) {
            ACCESS_LOGGER.error("");
        }
    }

    // ========================================================================
    // PRIVATE METHODS
    // ========================================================================

    /**
     * Permet de savoir si le logger d'access-log est actif pour le code retour 'success'
     *
     * @param status Code retour de l'api.
     * @return true si le logger est actif, sinon false.
     */
    private static boolean isSuccess(final int status) {

        return (status >= 200 && status < 400) && ACCESS_LOGGER.isInfoEnabled();
    }

    /**
     * Permet de savoir si le logger d'access-log est actif pour le code retour '4xx'
     *
     * @param status Code retour de l'api.
     * @return true si le logger est actif, sinon false.
     */
    private static boolean isWarn(final int status) {

        return status >= 400 && status < 500 && ACCESS_LOGGER.isWarnEnabled();
    }

    /**
     * Permet de savoir si le logger d'access-log est actif pour le code retour '5xx'
     *
     * @param status Code retour de l'api.
     * @return true si le logger est actif, sinon false.
     */
    private static boolean isError(final int status) {

        return status >= 500 && ACCESS_LOGGER.isErrorEnabled();
    }

    private String getRequestRemoteAddress(final HttpServletRequest request) {

        String remoteAddress = request.getHeader("X-Forwarded-For");
        if (remoteAddress != null) {
            if (remoteAddress.contains(",")) {
                String[] remoteAddresses = CIStringUtils.split(remoteAddress, ',');
                if (remoteAddresses.length > 0) {
                    remoteAddress = remoteAddresses[0].trim();
                }
            }
        } else {
            remoteAddress = request.getRemoteAddr();
        }
        return remoteAddress;
    }

    private String getRequestUrl(final HttpServletRequest request) {

        StringBuilder url = new StringBuilder();

        url.append(request.getRequestURI());
        final String qs = request.getQueryString();
        if (qs != null) {
            url.append('?');
            url.append(qs);
        }

        return url.toString();
    }

    private Map<String, Set<String>> getRequestHeaders(final HttpServletRequest request) {
        return getHeadersInternal(Collections.list(request.getHeaderNames()), (String s) -> Collections.list(request.getHeaders(s)));
    }

    private Map<String, Set<String>> getResponseHeaders(final HttpServletResponse response) {
        return getHeadersInternal(response.getHeaderNames(), response::getHeaders);
    }

    private final ConcurrentHashMap<String, String> headerNamesToLowerCase = new ConcurrentHashMap<>();

    private Map<String, Set<String>> getHeadersInternal(Collection<String> headerNames, Function<String, Collection<String>> headerValuesFunc) {
        var headers = new HashMap<String, Set<String>>(headerNames.size());
        for (var headerName : headerNames) {
            headerName = headerNamesToLowerCase.computeIfAbsent(headerName, CIStringUtils::lowerCase);
            headers.put(headerName, new TreeSet<>(headerValuesFunc.apply(headerName)));
        }
        return headers;
    }

}
