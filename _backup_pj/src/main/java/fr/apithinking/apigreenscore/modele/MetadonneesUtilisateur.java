package fr.apithinking.apigreenscore.modele;

import lombok.Data;

import java.util.Date;

/**
 * Données qui ne font pas partie du modèle utilisateur mais nous facilitent la vie.
 */
@Data
public class MetadonneesUtilisateur {

    /**
     * Est-ce que les CGU pages jaunes sont acceptées.
     */
    private boolean cguPagesJaunesAcceptees;

    /**
     * Date d'acceptation des CGU globales.
     */
    private Date dateCguGlobales;

    /**
     * Date d'acceptation des CGU pages jaunes.
     */
    private Date dateCguPagesJaunes;

    /**
     * Mot de passe décrypté.
     */
    private transient String motDePasseClair;

}
