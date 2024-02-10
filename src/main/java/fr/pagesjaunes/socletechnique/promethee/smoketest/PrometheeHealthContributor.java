package fr.pagesjaunes.socletechnique.promethee.smoketest;

import fr.pagesjaunes.socletechnique.promethee.PrometheeConfigPropertySourceLocator;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PrometheeHealthContributor implements CompositeHealthContributor {

    private final Collection<NamedContributor<HealthContributor>> healthContributors;

    private final Map<String, HealthContributor> namedHealthContributors;

    public PrometheeHealthContributor(PrometheeConfigPropertySourceLocator prometheeConfigPropertySourceLocator) {
        healthContributors = prometheeConfigPropertySourceLocator.getPrometheePropertySources().stream().map(s -> {
            String name = "Promethee-" + s.getDomain() + "_" + s.getLabel() + "_" + s.getApplication() + "_" + s.getProfile();
            HealthContributor hc = prometheeConfigPropertySourceLocator.getPrometheePropertySourceHealthIndicator(s);
            return NamedContributor.of(name, hc);
        }).toList();
        namedHealthContributors = new HashMap<>();
        healthContributors.forEach(nc -> namedHealthContributors.put(nc.getName(), nc.getContributor()));
    }

    @Override
    public HealthContributor getContributor(String name) {
        return namedHealthContributors.get(name);
    }

    @Override
    public Iterator<NamedContributor<HealthContributor>> iterator() {
        return healthContributors.iterator();
    }
}
