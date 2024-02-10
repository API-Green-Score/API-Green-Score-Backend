package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.logging;

import fr.pagesjaunes.socletechnique.webmvc.logging.AccessLogRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebLoggingAutoconfiguration implements WebMvcConfigurer {

    @Bean
    public AccessLogRequestInterceptor accessLogRequestInterceptor() {

        return new AccessLogRequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(accessLogRequestInterceptor()).addPathPatterns("/**");
    }
}
