package fr.pagesjaunes.socletechnique.lang.utils;

import fr.pagesjaunes.socletechnique.lang.thread.InitializableThreadLocal;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.regex.Matcher;

/**
 * Classe utilitaire pour les manipulations de chaines de caracteres.
 *
 * @author mbrossais
 */
public final class CIStringUtils extends StringUtils { //NOPMD pb cyclomatic : pas le choix ici

    private static final char CHAR_ESPACE = 0x20;

    /**
     * Zone ASCII imprimable
     */
    private static final char CHAR_CODE_MIN1 = CHAR_ESPACE;

    private static final char CHAR_CODE_MAX1 = 0x7e;

    /**
     * Zone a remplacer par des espaces
     */

    private static final char CHAR_REMPL_BLANC_MIN = 0x21;

    private static final char CHAR_REMPL_BLANC_MAX = 0x2e;

    /**
     * Zone char ASCII minuscule
     */
    private static final char CHAR_LOWERCASE_MIN = 0x61;

    private static final char CHAR_LOWERCASE_MAX = 0x7a;

    /**
     * Zone Latin 1
     */
    private static final char CHAR_CODE_MIN2 = 0xa0;

    private static final char CHAR_CODE_MAX2 = 0xff;

    /**
     * Normalization : suppression des accents
     */
    private static final char[] NORMALIZE = {
            /* A0-AF */
            0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20,
            /* B0-BF */
            0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20,
            /* C0-CF */
            0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x43, 0x45, 0x45, 0x45, 0x45, 0x49, 0x49, 0x49, 0x49,
            /* D0-DF */
            0x20, 0x4e, 0x4f, 0x4f, 0x4f, 0x4f, 0x4f, 0x20, 0x20, 0x55, 0x55, 0x55, 0x55, 0x59, 0x20, 0x20,
            /* E0-EF */
            0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x41, 0x43, 0x45, 0x45, 0x45, 0x45, 0x49, 0x49, 0x49, 0x49,
            /* F0-FF */
            0x20, 0x4e, 0x4f, 0x4f, 0x4f, 0x4f, 0x4f, 0x20, 0x20, 0x55, 0x55, 0x55, 0x55, 0x59, 0x20, 0x59};

    /**
     * Thread local pour obtenir une copie de stringBuilder, afin de gerer une copie par thread utilisateur.
     */
    private static final InitializableThreadLocal<StringBuilder> threadLocalSB = new InitializableThreadLocal<>(StringBuilder.class, 200);

    private static final int CHAR_BUFFER_SIZE_DEFAULT = 128;

    private static final ThreadLocal<char[]> buffer = ThreadLocal.withInitial(() -> new char[CHAR_BUFFER_SIZE_DEFAULT]);

    /**
     * Constructeur.
     */
    private CIStringUtils() {
    }

    /**
     * Remplace les chaines correspondant au regex par la chaine "newString" dans le contenu du String passé en
     * parametre.
     *
     * @param str       le texte a traiter
     * @param regex     le regex
     * @param newString la chaine de remplacement.
     */
    public static String replaceAllWithRegex(final String str, final String regex, final String newString) {
        if (CIStringUtils.isNotBlank(str) && CIStringUtils.isNotBlank(regex)) {
            Matcher matcher = CIPatternUtils.getPattern(regex).matcher(str);
            return matcher.replaceAll(newString);
        }
        return str;
    }

    /**
     * Remplace la chaine "oldString" par la chaine "newString" dans le contenu du StringBuilder passé en paramétre.
     *
     * @param sb        le texte é traiter
     * @param oldString la chaine a remplacer
     * @param newString la chaine de remplacement.
     */
    public static void replace(final StringBuilder sb, final String oldString, final String newString) {
        if (sb != null && sb.length() > 0 && CIStringUtils.isNotEmpty(oldString)) {
            int index = sb.indexOf(oldString);
            int oldStringLen = oldString.length();
            int padding = 0;
            if (contains(newString, oldString)) {
                padding = newString.length();
            }
            while (index != -1) {
                sb.replace(index, index + oldStringLen, newString);
                index = sb.indexOf(oldString, index + padding);
            }
        }
    }

