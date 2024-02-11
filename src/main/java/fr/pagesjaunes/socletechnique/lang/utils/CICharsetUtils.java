package fr.pagesjaunes.socletechnique.lang.utils;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CICharsetUtils {

    private static final Map<String, Charset> charsets = new ConcurrentHashMap<>();

    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    // ========================================================================
    // CONSTRUCTEUR
    // ========================================================================

    /**
     * Constructeur.
     */
    private CICharsetUtils() {
    }

    // ========================================================================
    // METHODES PUBLIQUES
    // ========================================================================

    public static Charset getCharset(final String pCharset) {
        return charsets.computeIfAbsent(pCharset, Charset::forName);
    }

    /**
     * @return
     */
    public static Charset defaultCharset() {
        return DEFAULT_CHARSET;
    }
}
