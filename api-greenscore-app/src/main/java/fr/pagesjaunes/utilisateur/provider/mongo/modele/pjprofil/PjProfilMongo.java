package fr.pagesjaunes.utilisateur.provider.mongo.modele.pjprofil;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.pagesjaunes.utilisateur.modele.OrigineCreation;
import fr.pagesjaunes.utilisateur.modele.OrigineCreationPseudo;
import fr.pagesjaunes.utilisateur.modele.SystemeOrigine;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
public class PjProfilMongo {

    @JsonProperty(FieldName.PSEUDO)
    @Field(FieldName.PSEUDO)
    private String pseudo;

    @JsonProperty(FieldName.NOM)
    @Field(FieldName.NOM)
    private String nom;

    @JsonProperty(FieldName.PRENOM)
    @Field(FieldName.PRENOM)
    private String prenom;

    @JsonProperty(FieldName.DATE_NAISSANCE)
    @Field(FieldName.DATE_NAISSANCE)
    private String dateNaissance;

    @JsonProperty(FieldName.CIVILITE)
    @Field(FieldName.CIVILITE)
    private String civilite;

    @JsonProperty(FieldName.ADRESSE)
    @Field(FieldName.ADRESSE)
    private String adresse;

    @JsonProperty(FieldName.ID_LOCALITE)
    @Field(FieldName.ID_LOCALITE)
    private String idLocalite;

    @JsonProperty(FieldName.CHEMIN_AVATAR)
    @Field(FieldName.CHEMIN_AVATAR)
    private String cheminAvatar;

    @JsonProperty(FieldName.NUMERO_MOBILE)
    @Field(FieldName.NUMERO_MOBILE)
    private String numeroMobile;

    @JsonProperty(FieldName.CGU_DATE_ACCEPTATION)
    @Field(FieldName.CGU_DATE_ACCEPTATION)
    private Date dateAcceptationCgus;

    @JsonProperty(FieldName.MODERATION)
    @Field(FieldName.MODERATION)
    private ModerationMongo moderation;

    @JsonProperty(FieldName.NEWSLETTER)
    @Field(FieldName.NEWSLETTER)
    private Boolean inscritNewsletter;

    @JsonProperty(FieldName.DATE_CREATION)
    @Field(FieldName.DATE_CREATION)
    private Date dateCreation;

    @JsonProperty(FieldName.ORIGINE_CREATION)
    @Field(FieldName.ORIGINE_CREATION)
    private OrigineCreation origineCreation;

    @JsonProperty(FieldName.VILLE_FORCEE)
    @Field(FieldName.VILLE_FORCEE)
    private Boolean villeForcee;

    @JsonProperty(FieldName.ORIGINE_CREATION_PSEUDO)
    @Field(FieldName.ORIGINE_CREATION_PSEUDO)
    private OrigineCreationPseudo origineCreationPseudo;

    @JsonProperty(FieldName.SYSTEME_ORIGINE)
    @Field(FieldName.SYSTEME_ORIGINE)
    private SystemeOrigine systemeOrigine;

    @JsonProperty(FieldName.CODE_ORIGINE_SOLLICITATION)
    @Field(FieldName.CODE_ORIGINE_SOLLICITATION)
    private String codeOrigineSollicitation;

    @JsonProperty(FieldName.NUMERO_MOBILE_VERIFIE)
    @Field(FieldName.NUMERO_MOBILE_VERIFIE)
    private Boolean numeroMobileVerifie;

    @JsonProperty(FieldName.PARCOURS_ORIGINE_CREATION)
    @Field(FieldName.PARCOURS_ORIGINE_CREATION)
    private String parcoursOrigineCreation;

    @JsonProperty(FieldName.LAST_ACTIVITE_COMPTE)
    @Field(FieldName.LAST_ACTIVITE_COMPTE)
    private Date lastActiviteCompte;

    @UtilityClass
    public static class FieldName {
        public static final String PSEUDO = "pseudo";

        public static final String NOM = "nom";

        public static final String PRENOM = "prenom";

        public static final String DATE_NAISSANCE = "date_naissance";

        public static final String CIVILITE = "civilite";

        public static final String ADRESSE = "adresse";

        public static final String ID_LOCALITE = "id_localite";

        public static final String CHEMIN_AVATAR = "chemin_avatar";

        public static final String NUMERO_MOBILE = "numero_mobile";

        public static final String CGU_DATE_ACCEPTATION = "cgu_date_acceptation";

        public static final String MODERATION = "moderation";

        public static final String NEWSLETTER = "newsletter";

        public static final String DATE_CREATION = "date_creation";

        public static final String ORIGINE_CREATION = "origine_creation";

        public static final String VILLE_FORCEE = "ville_forcee";

        public static final String ORIGINE_CREATION_PSEUDO = "origine_creation_pseudo";

        public static final String SYSTEME_ORIGINE = "systeme_origine";

        public static final String CODE_ORIGINE_SOLLICITATION = "code_origine_sollicitation";

        public static final String NUMERO_MOBILE_VERIFIE = "numero_mobile_verifie";

        public static final String PARCOURS_ORIGINE_CREATION = "parcours_origine_creation";

        public static final String LAST_ACTIVITE_COMPTE = "last_activite_compte";

    }
}
