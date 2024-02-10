package fr.apithinking.apigreenscore.coreweb.autoconfigure.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:/swagger/web-springdoc.properties"})
@ConditionalOnClass({SpringDocWebMvcConfiguration.class})
@Slf4j
public class SpringdocAutoConfiguration {

    @Value("${spring.application.name:default}")
    private String applicationName;

    @Value("${spring.application.version:#{null}}")
    private String applicationVersion;

    @Bean
    @ConditionalOnMissingBean
    public OpenAPI openAPI() {

        LOGGER.info("AUTO-CONFIG initialization");

        OpenAPI r = new OpenAPI();
        if (StringUtils.isNotBlank(applicationVersion) && StringUtils.isNotBlank(applicationName)) {
            r = r.info(new Info().title(applicationName).version(applicationVersion));
        }
        return r;
    }
}