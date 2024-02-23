package fr.apithinking.apigreenscore.core.autoconfigure.jackson;

import com.fasterxml.jackson.module.blackbird.BlackbirdModule;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class BlackbirdModule4JacksonConfiguration {

    @Bean
    public BlackbirdModule blackbirdModule() {
        return new BlackbirdModule();
    }
}
