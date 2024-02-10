package fr.pagesjaunes.socletechnique.promethee;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;
import java.util.TreeMap;

/**
 * Configuration des appels au serveur promethee pour la configuration de l'application.
 * 
 * @author gdespres
 */
@ConfigurationProperties(prefix = "socletechnique.config.promethee")
@Data
public class PrometheeConfigProperties {

    /**
     * Enables Promethee Config
     */
    private boolean enabled = true;

    /**
     * Promethee base url.
     */
    @Value("${confpj.promethee.v2.url:#{null}}")
    private String url;

    /**
     * Default domain
     */
    private String[] domain = new String[] { "environment" };

    /**
     * Name of the application.
     */
    @Value("${spring.application.name:default}")
    private String application;

    /**
     * Default profile
     */
    @Value("${confpj.promethee.v2.profile:default}")
    private String profile;

    /**
     * Default label
     */
    @Value("${confpj.promethee.v2.label:auto}")
    private String label;

    /**
     * Enables Promethee config auto reload
     */
    private boolean autoReload = false;

    /**
     * Interval (sec) when auto reload is active
     */
    private int reloadInterval = 60;

    /**
     * Domains configuration
     */
    @Setter(AccessLevel.NONE)
    @NestedConfigurationProperty
    private final Map<String, PrometheeDomainConfigProperties> domains = new TreeMap<>();

    /**
     * Configuration spéficique à un domain particulier.
     * Si une des valeurs n'est pas précisée, la valeur par défaut est utilisée.
     * 
     * @author gdespres
     */
    @Data
    static class PrometheeDomainConfigProperties {

        /**
         * Enables domain config
         */
        private boolean enabled;

        /**
         * Profiles to use for this domain. 
         * If not defined, default profile is used.
         */
        private String profile;

        /**
         * Promethee label to use for this domain.
         * If not defined, default label is used.
         */
        private String label;

        /**
         * Comma delimited string of application to use for this domain.
         * If not defined, default application is used.
         */
        private String application;
    }
}
