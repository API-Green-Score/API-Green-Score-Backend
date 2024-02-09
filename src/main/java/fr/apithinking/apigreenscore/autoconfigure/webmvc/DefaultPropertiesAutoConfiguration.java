package fr.apithinking.apigreenscore.autoconfigure.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
        "classpath:/starter-web-management.properties",
        "classpath:/starter-web-server.properties",
        "classpath:/starter-web-spring.properties"
})
public class DefaultPropertiesAutoConfiguration {
}
