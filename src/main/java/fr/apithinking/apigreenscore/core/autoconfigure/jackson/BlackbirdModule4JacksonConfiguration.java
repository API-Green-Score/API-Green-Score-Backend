package fr.apithinking.apigreenscore.core.autoconfigure.jackson;

import com.fasterxml.jackson.module.blackbird.BlackbirdModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
@Slf4j
public class BlackbirdModule4JacksonConfiguration {

    @Bean
    public BlackbirdModule blackbirdModule() {
        LOGGER.info("AUTO-CONFIG initialization");
        return new BlackbirdModule();
    }
}