    /**
     * Remplace la chaine "oldString" par la chaine "newString" dans le contenu du String passé en paramétre.
     *
     * @param str       le texte a traiter
     * @param oldString la chaine a remplacer
     * @param newString la chaine de remplacement.
     */
    public static String replace(final String str, final String oldString, final String newString) {
        String retour = str;
        if (CIStringUtils.isNotBlank(str) && CIStringUtils.isNotEmpty(oldString)) {
            StringBuilder sb = threadLocalSB.getCleanValue();
            sb.append(str);
            replace(sb, oldString, newString);
            retour = sb.toString();
        }
        return retour;
    }

    /**
     * Normalise une chaine : seulement des caracteres ascii 7bits, (suppression des accents, des caracteres speciaux,
     * mise en majuscule).
     *
     * @param s la chaine a normaliser
     * @return la chaine normalisee ou null si la chaine a normaliser etait nule
     */
    public static String normalize(final String s) {
        if (CIStringUtils.isNotBlank(s)) {
            int inputLength = s.length();
            char[] bufferOut = getCharBuffer(inputLength);
            int indexBufferOut = 0;

            for (int i = 0; i < inputLength; i++) {
                char c = s.charAt(i);
                if (c == CHAR_CODE_MIN1) { // On laisse passer les espaces
                    bufferOut[indexBufferOut++] = c;
                } else if ((c >= CHAR_REMPL_BLANC_MIN) && (c <= CHAR_REMPL_BLANC_MAX)) { // On remplace cette zone par
                    // des espaces
                    bufferOut[indexBufferOut++] = CHAR_ESPACE;
                } else if ((c > CHAR_REMPL_BLANC_MAX) && (c < CHAR_LOWERCASE_MIN)) { // On laisse passer
                    bufferOut[indexBufferOut++] = c;
                } else if ((c >= CHAR_LOWERCASE_MIN) && (c <= CHAR_LOWERCASE_MAX)) { // On converti en majuscules
                    bufferOut[indexBufferOut++] = (char) (c - CHAR_CODE_MIN1);
                } else if ((c > CHAR_LOWERCASE_MAX) && (c <= CHAR_CODE_MAX1)) { // On laisse passer
                    bufferOut[indexBufferOut++] = c;
                } else if ((c >= CHAR_CODE_MIN2) && (c <= CHAR_CODE_MAX2)) { // On normalize : suppression des
                    // caracteres bizarres et accents
                    bufferOut[indexBufferOut++] = NORMALIZE[c - CHAR_CODE_MIN2];
                }
            }
            return new String(bufferOut, 0, indexBufferOut);
        } else {
            return s;
        }
    }

    private static char[] getCharBuffer(final int length) {
        char[] cs = buffer.get();
        if (cs.length < length) {
            cs = new char[length];
            buffer.set(cs);
        }
        return cs;
    }

    /**
     * Formatte un libelle pour le mettre tout en minuscule sauf la premiere lettre.
     */
    public static String firstLetterToUpperCase(final String pLibelle) {
        StringBuilder sb = threadLocalSB.getCleanValue();
        String libelleFormat = "";
        if (CIStringUtils.isNotBlank(pLibelle)) {
            libelleFormat = pLibelle.trim();
            if (libelleFormat.length() > 1) {
                sb.append(libelleFormat.toUpperCase(), 0, 1);
                sb.append(libelleFormat.toLowerCase(), 1, libelleFormat.length());
                libelleFormat = sb.toString();
            } else {
                libelleFormat = libelleFormat.toUpperCase();
            }
        }
        return libelleFormat;
    }

    /**
     * Supprime les caractères donnés d'une chaine et passe en majuscule le caractere suivant.
     *
     * @param str       La chaine a traiter.
     * @param delimiter Le caractère a trouver.
     * @return La chaine traitée.
     */
    public static String capitalizeAfterEach(final String str, final char delimiter) {
        if (str == null || str.length() == 0) {
            return str;
        }
        int strLen = str.length();
        StringBuilder builder = threadLocalSB.getCleanValue();
        boolean capitalizeNext = false;
        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);
            if (ch == delimiter) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                builder.append(Character.toTitleCase(ch));
                capitalizeNext = false;
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * Concaténe plusieurs chaines en un seule via un {@link StringBuilder}. Aucun séparateur n'est ajouté entre les
     * sous-chaines. Si une sous-chaine est <code>null</code>, les 4 caractéres <code>"null"</code> sont concaténés.
     * Retourne une chaine vide si aucune sous-chaine n'est transmise en paramétre.
     * <p>
     * Note: Cette méthode doit étre seulement utilisée pour concaténer un nombre restreint de petites chaines, la
     * capacité du StringBuilder instancié étant seulement de 16.
     * </p>
     *
     * <pre>
     * concat(&quot;Hello &quot;, &quot;World&quot;, &quot;!&quot;) = &quot;Hello World!&quot;
     * concat(&quot;Hello &quot;, null, &quot;!&quot;)    = &quot;Hello null!&quot;
     * concat()                       = &quot;&quot;
     * </pre>
     *
     * @param lstSubstring les sous-chaines (ellipse).
     * @return la chaine résultante. Ne retourne jamais <code>null</code>.
     * @see StringBuilder#append(String)
     */
    public static String concat(final Object... lstSubstring) {
        return concat(null, lstSubstring);
    }

