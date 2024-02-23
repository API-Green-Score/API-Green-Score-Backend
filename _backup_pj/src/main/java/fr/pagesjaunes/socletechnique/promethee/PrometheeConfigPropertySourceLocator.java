package fr.pagesjaunes.socletechnique.promethee;

import fr.pagesjaunes.socletechnique.promethee.PrometheeConfigProperties.PrometheeDomainConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class PrometheeConfigPropertySourceLocator implements PropertySourceLocator {

    private static final String ALL_VARIABLES_PATH = "/{domain}/{label}/{application}/{profile}?resolvePlaceholders=false";

    private final PrometheeConfigProperties configProperties;

    private final RestTemplate restTemplate;

    private final List<PrometheePropertySource> reloadablePropertySources = new ArrayList<>();

    private static final ParameterizedTypeReference<Map<String, String>> prometheeResponseType = new ParameterizedTypeReference<Map<String, String>>() {
    };

    public PrometheeConfigPropertySourceLocator(
            final PrometheeConfigProperties configProperties,
            final RestTemplateBuilder restTemplateBuilder
    ) {

        this.configProperties = configProperties;

        this.restTemplate = restTemplateBuilder
                .rootUri(configProperties.getUrl())
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .additionalInterceptors((request, body, execution) -> {
                    request.getHeaders().set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                    return execution.execute(request, body);
                })
                .build();

    }

    @Override
    public PropertySource<?> locate(final Environment environment) {

        CompositePropertySource propertySources = new CompositePropertySource("promethee");

        addPropertySources(propertySources, configProperties.getDomain());

        return propertySources;
    }

    public Collection<PrometheePropertySource> getPrometheePropertySources() {
        return reloadablePropertySources;
    }

    public HealthIndicator getPrometheePropertySourceHealthIndicator(PrometheePropertySource source) {
        return () -> {

            String url = configProperties.getUrl() + "/" + source.getDomain() + "/" + source.getLabel() + "/" + source.getApplication() + "/" + source.getProfile();
            try {
                ResponseEntity<Map<String, String>> resp = callPromethee(source);
                if (resp.getStatusCode().is2xxSuccessful()) {
                    return Health.up().withDetail("url", url).build();
                } else {
                    return Health.down().withDetail("url", url).withDetail("status", resp.getStatusCode()).build();
                }
            } catch (RestClientException e) {
                return Health.down().withDetail("url", url).withDetail("error", e).build();
            }
        };
    }

    private void addPropertySources(CompositePropertySource propertySources, Map<String, PrometheeDomainConfigProperties> domains) {

        if (domains != null) {
            for (Entry<String, PrometheeDomainConfigProperties> entry : domains.entrySet()) {

                String domain = entry.getKey();
                PrometheeDomainConfigProperties domainConfig = entry.getValue();
                addPropertySource(propertySources, domain, domainConfig);
            }
        }
    }

    private void addPropertySource(CompositePropertySource propertySources, String domain, PrometheeDomainConfigProperties domainConfig) {

        if (domainConfig != null && domainConfig.isEnabled()) {
            String label = domainConfig.getLabel() != null ? domainConfig.getLabel() : configProperties.getLabel();
            String application = domainConfig.getApplication() != null ? domainConfig.getApplication() : configProperties.getApplication();
            String profile = domainConfig.getProfile() != null ? domainConfig.getProfile() : configProperties.getProfile();

            try {
                registerPropertySource(propertySources, getPrometheeEnvironmentConfig(domain, label, application, profile));
            } catch (Exception e) {

                String message = String.format("Error loading configuration for /%s/%s/%s/%s", domain, label, application, profile);
                LOGGER.error(message, e);
            }
        }
    }

    private void addPropertySources(CompositePropertySource propertySources, String[] domains) {

        if (domains != null) {
            for (String domain : domains) {

                String label = configProperties.getLabel();
                String application = configProperties.getApplication();
                String profile = configProperties.getProfile();

                try {
                    registerPropertySource(propertySources, getPrometheeEnvironmentConfig(domain, label, application, profile));
                } catch (Exception e) {

                    String message = String.format("Error loading configuration for /%s/%s/%s/%s", domain, label, application, profile);
                    LOGGER.error(message, e);
                }
            }
        }
    }

    private PrometheePropertySource getPrometheeEnvironmentConfig(
            final String domain,
            final String label,
            final String application,
            final String profile) {

        var pps = new PrometheePropertySource(domain, label, application, profile, new ConcurrentHashMap<>());
        var response = callPromethee(pps);
        if (!response.getStatusCode().is2xxSuccessful()) {

            String message = String.format("Invalid response from Promethee for domain=%s, label=%s, application=%s and profile=%s", domain, label, application, profile);
            throw new HttpClientErrorException(response.getStatusCode(), message);
        }

        return new PrometheePropertySource(domain, label, application, profile, new ConcurrentHashMap<>(response.getBody()));
    }

    private void registerPropertySource(CompositePropertySource propertySources, PrometheePropertySource ps) {
        propertySources.addPropertySource(ps);
        reloadablePropertySources.add(ps);
    }

    private ResponseEntity<Map<String, String>> callPromethee(PrometheePropertySource source) {
        String domain = source.getDomain();
        String label = source.getLabel();
        String application = source.getApplication();
        String profile = source.getProfile();
        LOGGER.info("Calling promethee (config loading) for domain={}, label={}, application={}, profile={}", domain, label, application, profile);
        return restTemplate.exchange(ALL_VARIABLES_PATH, HttpMethod.GET, null, prometheeResponseType, domain, label, application, profile);
    }
}

