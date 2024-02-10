package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.error;

import fr.pagesjaunes.socletechnique.webmvc.exception.handler.RestErrorAdvice;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
public class ErrorsProblemsAutoConfiguration {

    @Bean
    public RestErrorAdvice restErrorAdvice() {
        return new RestErrorAdvice();
    }

}
