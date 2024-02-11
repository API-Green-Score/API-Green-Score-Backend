package fr.pagesjaunes.socletechnique.lang.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Classe utilitaire pour les manipulations de tableaux.
 *
 * @author mmauny
 */
public final class CIArrayUtils extends ArrayUtils {

    private CIArrayUtils() {
    }

    /**
     * Comparaison de 2 tableaux de {@link String}.
     * Retourne true si les 2 tableaux contiennent les m&ecirc;mes &eacute;lements, sans prise en compte de l'ordre.
     * Retourne true si les 2 tableaux sont null.
     *
     * <pre>
     * isSameStringArray(null, null)                                = true
     * isSameStringArray(["abc","def","ghi"], null)                 = false
     * isSameStringArray(["abc","def","ghi"], ["abc","def","ghi"])  = true
     * isSameStringArray(["abc","def","ghi"], ["def","abc","ghi"])  = true
     * isSameStringArray(["abc","def","ghi"], ["def"])              = false
     * </pre>
     *
     * @param array1 Tableau 1
     * @param array2 Tableau 2
     * @return true si les 2 tableaux sont identiques, false sinon.
     */
    public static boolean isSameStringArray(final String[] array1, final String[] array2) {
        if (array1 == null) {
            return array2 == null;
        }
        if (array2 == null) {
            return false;
        }
        if (array1.length != array2.length) {
            return false;
        }
        for (String element : array1) {
            if (!contains(array2, element)) {
                return false;
            }
        }
        return true;
    }
}
