package fr.pagesjaunes.socletechnique.logging.log4j.stackdriver;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author gdespres
 */
@Accessors(chain = true)
@Getter
@Setter
public class Timestamp {

    private long seconds;

    private long nanos;
}
