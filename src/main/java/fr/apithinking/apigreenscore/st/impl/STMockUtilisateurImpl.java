package fr.apithinking.apigreenscore.st.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.apithinking.apigreenscore.modele.Utilisateur;
import fr.apithinking.apigreenscore.st.STMockUtilisateur;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Service technique de mock utilisateur.
 * @author ltroquetnewpj
 */
@Slf4j
@Component
public class STMockUtilisateurImpl implements STMockUtilisateur {

    /**
     * Mapping des utilisateurs mockés.
     * La clé de la map est l'identifiant du profil mocké, passé à droite du @ dans l'idUtilisateur
     * La valeur de la map est le chemin vers le fichier json du profil mocké.
     */
    private static final Map<String, String> UTILISATEURS_MOCK = new HashMap<>();

    /**
     * Utilisateur mock renvoyé par défaut si l'identifiant utilisateur ne contient pas le sélecteur "@"
     */
    private static final String PROFIL_MOCK_DEFAUT = "1";

    private static final String PROFIL_MAIL_DYNAMIQUE = "8";

    static {
        // Profil "utilisateur complet".
        UTILISATEURS_MOCK.put(PROFIL_MOCK_DEFAUT, "/mock/utilisateurComplet.json");

        // Profil "utilisateur sans profil PagesJaunes".
        UTILISATEURS_MOCK.put("2", "/mock/utilisateurSansProfil.json");

        // Profil "utilisateur en modération".
        UTILISATEURS_MOCK.put("3", "/mock/utilisateurEnModeration.json");

        // Profil "utilisateur en première modération".
        UTILISATEURS_MOCK.put("4", "/mock/utilisateurEnPremiereModeration.json");

        // Profil "utilisateur en attente d'activation".
        UTILISATEURS_MOCK.put("5", "/mock/utilisateurEnAttenteActivation.json");

        // Profil "utilisateur désactivé".
        UTILISATEURS_MOCK.put("6", "/mock/utilisateurDesactive.json");

        // Profil "utilisateur santé complet".
        UTILISATEURS_MOCK.put("7", "/mock/utilisateurSanteComplet.json");

        // Profil "utilisateur complet avec mail dynamique (ajout id utilisateur comme prefixe dans le mail)".
        UTILISATEURS_MOCK.put(PROFIL_MAIL_DYNAMIQUE, "/mock/utilisateurComplet.json");
    }

    private ObjectMapper objectMapper;

    public STMockUtilisateurImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Utilisateur recupererUtilisateurMock(final String pIdUtilisateur) {
        LOGGER.debug("-->recupererUtilisateurMock({})", pIdUtilisateur);

        String profilSelectionne = recupereProfilSelectionne(pIdUtilisateur, "+");

        return recupereUtilisateurMocke(profilSelectionne, pIdUtilisateur, "+");
    }

    /**
     * Récupération du profil spécifique
     * @param pIdUtilisateur ID utilisateur contenant un potentiel profil
     * @param pSeparateur separateur si presence d'un profil specifique
     * @return profil
     */
    private String recupereProfilSelectionne(String pIdUtilisateur, String pSeparateur) {
        String profilSelectionne = StringUtils.substringAfter(pIdUtilisateur, pSeparateur);

        if (profilSelectionne.isEmpty()) {
            profilSelectionne = PROFIL_MOCK_DEFAUT;
        } else {
            if (!UTILISATEURS_MOCK.containsKey(profilSelectionne)) {
                LOGGER.warn("Le profil sélectionnné {} pour idUtilisateur={} n'existe pas, utilisation du profil par défaut {}",
                        profilSelectionne, pIdUtilisateur, PROFIL_MOCK_DEFAUT);
                profilSelectionne = PROFIL_MOCK_DEFAUT;
            }
        }

        return profilSelectionne;
    }

    /**
     * Récupération de l'utilisateur mocké en fonction du profil
     * @param pProfilSelectionne profil utilisé pour mocké l'utilisateur
     * @param pIdUtilisateur ID utilisateur contenant un potentiel profil
     * @param pSeparateur separateur si presence d'un profil specifique
     * @return utilisateur mocké
     */
    private Utilisateur recupereUtilisateurMocke(String pProfilSelectionne, String pIdUtilisateur, String pSeparateur) {
        final String cheminMock = UTILISATEURS_MOCK.get(pProfilSelectionne);
        Utilisateur utilisateur = creerMockObjet(cheminMock, Utilisateur.class);
        utilisateur.setId(pIdUtilisateur);
        utilisateur.setIdUtilisateurExterne(pIdUtilisateur + "_ext");

        if (PROFIL_MAIL_DYNAMIQUE.equals(pProfilSelectionne)) {
            String idUtilisateur = StringUtils.substringBefore(pIdUtilisateur, pSeparateur);
            utilisateur.setEmail(idUtilisateur + "-" + utilisateur.getEmail());
        }

        return utilisateur;
    }

    /**
     * Methode permettant de retourne un objet a partir d'un flux JSON dont le chemin est fourni en parametre.
     * @param pCheminFichierJSON chemin du fichier JSON a charger
     * @param pClass type de l'objet du lequel le flux doit etre converti
     * @return Objet converti a partir du flux JSON
     */
    protected <T> T creerMockObjet(final String pCheminFichierJSON, final Class<T> pClass) {
        LOGGER.debug("-->creerMockObjet({}, {})", pCheminFichierJSON, pClass.getName());

        InputStream is = this.getClass().getResourceAsStream(pCheminFichierJSON);

        if (is != null) {
            try {
                return objectMapper.readValue(is, pClass);
            } catch (IOException e) {
                LOGGER.error("Impossible de trouver le fichier json '{}' pour creer le mock : {}", pCheminFichierJSON, e.getMessage());
                throw new UncheckedIOException(e);
            }
        }

        LOGGER.error("Impossible de trouver le fichier json '{}' pour creer le mock.", pCheminFichierJSON);
        return null;
    }

}
