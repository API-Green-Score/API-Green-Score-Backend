package fr.apithinking.apigreenscore.coreweb.autoconfigure.error;

import fr.apithinking.apigreenscore.coreweb.handler.RestErrorAdvice;
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
