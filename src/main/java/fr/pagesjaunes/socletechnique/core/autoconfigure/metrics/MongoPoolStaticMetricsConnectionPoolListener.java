package fr.pagesjaunes.socletechnique.core.autoconfigure.metrics;

import com.mongodb.connection.ServerId;
import com.mongodb.event.ConnectionPoolClosedEvent;
import com.mongodb.event.ConnectionPoolCreatedEvent;
import com.mongodb.event.ConnectionPoolListener;
import io.micrometer.common.lang.NonNullApi;
import io.micrometer.common.lang.NonNullFields;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.mongodb.DefaultMongoConnectionPoolTagsProvider;
import io.micrometer.core.instrument.binder.mongodb.MongoConnectionPoolTagsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@NonNullApi
@NonNullFields
public class MongoPoolStaticMetricsConnectionPoolListener implements ConnectionPoolListener {

    private static final String METRIC_PREFIX = "mongodb.driver.pool.";
    private final Map<ServerId, AtomicInteger> minPoolSizes = new ConcurrentHashMap<>();
    private final Map<ServerId, AtomicInteger> maxPoolSizes = new ConcurrentHashMap<>();
    private final Map<ServerId, List<Meter>> meters = new ConcurrentHashMap<>();

    private final MeterRegistry registry;
    private final MongoConnectionPoolTagsProvider tagsProvider;

    /**
     * Create a new {@code MongoMetricsConnectionPoolListener}.
     *
     * @param registry registry to use
     */
    public MongoPoolStaticMetricsConnectionPoolListener(MeterRegistry registry) {
        this(registry, new DefaultMongoConnectionPoolTagsProvider());
    }

    /**
     * Create a new {@code MongoMetricsConnectionPoolListener}.
     *
     * @param registry registry to use
     * @param tagsProvider tags provider to use
     * @since 1.7.0
     */
    public MongoPoolStaticMetricsConnectionPoolListener(MeterRegistry registry, MongoConnectionPoolTagsProvider tagsProvider) {
        this.registry = registry;
        this.tagsProvider = tagsProvider;
    }

    @Override
    public void connectionPoolCreated(ConnectionPoolCreatedEvent event) {
        List<Meter> connectionMeters = new ArrayList<>();
        connectionMeters.add(registerGauge(event, METRIC_PREFIX + "minsize",
                "The minimum allowed size of the pool, including idle and and in-use members", minPoolSizes));
        AtomicInteger minPoolSize = minPoolSizes.get(event.getServerId());
        minPoolSize.set(event.getSettings().getMinSize());
        connectionMeters.add(registerGauge(event, METRIC_PREFIX + "maxsize",
                "The maximum allowed size of the pool, including idle and and in-use members", maxPoolSizes));
        meters.put(event.getServerId(), connectionMeters);
        AtomicInteger maxPoolSize = maxPoolSizes.get(event.getServerId());
        maxPoolSize.set(event.getSettings().getMaxSize());
    }

    @Override
    public void connectionPoolClosed(ConnectionPoolClosedEvent event) {
        ServerId serverId = event.getServerId();
        for (Meter meter : meters.get(serverId)) {
            registry.remove(meter);
        }
        meters.remove(serverId);
        minPoolSizes.remove(serverId);
        maxPoolSizes.remove(serverId);
    }

    private Gauge registerGauge(ConnectionPoolCreatedEvent event, String metricName, String description, Map<ServerId, AtomicInteger> metrics) {
        AtomicInteger value = new AtomicInteger();
        metrics.put(event.getServerId(), value);
        return Gauge.builder(metricName, value, AtomicInteger::doubleValue)
                .description(description)
                .tags(tagsProvider.connectionPoolTags(event))
                .register(registry);
    }
}