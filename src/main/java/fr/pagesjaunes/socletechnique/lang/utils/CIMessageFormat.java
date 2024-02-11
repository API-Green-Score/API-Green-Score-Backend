package fr.pagesjaunes.socletechnique.lang.utils;

import fr.pagesjaunes.socletechnique.lang.thread.InitializableThreadLocal;

import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

/**
 * @author gdespres
 */
public final class CIMessageFormat {

    private static final int MAX_ENTRIES = 100;

    private static final FieldPosition FIELD_POSITION_ZERO = new FieldPosition(0);

    private static final ThreadLocal<Map<KeyFormatter, MessageFormat>> FORMATS = ThreadLocal.withInitial(() -> new CIMRULinkedHashMap<>(MAX_ENTRIES));

    private static final InitializableThreadLocal<StringBuilder> SB_THREAD = new InitializableThreadLocal<>(StringBuilder.class);

    private static final ThreadLocal<StringBuffer> FORMAT_BUFFER = ThreadLocal.withInitial(StringBuffer::new);

    // ========================================================================
    // CONSTRUCTEUR
    // ========================================================================

    /**
     * Constructeur.
     */
    private CIMessageFormat() {
    }

    // ========================================================================
    // METHODES PUBLIQUES
    // ========================================================================

    /**
     * Methode permettant de formatter un message a partir d'un pattern et d'un ensemble de parametres.
     * - Optimisation Stephane Routhiau : gain de l'ordre de  80 % (temps) et 82,5 % en consommation memoire
     *
     * @param pPattern Pattern du message
     * @param pArgs    Ensemble des parametres
     * @return Le message formate.
     */
    public static String format(final String pPattern, final Object... pArgs) {
        return format(pPattern, null, false, false, pArgs);
    }

    public static String format(final String pPattern, final Locale locale, final boolean isMessageRessources, final boolean isEscape, final Object... pArgs) {
        String message = null;

        if (pPattern != null) {
            KeyFormatter key = new KeyFormatter(pPattern, locale, isMessageRessources, isEscape);
            MessageFormat formatter = FORMATS.get().get(key);
            if (formatter == null) {
                if (isMessageRessources) {
                    formatter = new MessageFormat(escapeMessageProperties(pPattern, isEscape));
                } else {
                    formatter = new MessageFormat(escape(pPattern));
                }

                if (locale != null) {
                    formatter.setLocale(locale);
                }
                FORMATS.get().put(key, formatter);
            }
            StringBuffer buffer = getCleanFormatBuffer();
            formatter.format(pArgs, buffer, FIELD_POSITION_ZERO);
            message = buffer.toString();
        }

        return message;
    }

    private static StringBuffer getCleanFormatBuffer() {
        StringBuffer buffer = FORMAT_BUFFER.get();
        buffer.setLength(0);
        return buffer;
    }

    // ========================================================================
    // METHODES PRIVEES
    // ========================================================================

    private static String escape(final String pPattern) {

        if (pPattern == null) {
            return pPattern;
        }

        int n = pPattern.length();
        final StringBuilder sb = SB_THREAD.getCleanValue();

        int openParameterBracket = 0;
        boolean isMessage = false;
        for (int i = 0; i < n; i++) {
            char ch = pPattern.charAt(i);
            switch (ch) {
                case '{':
                    if (pPattern.charAt(i + 1) < 48 || pPattern.charAt(i + 1) > 57) {
                        sb.append('\'').append(ch).append('\'');
                    } else {
                        sb.append(ch);
                        ++openParameterBracket;
                    }
                    break;
                case '}':
                    if (openParameterBracket > 0) {
                        sb.append(ch);
                        --openParameterBracket;
                        if (openParameterBracket == 0) {
                            isMessage = false;
                        }
                    } else {
                        // CAS specifique de deux accolades fermente qui se suivent :
                        // on ajoute un espace entre chacune d'elles car une fois echappee cela donne '}''}'
                        // et le '' est remplace par ' ce qui donne '}'}' et non pas }}
                        if (i > 0 && pPattern.charAt(i - 1) == '}') {
                            sb.append(' ');
                        }
                        sb.append('\'').append(ch).append('\'');
                    }
                    break;
                case '|':
                    sb.append(ch);
                    isMessage = false;
                    break;
                case '\'':
                    sb.append('\'').append(ch);
                    break;
                case '<', '#':
                    if (openParameterBracket > 0) {
                        if (!isMessage) {
                            sb.append(ch);
                            isMessage = true;
                        } else {
                            sb.append('\'').append(ch).append('\'');
                        }
                    } else {
                        sb.append(ch);
                    }
                    break;
                default:
                    sb.append(ch);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * Escape any single quote characters that are included in the specified
     * message string.
     *
     * @param string The string to be escaped
     */
    private static String escapeMessageProperties(final String string, final boolean isEscape) {

        if (!isEscape) {
            return string;
        }

        if ((string == null) || (string.indexOf('\'') < 0)) {
            return string;
        }

        int n = string.length();
        StringBuilder sb = SB_THREAD.getCleanValue();

        for (int i = 0; i < n; i++) {
            char ch = string.charAt(i);

            if (ch == '\'') {
                sb.append('\'');
            }

            sb.append(ch);
        }

        return sb.toString();

    }

    static class KeyFormatter {

        private final String pattern;

        private final Locale locale;

        private final boolean isMessageRessources;

        private final boolean isEscape;

        /**
         *
         */
        KeyFormatter(final String pattern, final Locale locale, final boolean isMessageRessources, final boolean isEscape) {
            this.pattern = pattern;
            this.locale = locale;
            this.isMessageRessources = isMessageRessources;
            this.isEscape = isEscape;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (isEscape ? 1231 : 1237);
            result = prime * result + (isMessageRessources ? 1231 : 1237);
            result = prime * result + ((locale == null) ? 0 : locale.hashCode());
            result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            KeyFormatter other = (KeyFormatter) obj;
            if (isEscape != other.isEscape) {
                return false;
            }
            if (isMessageRessources != other.isMessageRessources) {
                return false;
            }
            if (locale == null) {
                if (other.locale != null) {
                    return false;
                }
            } else if (!locale.equals(other.locale)) {
                return false;
            }
            if (pattern == null) {
                return other.pattern == null;
            } else {
                return pattern.equals(other.pattern);
            }
        }

    }
}
