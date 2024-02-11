package fr.pagesjaunes.socletechnique.lang.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public final class CIPatternUtils { //NOPMD pb cyclomatic : pas le choix ici

    private static final Map<String, Pattern> patterns = new ConcurrentHashMap<>();

    // ========================================================================
    // CONSTRUCTEUR
    // ========================================================================

    /**
     * Constructeur.
     */
    private CIPatternUtils() {
    }

    // ========================================================================
    // METHODES PUBLIQUES
    // ========================================================================

    /**
     * Methode permettant de formatter un message a partir d'un pattern et d'un ensemble de parametres.
     * - Optimisation Stephane Routhiau : gain de l'ordre de  80 % (temps) et 82,5 % en consommation memoire
     *
     * @param pPattern Pattern du message
     * @return Le message formate.
     */
    public static Pattern getPattern(final String pPattern) {
        return patterns.computeIfAbsent(pPattern, Pattern::compile);
    }

    public static boolean matches(final String pattern, final String stringToTest) {
        return getPattern(pattern).matcher(stringToTest).matches();
    }

    /**
     * Convertion de pattern glob (type shell) vers java
     *
     * @param pLine ligne à convertir
     * @return ligne convertie en pattern java
     */
    public static String convertGlobToRegEx(final String pLine) { //NOPMD pb cyclomatic : pas le choix ici
        String line = pLine.trim();

        // Remove beginning and ending * globs because they're useless
        line = CIStringUtils.removeStart(line, "*");
        line = CIStringUtils.removeEnd(line, "*");

        StringBuilder sb = new StringBuilder(line.length() + 10);
        boolean escaping = false;
        int inCurlies = 0;
        for (char currentChar : line.toCharArray()) {
            switch (currentChar) { //NOPMD bien voulu dans certains cases
                case '*':
                    if (escaping) {
                        sb.append("\\*");
                    } else {
                        sb.append(".*");
                    }
                    escaping = false;
                    break;
                case '?':
                    if (escaping) {
                        sb.append("\\?");
                    } else {
                        sb.append('.');
                    }
                    escaping = false;
                    break;
                case '!':
                    if (escaping) {
                        sb.append("\\!");
                    } else {
                        sb.append('^');
                    }
                    escaping = false;
                    break;
                case '.', '(', ')', '+', '|', '^', '$', '@', '%':
                    sb.append('\\');
                    sb.append(currentChar);
                    escaping = false;
                    break;
                case '\\':
                    if (escaping) {
                        sb.append("\\\\");
                        escaping = false;
                    } else {
                        escaping = true;
                    }
                    break;
                case '{':
                    if (escaping) {
                        sb.append("\\{");
                    } else {
                        sb.append('(');
                        inCurlies++;
                    }
                    escaping = false;
                    break;
                case '}':
                    if (inCurlies > 0 && !escaping) {
                        sb.append(')');
                        inCurlies--;
                    } else if (escaping) {
                        sb.append("\\}");
                    } else {
                        sb.append("}");
                    }
                    escaping = false;
                    break;
                case ',':
                    if (inCurlies > 0 && !escaping) {
                        sb.append('|');
                    } else if (escaping) {
                        sb.append("\\,");
                    } else {
                        sb.append(",");
                    }
                    break;
                default:
                    escaping = false;
                    sb.append(currentChar);
            }
        }
        return sb.toString();
    }

}
