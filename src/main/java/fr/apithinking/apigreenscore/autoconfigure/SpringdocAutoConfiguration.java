package fr.apithinking.apigreenscore.autoconfigure;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:/web-springdoc.properties"})
@ConditionalOnClass({SpringDocWebMvcConfiguration.class})
public class SpringdocAutoConfiguration {

    @Value("${spring.application.swagger.title:default}")
    private String applicationTitle;

    @Value("${spring.application.swagger.version:#{null}}")
    private String applicationVersion;

    @Value("${spring.application.swagger.description:#{null}}")
    private String applicationDescription;

    @Value("${spring.application.swagger.license.name:#{null}}")
    private String applicationLicenseName;

    @Value("${spring.application.swagger.license.url:#{null}}")
    private String applicationLicenseUrl;

    @Value("${spring.application.swagger.contact.name:#{null}}")
    private String applicationContactName;

    @Value("${spring.application.swagger.contact.url:#{null}}")
    private String applicationContactUrl;

    @Value("${spring.application.swagger.terms.url:#{null}}")
    private String applicationTermsUrl;

    @Bean
    @ConditionalOnMissingBean
    public OpenAPI openAPI() {
        OpenAPI openApi = new OpenAPI();
        if (StringUtils.isNotBlank(applicationVersion) && StringUtils.isNotBlank(applicationTitle)) {
            openApi = openApi.info(new Info()
                    .title(applicationTitle)
                    .version(applicationVersion)
                    .description(applicationDescription)
                    .license(new License().name(applicationLicenseName).url(applicationLicenseUrl))
                    .contact(new Contact().url(applicationContactUrl).name(applicationContactName))
                    .termsOfService(applicationTermsUrl)
            );
        }
        return openApi;
    }
}
