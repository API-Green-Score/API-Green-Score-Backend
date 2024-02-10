package fr.pagesjaunes.socletechnique.promethee;

import fr.pagesjaunes.socletechnique.lang.utils.CIStringUtils;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Specific ResourceLoader for loading Promethee resources as Spring Resource.
 * Handle specific 'promethee:' URLs.
 * URL format: promethee:domain[/path][?resolvePlaceholders=true|false]
 * path is optional
 * resolvePlaceholders is optional, defaults to false
 * Ex.:
 * promethee:domain
 * promethee:domain/path/to/file
 * promethee:domain/path?resolvePlaceholders=true
 */
public class PrometheeResourceLoader implements ResourceLoader {

    private static final String PROMETHEE_URL_PREFIX = "promethee:";

    private final ApplicationContext applicationContext;

    private final ResourceLoader delegate;

    public PrometheeResourceLoader(ApplicationContext applicationContext, ResourceLoader resourceLoader) {
        this.applicationContext = applicationContext;
        this.delegate = resourceLoader;
    }

    @Override
    @NonNull
    public Resource getResource(@NonNull String location) {
        if (CIStringUtils.startsWith(location, PROMETHEE_URL_PREFIX)) {
            PrometheeUrlHelper.PrometheeUrlMatchResult result = PrometheeUrlHelper.matches(location.substring(PROMETHEE_URL_PREFIX.length()));
            String prometheeResourceUrl = PrometheeUrlHelper.buildUrl(result,
                    applicationContext.getEnvironment().getProperty("confpj.promethee.v2.url"),
                    applicationContext.getEnvironment().getProperty("confpj.promethee.v2.label", "auto"),
                    applicationContext.getEnvironment().getProperty("confpj.promethee.v2.profile", "default"),
                    CIStringUtils.replace(applicationContext.getEnvironment().getProperty("spring.application.name"), "-", ""));
            return delegate.getResource(prometheeResourceUrl);
        }
        return delegate.getResource(location);
    }

    @Override
    public ClassLoader getClassLoader() {
        return delegate.getClassLoader();
    }


    public static final class PrometheeUrlHelper {

        @Data
        @Builder
        public static class PrometheeUrlMatchResult {

            private String domain;

            private String path;

            private boolean resolvePlaceholders;
        }

        private static final Pattern pattern = Pattern.compile("^([^/?]+)(?>/([^?]+))?(?>\\?(\\w+=\\w+))?$"); // NOSONAR usage of regex is safe (tested)

        private PrometheeUrlHelper() {

        }

        public static PrometheeUrlMatchResult matches(String value) {
            Matcher matcher = pattern.matcher(value);
            if (matcher.matches()) {
                PrometheeUrlMatchResult.PrometheeUrlMatchResultBuilder builder = PrometheeUrlMatchResult.builder().domain(matcher.group(1));
                if (CIStringUtils.isNotBlank(matcher.group(2))) {
                    builder = builder.path(matcher.group(2));
                }
                if (CIStringUtils.isNotBlank(matcher.group(3))) {
                    String rphs = matcher.group(3);
                    if (CIStringUtils.equals(rphs, "resolvePlaceholders=true")) {
                        builder = builder.resolvePlaceholders(true);
                    } else if (CIStringUtils.equals(rphs, "resolvePlaceholders=false")) {
                        builder = builder.resolvePlaceholders(false);
                    } else {
                        throw new IllegalArgumentException("Invalid Promethee resource url query param 'resolvePlaceholders=true|false expected, got '" + rphs + "'");
                    }
                }
                return builder.build();
            }
            throw new IllegalArgumentException("Invalid Promethee resource url (" + value + ")");
        }

        public static String buildUrl(PrometheeUrlMatchResult matchResult, String prometheeBaseUrl, String prometheeLabel, String prometheeProfile, String applicationName) {
            StringBuilder sb = new StringBuilder();
            sb.append(prometheeBaseUrl)
                    .append('/')
                    .append(matchResult.getDomain())
                    .append('/')
                    .append(prometheeLabel)
                    .append('/')
                    .append(applicationName)
                    .append('/')
                    .append(prometheeProfile);
            if (CIStringUtils.isNotBlank(matchResult.getPath())) {
                sb.append('/')
                        .append(matchResult.getPath());
            }
            sb.append("?resolvePlaceholders=");
            if (matchResult.isResolvePlaceholders()) {
                sb.append("true");
            } else {
                sb.append("false");
            }
            return sb.toString();
        }

    }
}
