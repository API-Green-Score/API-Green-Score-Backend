package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.async;

import fr.pagesjaunes.socletechnique.webmvc.async.ContextAwarePoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@ConditionalOnWebApplication
@Slf4j
public class AyncExecutorAutoConfiguration implements AsyncConfigurer {

    @Autowired
    private TaskExecutorBuilder builder; // NOSONAR can not inject directly the field in the method that only use it (overriden method)

    @Override
    @Bean("contextAwareApplicationTaskExecutor")
    @Primary
    public Executor getAsyncExecutor() {
        return builder.build(ContextAwarePoolExecutor.class);
    }

}
