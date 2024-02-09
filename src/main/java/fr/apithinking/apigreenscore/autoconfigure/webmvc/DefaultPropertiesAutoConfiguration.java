package fr.apithinking.apigreenscore.autoconfigure.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
        "classpath:/autoconfigure/starter-web-management.properties",
        "classpath:/autoconfigure/starter-web-server.properties",
        "classpath:/autoconfigure/starter-web-spring.properties"
})
public class DefaultPropertiesAutoConfiguration {
}
