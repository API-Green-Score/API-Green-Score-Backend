package fr.pagesjaunes.utilisateur.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * Données de modération du profil Pages Jaunes.
 */
@Data
public class Moderation {

    @JsonProperty("date_mise_moderation")
    private Date dateMiseModeration;

    @JsonProperty("date_derniere_moderation")
    private Date dateDerniereModeration;

    @JsonProperty("moderateur")
    private String moderateur;

    @JsonProperty("pseudo_en_moderation")
    private Boolean pseudoEnModeration;

    @JsonProperty("pseudo_raison_refus")
    private String pseudoRaisonRefus;

    @JsonProperty("pseudo_precedent")
    private String pseudoPrecedent;

    @JsonProperty("pseudo_a_moderer")
    private String pseudoAModerer;

    @JsonProperty("avatar_en_moderation")
    private Boolean avatarEnModeration;

    @JsonProperty("avatar_raison_refus")
    private String avatarRaisonRefus;

    @JsonProperty("chemin_avatar_precedent")
    private String cheminAvatarPrecedent;

    @JsonProperty("chemin_avatar_a_moderer")
    private String cheminAvatarAModerer;

}
