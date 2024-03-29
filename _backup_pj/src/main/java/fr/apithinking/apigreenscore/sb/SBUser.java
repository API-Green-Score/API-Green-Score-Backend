package fr.apithinking.apigreenscore.sb;

import fr.apithinking.apigreenscore.modele.Utilisateur;

public interface SBUser {

    /**
     * Get user by ID
     * @param utilisateurId
     * @return
     */
    Utilisateur getUser(String utilisateurId);

    /**
     * Get user by external identifier.
     *
     * @param pExternalUserId
     * @return the corresponding user
     */
    Utilisateur getUserByExternalId(String pExternalUserId);

    /**
     * Get user by email.
     *
     * @param pEmail
     * @return the corresponding user
     */
    Utilisateur getUserByEmail(String pEmail);

}
