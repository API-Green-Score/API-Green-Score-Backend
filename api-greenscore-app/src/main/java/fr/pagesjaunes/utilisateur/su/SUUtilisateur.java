package fr.pagesjaunes.utilisateur.su;

import fr.pagesjaunes.utilisateur.modele.Utilisateur;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public interface SUUtilisateur {

    /**
     * Récupération d'un utilisateur.
     * @param pIdUtilisateur l'id du user
     */
    Utilisateur getUser(
            @NotNull(message = "L'identifiant de l'utilisateur doit être renseigné.") String pIdUtilisateur);

    /**
     * Récupération d'un utilisateur.
     * @param pExternalUserId l'id externe de l'utilisateur
     */
    Utilisateur getUserByExternalId(
            @NotNull(message = "L'identifiant externe de l'utilisateur doit être renseigné.") String pExternalUserId);

    /**
     * Récupération d'un utilisateur.
     * @param pEmail
     * @return l'utilisateur correspondant
     */
    Utilisateur getUserByEmail(@Email String pEmail);

}
