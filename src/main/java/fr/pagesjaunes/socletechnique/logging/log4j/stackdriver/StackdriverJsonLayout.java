package fr.pagesjaunes.socletechnique.logging.log4j.stackdriver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.pagesjaunes.socletechnique.lang.utils.CIStringUtils;
import fr.pagesjaunes.socletechnique.logging.LoggingConstants;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.core.util.StringBuilderWriter;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Plugin(name = "StackdriverJsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public final class StackdriverJsonLayout extends AbstractStringLayout {

    // ========================================================================
    // CONSTANT
    // ========================================================================

    /**
     * Allow subclasses access to the status logger without creating another instance.
     */
    private static final Logger LOGGER = StatusLogger.getLogger();

    private static final String EOL = "\r\n";

    private static final String CONTENT_TYPE = "application/json";

    private static final String ACCESSLOG_TYPE = "accesslog";

    // ========================================================================
    // BUILDER
    // ========================================================================

    @Accessors(chain = true)
    public static class StackdriverJsonLayoutBuilder<B extends StackdriverJsonLayoutBuilder<B>> extends Builder<B>
            implements org.apache.logging.log4j.core.util.Builder<StackdriverJsonLayout> {

        @PluginBuilderAttribute
        @Setter
        private String type = "trace";

        @PluginBuilderAttribute
        @Setter
        private boolean debug = false;

        @PluginBuilderAttribute
        @Setter
        public boolean ignore404 = true;

        @PluginElement("AdditionalField")
        @Setter
        private KeyValuePair[] additionalFields;

        public StackdriverJsonLayoutBuilder() {
            super();
            setCharset(StandardCharsets.UTF_8);
        }

        @Override
        public StackdriverJsonLayout build() {
            return new StackdriverJsonLayout(getConfiguration(), type, additionalFields, debug, ignore404);
        }
    }

    @PluginBuilderFactory
    public static <B extends StackdriverJsonLayoutBuilder<B>> B newBuilder() {
        return new StackdriverJsonLayoutBuilder<B>().asBuilder();
    }

    // ========================================================================
    // ATTRIBUTES
    // ========================================================================

    private final ObjectMapper objectMapper;

    private final ObjectWriter objectWriter;

    private final String type;

    private final boolean ignore404;

    private final List<KeyValuePair> additionalFields;

    private String applicationName;

    private String applicationVersion;

    // ========================================================================
    // CONSTRUCTEUR
    // ========================================================================

    private StackdriverJsonLayout(final Configuration config, final String type, final KeyValuePair[] additionalFields, final boolean debug, final boolean ignore404) {

        super(config, StandardCharsets.UTF_8, null, null);
        this.objectMapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        this.objectWriter = objectMapper
                .writer(debug ? new DefaultPrettyPrinter() : new MinimalPrettyPrinter());
        this.type = type;
        this.ignore404 = ignore404;
        this.additionalFields = additionalFields != null ? Arrays.asList(additionalFields) : null;
    }

    // ========================================================================
    // PUBLIC METHODS
    // ========================================================================

    @Override
    public String toSerializable(final LogEvent event) {
        final StringBuilderWriter writer = new StringBuilderWriter();
        try {
            toSerializable(event, writer);
            return writer.toString();
        } catch (final IOException e) {
            // Should this be an ISE or IAE?
            LOGGER.error(e);
            return Strings.EMPTY;
        }
    }

    @Override
    public String getContentType() {
        return CONTENT_TYPE + "; charset=" + this.getCharset();
    }

    // ========================================================================
    // PRIVATE METHODS
    // ========================================================================

    private void toSerializable(final LogEvent event, final Writer writer) throws IOException {

        objectWriter.writeValue(writer, wrapLogEvent(event));
        writer.write(EOL);
    }

    private Object wrapLogEvent(final LogEvent event) {

        Integer status = getContextPropertyAsInt(event, LoggingConstants.RESPONSE_STATUS);

        JsonPayload jsonPayLoad = new JsonPayload(event)
                .setType(type)
                .setApplicationName(applicationName)
                .setApplicationVersion(applicationVersion)
                .setContextId(getContextProperty(event, LoggingConstants.CONTEXT_ID))
                .setRequestId(getContextProperty(event, LoggingConstants.REQUEST_ID));
        var httpRequest = new HttpRequest()
                .setProtocol(getContextProperty(event, LoggingConstants.REQUEST_PROTOCOL))
                .setRequestMethod(getContextProperty(event, LoggingConstants.REQUEST_METHOD))
                .setRequestUrl(getContextProperty(event, LoggingConstants.REQUEST_URL))
                .setUserAgent(getContextProperty(event, LoggingConstants.REQUEST_USERAGENT))
                .setRemoteIp(getContextProperty(event, LoggingConstants.REQUEST_REMOTEIP))
                .setReferer(getContextProperty(event, LoggingConstants.REQUEST_REFERER))
                .setRequestHeaders(getContextPropertyAsMap(event, LoggingConstants.REQUEST_HEADERS))
                .setStatus(getContextPropertyAsInt(event, LoggingConstants.RESPONSE_STATUS))
                .setLatency(getContextPropertyAsDuration(event, LoggingConstants.RESPONSE_LATENCY))
                .setResponseSize(getContextProperty(event, LoggingConstants.RESPONSE_SIZE))
                .setResponseHeaders(getContextPropertyAsMap(event, LoggingConstants.RESPONSE_HEADERS));

        if (CIStringUtils.equals(type, ACCESSLOG_TYPE)) {
            jsonPayLoad.setHttpRequest(httpRequest);
        } else {
            jsonPayLoad.setHttpRequestContext(httpRequest);
        }

        // Par défault un statut >= 400 et < 500 est logué en WARN par l'AbstractLogRequestInterceptor
        // Si on décide d'ignorer les 404, on les loguera en INFO à la place.
        if (CIStringUtils.equals(type, ACCESSLOG_TYPE) && status != null && status == 404 && ignore404) {
            jsonPayLoad.setSeverity(Severity.INFO);
        }

        if (additionalFields != null) {
            for (KeyValuePair pair : additionalFields) {
                String value = pair.getValue();
                if (value.contains("${")) {
                    String resolvedValue = configuration.getStrSubstitutor().replace(event, value);
                    if (!resolvedValue.contains("${")) {
                        jsonPayLoad.addAdditionalField(pair.getKey(), resolvedValue);
                    }
                } else {
                    jsonPayLoad.addAdditionalField(pair.getKey(), value);
                }
            }
        }

        return jsonPayLoad;
    }

    private String getContextProperty(final LogEvent event, final String property) {

        return event.getContextData().getValue(property);
    }

    private Integer getContextPropertyAsInt(final LogEvent event, final String property) {

        String valueAsString = getContextProperty(event, property);
        return (valueAsString != null) ? Integer.valueOf(valueAsString) : null;
    }

    private String getContextPropertyAsDuration(final LogEvent event, final String property) {

        String valueAsString = getContextProperty(event, property);
        if (valueAsString != null) {
            long millis = Long.parseLong(valueAsString);
            double seconds = millis / 1000d;
            return seconds + "s";
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getContextPropertyAsMap(final LogEvent event, final String property) {

        String valueAsString = getContextProperty(event, property);
        if (valueAsString == null) {
            return null;
        }

        try {
            Map<String, ? extends Object> mvm = objectMapper.readValue(valueAsString, Map.class);
            if (mvm != null) {
                final Map<String, Object> map = new HashMap<>(mvm.size());
                mvm.keySet().forEach(key -> {
                    Object value = mvm.get(key);
                    if (Collection.class.isAssignableFrom(value.getClass())) {
                        Collection<String> values = (Collection<String>) value;
                        if (values.size() > 1) {
                            map.put(key, new HashSet<>(values));
                        } else if (values.size() == 1) {
                            map.put(key, values.iterator().next());
                        }
                    } else if (String.class.isAssignableFrom(value.getClass())) {
                        map.put(key, value);
                    }
                });
                return map;
            }
        } catch (IOException e) {
            LOGGER.warn("Erreur lors de la récupération de la map pour la clé '{}'.", property, e);
        }
        return null;
    }
}
