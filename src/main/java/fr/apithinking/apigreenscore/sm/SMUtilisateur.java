package fr.apithinking.apigreenscore.sm;

import fr.apithinking.apigreenscore.modele.Utilisateur;

/**
 * Métier de l'utilisateur.
 * @author jbpirauxnewpj
 */
public interface SMUtilisateur {

    /**
     * Récupération d'un utilisateur.
     * @param pIdUtilisateur
     * @return l'utilisateur correspondant
     */
    Utilisateur getUser(String pIdUtilisateur);

    /**
     * Recuperation d'un utilisateur.
     *
     * @param pExternalUserId identifiant externe de l'utilisateur
     * @return utilisateur
     */
    Utilisateur getUserByExternalId(String pExternalUserId);

    /**
     * Récupération d'un utilisateur.
     * @param pEmail
     * @return l'utilisateur correspondant
     */
    Utilisateur getUserByEmail(String pEmail);

}
