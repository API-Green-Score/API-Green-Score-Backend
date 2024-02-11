package fr.pagesjaunes.socletechnique.lang.utils;

/**
 * Classe utilitaire pour les manipulations des énumérations.
 *
 * @author mbrossais
 */
public final class CIEnumUtils {

    /**
     * Constructeur.
     */
    private CIEnumUtils() {
        // noop
    }

    /**
     * Détermine si une constante fait partie d'une énumération.
     *
     * @param enumClass    la classe d'énumération.
     * @param constantName le nom de la constante.
     * @return <code>true</code> si la constante existe; <code>false</code> sinon.
     */
    public static <T extends Enum<T>> boolean isEnumConstantExists(final Class<T> enumClass, final String constantName) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.toString().equals(constantName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne un des constantes d'une énumération.
     *
     * @param enumClass    la classe d'énumération.
     * @param constantName le nom de la constante.
     * @return la constante; ou <code>null</code> si la constante n'existe pas.
     */
    public static <T extends Enum<T>> T getEnumConstant(final Class<T> enumClass, final String constantName) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.toString().equals(constantName)) {
                return enumConstant;
            }
        }
        return null;
    }

}
