package fr.apithinking.apigreenscore.core.smoketest.mongo;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class MongoHealthIndicator extends AbstractHealthIndicator {

    private final MongoTemplate mongoTemplate;

    @Setter
    private String uri;

    public MongoHealthIndicator(MongoTemplate mongoTemplate) {
        super("MongoDB health check failed");
        Assert.notNull(mongoTemplate, "MongoTemplate must not be null");
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Document result = this.mongoTemplate.executeCommand("{ buildInfo: 1 }");

        Map<String, Object> details = new HashMap<>();
        details.put("version", result.getString("version"));
        details.put("db", mongoTemplate.getDb().getName());
        if (StringUtils.isNotBlank(uri)) {
            details.put("uri", uri);
        }
        builder.up().withDetails(details);
    }
}
