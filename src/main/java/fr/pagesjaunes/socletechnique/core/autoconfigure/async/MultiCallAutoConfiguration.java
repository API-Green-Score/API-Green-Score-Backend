package fr.pagesjaunes.socletechnique.core.autoconfigure.async;

import fr.pagesjaunes.socletechnique.core.async.MultiCallHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultiCallAutoConfiguration {

    @Bean
    public MultiCallHelper multiCallHelper() {
        return new MultiCallHelper();
    }
}