    /**
     * Concaténe plusieurs chaines en un seule via un {@link StringBuilder}. Aucun séparateur n'est ajouté entre les
     * sous-chaines. Si une sous-chaine est <code>null</code>, les 4 caractéres <code>"null"</code> sont concaténés.
     * Retourne une chaine vide si aucune sous-chaine n'est transmise en paramétre.
     * <p>
     * Si le StringBuilder passe en parametre est null, on en cree un de capacite 16.
     *
     * <pre>
     * concat(&quot;Hello &quot;, &quot;World&quot;, &quot;!&quot;) = &quot;Hello World!&quot;
     * concat(&quot;Hello &quot;, null, &quot;!&quot;)    = &quot;Hello null!&quot;
     * concat()                       = &quot;&quot;
     * </pre>
     *
     * @param lstSubstring les sous-chaines (ellipse).
     * @return la chaine résultante. Ne retourne jamais <code>null</code>.
     * @see StringBuilder#append(String)
     */
    public static String concat(final StringBuilder builder, final Object... lstSubstring) {
        StringBuilder builderTemp = builder;
        if (builderTemp == null) {
            builderTemp = threadLocalSB.getCleanValue();
        }
        for (Object obj : lstSubstring) {
            builderTemp.append(obj);
        }
        return builderTemp.toString();
    }

    /**
     * Supprime les diacritiques des lettres accentuees, ainsi que les ligatures d'une chaine.
     *
     * @param str la chaine initiale. Non modifiee par l'operation.
     * @return la chaine modifiee.
     */
    public static String supprimerAccents(final String str) {
        String resultat = null;
        if (str != null) {
            // Supprime les diacritiques sur les A
            resultat = replaceChars(str, "\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5",
                    "AAAAAAaaaaaa");
            // Supprime les diacritiques sur les C
            resultat = replaceChars(resultat, "\u00C7\u00E7", "Cc");
            // Supprime les diacritiques sur les E
            resultat = replaceChars(resultat, "\u00C8\u00C9\u00CA\u00CB\u00E8\u00E9\u00EA\u00EB", "EEEEeeee");
            // Supprime les diacritiques sur les I
            resultat = replaceChars(resultat, "\u00CC\u00CD\u00CE\u00CF\u00EC\u00ED\u00EE\u00EF", "IIIIiiii");
            // Supprime les diacritiques sur les O
            resultat = replaceChars(resultat, "\u00D2\u00D3\u00D4\u00D5\u00D6\u00F2\u00F3\u00F4\u00F5\u00F6",
                    "OOOOOooooo");
            // Supprime les diacritiques sur les U
            resultat = replaceChars(resultat, "\u00D9\u00DA\u00DB\u00DC\u00F9\u00FA\u00FB\u00FC", "UUUUuuuu");
            // Supprime les diacritiques sur les Y
            resultat = replaceChars(resultat, "\u00DD\u00FD\u00FF", "Yyy");
            StringBuilder sbResultat = threadLocalSB.getCleanValue();
            sbResultat.append(resultat);
            // Supprime les ligatures
            replace(sbResultat, "\u0152", "OE");
            replace(sbResultat, "\u0153", "oe");
            replace(sbResultat, "\u00C6", "AE");
            replace(sbResultat, "\u00E6", "ae");
            resultat = sbResultat.toString();
        }
        return resultat;
    }

