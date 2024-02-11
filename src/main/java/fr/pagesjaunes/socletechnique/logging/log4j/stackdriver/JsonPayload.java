package fr.pagesjaunes.socletechnique.logging.log4j.stackdriver;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fr.pagesjaunes.socletechnique.logging.LoggingConstants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;

import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.spi.StandardLevel.*;

@Getter
@Setter
@Accessors(chain = true)
@JsonPropertyOrder({ "timestamp", "type", "severity", "message" })
public class JsonPayload {

    private Timestamp timestamp;

    private Severity severity;

    private String message;

    private String thread;

    private String loggerName;

    @JsonProperty("type")
    private String type;

    private String applicationName;

    private String applicationVersion;

    private String contextId;

    private String requestId;

    @Getter(AccessLevel.NONE)
    private final Map<String, Object> additionalFields = new HashMap<>();

    @Setter(AccessLevel.NONE)
    private HttpRequest httpRequest;

    private HttpRequest httpRequestContext;

    public JsonPayload() {
    	
    }
    
    public JsonPayload(final LogEvent event) {

        this.timestamp = getTimestamp(event);
        this.thread = event.getThreadName();
        this.severity = severityFor(event.getLevel());
        this.message = getMessageAndThrowable(event);
        this.loggerName = event.getLoggerName();
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalFields() {
        return additionalFields;
    }

    @JsonAnySetter
    public JsonPayload addAdditionalField(final String key, final Object value) {
        if (value != null) {
            this.additionalFields.put(key, value);
        }
        return this;
    }

    /**
     * On ajoute l'objet {@link HttpRequest} que s'il on a au moins la méthod et l'url.
     * @param httpRequest
     */
    public JsonPayload setHttpRequest(final HttpRequest httpRequest) {
        if (httpRequest.getRequestMethod() != null
                && httpRequest.getRequestUrl() != null) {
            this.httpRequest = httpRequest;
        }
        return this;
    }

    private String getMessageAndThrowable(final LogEvent event) {

        StringBuilder sb = new StringBuilder();
        sb.append(event.getMessage().getFormattedMessage());

        if (event.getThrown() != null) {
        	        	
            String throwableAsString = ExceptionUtils.getStackTrace(event.getThrown());
            sb.append("\r\n").append(throwableAsString);
        }

        return sb.toString();
    }

    private static Severity severityFor(final Level level) {
        if (level.intLevel() == DEBUG.intLevel()) {
            return Severity.DEBUG;
        } else if (level.intLevel() == INFO.intLevel()) {
            return Severity.INFO;
        } else if (level.intLevel() == WARN.intLevel()) {
            return Severity.WARNING;
        } else if (level.intLevel() == ERROR.intLevel()) {
            return Severity.ERROR;
        } else {
            return Severity.DEFAULT;
        }
    }

    private static Timestamp getTimestamp(final LogEvent event) {

        long millis = event.getTimeMillis();
        if (event.getContextData().containsKey(LoggingConstants.REQUEST_MILLIS)) {
            millis = Long.parseLong(event.getContextData().getValue(LoggingConstants.REQUEST_MILLIS));
        }

        long seconds = millis / 1000;
        long nanos = (millis % 1000) * 1000000;
        return new Timestamp()
                .setSeconds(seconds)
                .setNanos(nanos);
    }
}
