package fr.apithinking.apigreenscore.st;

import fr.apithinking.apigreenscore.modele.Utilisateur;

/**
 * Interface du service technique de mock Gigya.
 * @author ltroquetnewpj
 */
public interface STMockUtilisateur {

    /**
     * Récupération d'un utilisateur.
     * @param pIdUtilisateur Identifiant technique de l'utilisateur, éventuellement suivi d'un selecteur de profil (eg: 2114RSR@2)
     * @return l'utilisateur correspondant
     */
    Utilisateur recupererUtilisateurMock(String pIdUtilisateur);

}
