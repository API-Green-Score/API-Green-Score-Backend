package fr.pagesjaunes.socletechnique.webmvc.autoconfigure.webserver;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.AbstractProtocol;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration")
public class WebServerAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "confpj.socletechnique.management", name = "enabled", havingValue = "true", matchIfMissing = true)
    TomcatServletWebServerFactory tomcatServletWebServerFactory(
            ObjectProvider<TomcatConnectorCustomizer> connectorCustomizers,
            ObjectProvider<TomcatContextCustomizer> contextCustomizers,
            ObjectProvider<TomcatProtocolHandlerCustomizer<?>> protocolHandlerCustomizers,
            @Value("${confpj.socletechnique.management.port:8081}") int managementPort) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.getTomcatConnectorCustomizers()
                .addAll(connectorCustomizers.orderedStream().toList());
        factory.getTomcatContextCustomizers()
                .addAll(contextCustomizers.orderedStream().toList());
        factory.getTomcatProtocolHandlerCustomizers()
                .addAll(protocolHandlerCustomizers.orderedStream().toList());

        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(managementPort);
        AbstractProtocol<?> protocol = (AbstractProtocol<?>) connector.getProtocolHandler();
        protocol.setMaxThreads(5);
        protocol.setMinSpareThreads(1);

        factory.addAdditionalTomcatConnectors(connector);
        return factory;
    }
}
