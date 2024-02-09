package fr.pagesjaunes.utilisateur.provider.mongo.modele.pjprofil;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
public class ModerationMongo {

    @JsonProperty(FieldName.DATE_MISE_MODERATION)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.DATE_MISE_MODERATION)
    private Date dateMiseModeration;

    @JsonProperty(FieldName.DATE_DERNIERE_MODERATION)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.DATE_DERNIERE_MODERATION)
    private Date dateDerniereModeration;

    @JsonProperty(FieldName.MODERATEUR)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.MODERATEUR)
    private String moderateur;

    @JsonProperty(FieldName.PSEUDO_EN_MODERATION)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.PSEUDO_EN_MODERATION)
    private Boolean pseudoEnModeration;

    @JsonProperty(FieldName.PSEUDO_RAISON_REFUS)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.PSEUDO_RAISON_REFUS)
    private String pseudoRaisonRefus;

    @JsonProperty(FieldName.PSEUDO_PRECEDENT)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.PSEUDO_PRECEDENT)
    private String pseudoPrecedent;

    @JsonProperty(FieldName.PSEUDO_A_MODERER)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.PSEUDO_A_MODERER)
    private String pseudoAModerer;

    @JsonProperty(FieldName.AVATAR_EN_MODERATION)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.AVATAR_EN_MODERATION)
    private Boolean avatarEnModeration;

    @JsonProperty(FieldName.AVATAR_RAISON_REFUS)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.AVATAR_RAISON_REFUS)
    private String avatarRaisonRefus;

    @JsonProperty(FieldName.CHEMIN_AVATAR_PRECEDENT)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.CHEMIN_AVATAR_PRECEDENT)
    private String cheminAvatarPrecedent;

    @JsonProperty(FieldName.CHEMIN_AVARTAR_A_MODERER)
    @JsonInclude(Include.NON_NULL)
    @Field(FieldName.CHEMIN_AVARTAR_A_MODERER)
    private String cheminAvatarAModerer;

    @UtilityClass
    public static class FieldName {
        public static final String DATE_MISE_MODERATION = "date_mise_moderation";

        public static final String DATE_DERNIERE_MODERATION = "date_derniere_moderation";

        public static final String MODERATEUR = "moderateur";

        public static final String PSEUDO_EN_MODERATION = "pseudo_en_moderation";

        public static final String PSEUDO_RAISON_REFUS = "pseudo_raison_refus";

        public static final String PSEUDO_PRECEDENT = "pseudo_precedent";

        public static final String PSEUDO_A_MODERER = "pseudo_a_moderer";

        public static final String AVATAR_EN_MODERATION = "avatar_en_moderation";

        public static final String AVATAR_RAISON_REFUS = "avatar_raison_refus";

        public static final String CHEMIN_AVATAR_PRECEDENT = "chemin_avatar_precedent";

        public static final String CHEMIN_AVARTAR_A_MODERER = "chemin_avatar_a_moderer";

    }
}
