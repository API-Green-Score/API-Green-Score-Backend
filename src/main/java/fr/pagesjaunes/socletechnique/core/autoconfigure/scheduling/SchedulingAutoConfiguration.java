package fr.pagesjaunes.socletechnique.core.autoconfigure.scheduling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@PropertySource({"classpath:/socle-technique-core-scheduling.properties"})
@EnableScheduling
public class SchedulingAutoConfiguration implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(internalTaskExecutor());
    }

    @Bean(destroyMethod = "shutdown", name = "socle-technique.internalTaskExecutor")
    public Executor internalTaskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }
}
