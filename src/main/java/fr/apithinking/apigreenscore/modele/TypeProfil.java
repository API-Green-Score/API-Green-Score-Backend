package fr.apithinking.apigreenscore.modele;

import com.fasterxml.jackson.annotation.JsonCreator;
import fr.pagesjaunes.socletechnique.lang.utils.CIEnumUtils;

/**
 * @author jmleclercqnewpj
 */
public enum TypeProfil {
    CONTRIB,
    SANTE;

    /** Récupère le type à partir de l'entité. */
    @JsonCreator
    public static TypeProfil getTypeFromString(final String pProfil) {
        TypeProfil result = null;
        // Si aucun profil de renseigné, CONTRIB par défaut.
        if (pProfil == null) {
            result = TypeProfil.CONTRIB;
        } else { // Sinon on regarde si la valeur renseignée est OK.
            result = CIEnumUtils.isEnumConstantExists(TypeProfil.class, pProfil) ? TypeProfil.valueOf(pProfil) : null;
        }
        return result;
    }
}