    /**
     * Permet de savoir si deux chaines sont égales meme si l'une est null et l'autre vide.
     *
     * @return true  si les deux chaines sont égales meme si l'une est null et l'autre vide.
     */
    public static boolean isEqualsIgnoreNull(final String chaine1, final String chaine2) {
        //elles sont egales si :
        // chaine1 == null && chaine2 ==null
        // || chaine 1="" && chaine2="";
        // || chaine 1=null && chaine2="";
        // || chaine 1="" && chaine2=null;
        // || chaine.equals(chaine2)

        return CIStringUtils.isEmpty(chaine1) && CIStringUtils.isEmpty(chaine2) || CIStringUtils.equals(chaine1, chaine2);
    }

    /**
     * <p>Joint les &eacute;l&eacute;ments du tableau en une cha&#238;ne de caract&egrave;res s&eacute;par&eacute;s par le
     * caract&egrave;re s&eacute;parateur.</p>
     *
     * <p>Le caract&egrave;re s&eacute;parateur n'est ajout&eacute; ni en d&eacute;but ni en fin de cha&#238;ne.
     * Les objets Null ou cha&#238;nes vides sont repr&eacute;sent&eacute;s par des cha&#238;nes vides.</p>
     *
     * <pre>
     * CIStringUtils.join(null, *)               = null
     * CIStringUtils.join([], *)                 = ""
     * CIStringUtils.join([null], *)             = ""
     * CIStringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * CIStringUtils.join([null, "", "a"], ';')  = ";;a"
     * </pre>
     *
     * @param array     le tableau de valeur &agrave; fusionner, peut &ecirc;tre null
     * @param separator la carat&egrave;re s&eacute;parateur &agrave; utiliser
     * @return La String fusionn&eacute;e, &lt;code&gt;null&lt;/code&gt; si le tableau en entr&eacute;e est null
     */
    public static String join(final Object[] array, final char separator) {

        if (array == null) {
            return null;
        }

        StringBuilder builder = threadLocalSB.getCleanValue();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                builder.append(separator);
            }
            if (array[i] != null) {
                builder.append(array[i]);
            }
        }

        return builder.toString();
    }

    /**
     * <p>Joins the elements of the provided <code>Collection</code> into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     *
     * <p>See the examples here: {@link #join(Object[], char)}. </p>
     *
     * @param iterator  the <code>Iterator</code> on collection of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, <code>null</code> if null iterator input
     */
    public static String join(final Iterator<?> iterator, final char separator) {
        return join(iterator, String.valueOf(separator));
    }

    /**
     * <p>Joins the elements of the provided <code>Collection</code> into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     *
     * <p>See the examples here: {@link #join(Object[], char)}. </p>
     *
     * @param iterator  the <code>Iterator</code> on collection of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, <code>null</code> if null iterator input
     */
    public static String join(final Iterator<?> iterator, final String separator) {
        return join(iterator, separator, null);
    }

    /**
     * <p>Joins the elements of the provided <code>Collection</code> into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     *
     * <p>See the examples here: {@link #join(Object[], char)}. </p>
     *
     * @param iterator  the <code>Iterator</code> on collection of values to join together, may be null
     * @param separator the separator character to use
     * @param prefixe,  add prefixe toO object.
     * @return the joined String, <code>null</code> if null iterator input
     */
    public static String join(final Iterator<?> iterator, final String separator, final String prefixe) {
        if (iterator == null) {
            return null;
        }

        StringBuilder builder = threadLocalSB.getCleanValue();
        int index = 0;
        while (iterator.hasNext()) { //NOPMD bien voulu ici
            Object object = iterator.next();
            if (index > 0 && separator != null) {
                builder.append(separator);
            }

            if (CIStringUtils.isNotBlank(prefixe)) {
                builder.append(prefixe).append(".");
            }
            if (object != null) {
                builder.append(object);
            }
            index++;
        }

        return builder.toString();
    }

    /**
     * <p>Joins the elements of the provided {@code Iterable} into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     *
     * <p>See the examples here: {@link #join(Object[], char)}. </p>
     *
     * @param iterable  the {@code Iterable} providing the values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null iterator input
     * @since 2.3
     */
    public static String join(final Iterable<?> iterable, final char separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

    /**
     * <p>Joins the elements of the provided {@code Iterable} into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list.
     * A {@code null} separator is the same as an empty String ("").</p>
     *
     * <p>See the examples here: {@link #join(Object[], String)}. </p>
     *
     * @param iterable  the {@code Iterable} providing the values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null iterator input
     * @since 2.3
     */
    public static String join(final Iterable<?> iterable, final String separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

}
