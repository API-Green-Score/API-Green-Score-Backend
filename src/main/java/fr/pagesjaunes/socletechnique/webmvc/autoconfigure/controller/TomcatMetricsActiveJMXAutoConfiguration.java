package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.controller;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * fix for https://github.com/spring-projects/spring-boot/issues/16498
 * Tomcat embedded not launch with jmx by default
 * Tomcat Metrics need jmx to be obtain
 * @author glegargeant
 *
 */
@Configuration

@AutoConfigureBefore(name = { "org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory" })
public class TomcatMetricsActiveJMXAutoConfiguration {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> activateTomcatMBeanServer() {

        return factory -> factory.setDisableMBeanRegistry(false);
    }

}
