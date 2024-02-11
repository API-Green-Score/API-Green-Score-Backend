package fr.pagesjaunes.socletechnique.logging.log4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

@Plugin(name = "minLevel", category = StrLookup.CATEGORY)
public class MinLevelLookup extends AbstractLevelLookup {

    @Override
    public Level apply(final Level t, final Level u) {

        return (t.intLevel() > u.intLevel()) ? t : u;
    }
}