package fr.pagesjaunes.socletechnique.promethee;

import lombok.Getter;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public class PrometheePropertySource extends EnumerablePropertySource<Map<String, String>> {

    private final String domain;

    private final String application;

    private final String label;

    private final String profile;

    public PrometheePropertySource(final String domain, String label, String application, String profile, final Map<String, String> config) {
        super(domain, config);
        this.domain = domain;
        this.label = label;
        this.application = application;
        this.profile = profile;
    }

    @Override
    public Object getProperty(@NonNull final String name) {

        return getSource().get(name);
    }

    @Override
    @NonNull
    public String[] getPropertyNames() {
        return StringUtils.toStringArray(source.keySet());
    }

    public void updateProperties(Map<String, String> config) {
        Map<String, String> source = getSource();
        List<String> deletedKeys = Arrays.stream(getPropertyNames()).filter(n -> !config.containsKey(n)).toList();
        // Set new property values
        source.putAll(config);
        // Delete properties no longer existing
        deletedKeys.forEach(source::remove);
    }

}
