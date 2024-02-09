package fr.pagesjaunes.utilisateur.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Objet utilisateur de l'api-utilisateur.
 *
 * @author jbpirauxnewpj
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur {

    @Builder.Default
    private final MetadonneesUtilisateur metadonnes = new MetadonneesUtilisateur();

    @JsonProperty("id_utilisateur")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("pseudo")
    private String pseudo;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("etat_compte")
    private TypeEtatCompte etatCompte;

    @JsonProperty("profil_pj_complet")
    private boolean profilPjComplet;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("prenom")
    private String prenom;

    @JsonProperty("date_naissance")
    private Date dateNaissance;

    @JsonProperty("civilite")
    private TypeCivilite civilite;

    @JsonProperty("adresse")
    private String adresse;

    @JsonProperty("id_localite")
    private String idLocalite;

    /**
     * Le chemin vers l'avatar public (ie modéré positivement) dans le CSM.
     */
    @JsonProperty("chemin_avatar")
    private String cheminAvatar;

    @JsonProperty("numero_mobile")
    private String numeroMobile;

    @JsonProperty("inscrit_newsletter")
    private Boolean inscritNewsletter;

    @JsonProperty("cgus")
    private List<Cgu> listeCgus;

    @JsonProperty("appareil_origine")
    private AppareilOrigine appareilOrigine;

    @JsonProperty("date_creation_profil")
    private Date dateCreationProfil;

    @JsonProperty("date_creation_compte")
    private Date dateCreationCompte;

    @JsonProperty("origine_creation")
    private OrigineCreation origineCreation;

    // TODO DDC : à voir BRI / FDE si toujours besoin car plus de notion de profil
//    @JsonProperty("profil_pj_existe")
//    private boolean profilPjExiste;

    @JsonProperty("ville_forcee")
    private Boolean villeForcee;

    @JsonProperty("origine_creation_pseudo")
    private OrigineCreationPseudo origineCreationPseudo;

    @JsonProperty("en_moderation")
    private Boolean enModeration;

    @JsonProperty("moderation")
    private Moderation moderation;

    @JsonProperty("reseaux_sociaux_associes")
    private List<OrigineCreation> reseauSociauxAssocies;

    @JsonProperty("partage_donnees")
    private Boolean partageDonnees;

    @JsonProperty("systeme_origine")
    private SystemeOrigine systemeOrigine;

    // TODO DDC : vu avec BRI => notion de profils jamais utilisés
    // ==> nettoyage de BD à prévoir
//    @JsonProperty("profils_partenaires")
//    private List<EntiteSolocal> listeProfilPartenaires;

    @JsonProperty("commentaire_desactivation")
    private String commentaireDesactivation;

    @JsonProperty("id_stat")
    private String idStatistique;

    @JsonProperty("code_origine_sollicitation")
    private String codeOrigineSollicitation;

    @JsonProperty("profils_compte")
    private List<TypeProfil> listeProfilsCompte;

    @JsonProperty("numero_mobile_verifie")
    private Boolean numeroMobileVerifie;

    @JsonProperty("parcours_origine_creation")
    private String parcoursOrigineCreation;

    @JsonProperty("id_utilisateur_externe")
    private String idUtilisateurExterne;

    @JsonProperty("last_activite_compte")
    private Date lastActiviteCompte;

    @JsonProperty("registration")
    private Registration registration;

    private Date lastLoginCompte;

}
