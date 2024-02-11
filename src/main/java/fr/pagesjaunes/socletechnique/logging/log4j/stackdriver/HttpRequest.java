package fr.pagesjaunes.socletechnique.logging.log4j.stackdriver;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@JsonPropertyOrder({ "protocol", "requestMethod", "requestUrl", "status", "latency" })
public class HttpRequest {

    private String protocol;

    private String requestMethod;

    private String requestUrl;

    private String userAgent;

    private String remoteIp;

    private String referer;

    private Map<String, Object> requestHeaders;

    private Integer status;

    private String latency;

    private String responseSize;

    private Map<String, Object> responseHeaders;
}