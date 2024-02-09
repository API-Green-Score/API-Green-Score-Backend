package fr.apithinking.apigreenscore.provider.mongo.modele.commun;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.apithinking.apigreenscore.modele.AppareilOrigine;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
public class CommunMongo {

    @JsonProperty(FieldName.CGU_DATE_ACCEPTATION)
    @Field(FieldName.CGU_DATE_ACCEPTATION)
    private Date dateAcceptationCgus;

    @JsonProperty(FieldName.NEWSLETTER)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.NEWSLETTER)
    private Boolean newsletter;

    @JsonProperty(FieldName.PARTAGE_DONNEES)
    @Field(FieldName.PARTAGE_DONNEES)
    private Boolean partageDonnees;

    @JsonProperty(FieldName.APPAREIL)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.APPAREIL)
    private AppareilOrigine appareilOrigine;

    // TODO DDC : vu avec BRI / FDE => notion de profils jamais utilisés
    // ==> nettoyage de BD à prévoir
//    @JsonProperty(FieldName.CLIC_RDV)
//    @Field(FieldName.CLIC_RDV)
//    private ClicRdvMongo clicrdv;
//
//    @JsonProperty(FieldName.CHRONO_RESTO)
//    @Field(FieldName.CHRONO_RESTO)
//    private ChronoRestoMongo chronoresto;
//
//    @JsonProperty(FieldName.HAMAK)
//    @Field(FieldName.HAMAK)
//    private HamakMongo hamak;
//
//    @JsonProperty(FieldName.PAGESJAUNES)
//    @Field(FieldName.PAGESJAUNES)
//    private PagesJaunesMongo pagesjaunes;

    @JsonProperty(FieldName.COMMENTAIRE_DEACTIVATION)
    @Field(FieldName.COMMENTAIRE_DEACTIVATION)
    private String commentaireDesactivation;

    @JsonProperty(FieldName.ID_STAT)
    @Field(FieldName.ID_STAT)
    private String idStatistique;

    // TODO DDC : à voir si pas deprecated avec FDE / BRI car
    // deprecated dans Utilisateur
    @JsonProperty(FieldName.MDP)
    @Field(FieldName.MDP)
    private InfosMdpMongo infosMdp;

    @JsonProperty(FieldName.ID_UTILISATEUR_EXTERNE)
    @Field(FieldName.ID_UTILISATEUR_EXTERNE)
    private String idUtilisateurExterne;

    @UtilityClass
    public static class FieldName {
        public static final String CGU_DATE_ACCEPTATION = "cgu_date_acceptation";

        public static final String NEWSLETTER = "newsletter";

        public static final String PARTAGE_DONNEES = "partage_donnees";

        public static final String APPAREIL = "appareil";

        public static final String CLIC_RDV = "clic_rdv";

        public static final String CHRONO_RESTO = "chrono_resto";

        public static final String HAMAK = "hamak";

        public static final String PAGESJAUNES = "pagesjaunes";

        public static final String COMMENTAIRE_DEACTIVATION = "commentaire_desactivation";

        public static final String ID_STAT = "id_stat";

        public static final String MDP = "mdp";

        public static final String ID_UTILISATEUR_EXTERNE = "id_utilisateur_externe";

    }
}
