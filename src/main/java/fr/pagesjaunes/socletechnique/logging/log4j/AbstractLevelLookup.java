package fr.pagesjaunes.socletechnique.logging.log4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.lookup.AbstractLookup;

import java.util.Locale;
import java.util.function.BinaryOperator;

public abstract class AbstractLevelLookup extends AbstractLookup implements BinaryOperator<Level> {

    private static final String LEVELS_SEPARATOR = ":";

    @Override
    public String lookup(final LogEvent event, final String key) {

        if (key.contains(LEVELS_SEPARATOR)) {

            String[] levels = key.split(LEVELS_SEPARATOR, 2);
            Level firstLevel = Level.getLevel(levels[0].toUpperCase(Locale.US));
            Level secondLevel = Level.getLevel(levels[1].toUpperCase(Locale.US));

            Level finalLevel;
            if (firstLevel == null && secondLevel == null) {
                finalLevel = Level.OFF;
            } else if (firstLevel == null) {
                finalLevel = secondLevel;
            } else if (secondLevel == null) {
                finalLevel = firstLevel;
            } else {
                finalLevel = this.apply(firstLevel, secondLevel);
            }
            return finalLevel.name();
        } else {

        	Level level = Level.getLevel(key.toUpperCase(Locale.US));        	
            return (level != null) ? key : Level.OFF.name();
        }
    }

}