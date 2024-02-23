package fr.apithinking.apigreenscore.provider.mongo.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.apithinking.apigreenscore.modele.OrigineCreation;
import fr.apithinking.apigreenscore.modele.TypeEtatCompte;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Data
@Builder
@FieldNameConstants
@Document(UserMongo.COLLECTION)
@TypeAlias(UserMongo.COLLECTION)
public class UserMongo {

    // TODO DDC : voir avce FDE si ménage dans les champs

    public static final String COLLECTION = "Users";

    @Id
    @JsonProperty(FieldName.ID)
    @Field(name = FieldName.ID, targetType = FieldType.OBJECT_ID)
    private String id;

    @JsonProperty(FieldName.ID_AUTH_PARTNER)
    @Field(FieldName.ID_AUTH_PARTNER)
    private String idAuthPartner;

    @JsonProperty(FieldName.EMAIL)
    @Field(FieldName.EMAIL)
    private String email;

    @JsonProperty(FieldName.DATE_CREATION_COMPTE)
    @Field(FieldName.DATE_CREATION_COMPTE)
    private Date dateCreationCompte;

    @JsonProperty(FieldName.LAST_UPDATED_TIMESTAMP)
    @Field(FieldName.LAST_UPDATED_TIMESTAMP)
    private Long lastUpdatedTimestamp;

    @JsonProperty(FieldName.LAST_LOGIN_COMPTE)
    @Field(FieldName.LAST_LOGIN_COMPTE)
    private Date lastLoginCompte;

    @JsonProperty(FieldName.DATA)
    @Field(FieldName.DATA)
    private DataMongo data;

    @JsonProperty(FieldName.ETAT_COMPTE)
    @Field(FieldName.ETAT_COMPTE)
    private TypeEtatCompte etatCompte;

    @Singular(value = "reseauSocialAssocie")
    @JsonProperty(FieldName.RESEAUX_SOCIAUX_ASSOCIES)
    @Field(FieldName.RESEAUX_SOCIAUX_ASSOCIES)
    private List<OrigineCreation> reseauSociauxAssocies; // TODO DDC : renommage avec x

    @JsonProperty(FieldName.REGISTRATION)
    @Field(FieldName.REGISTRATION)
    private RegistrationMongo registration;

    @JsonProperty(FieldName.CLASS)
    @Field(FieldName.CLASS)
    private String className;

    @UtilityClass
    public static class FieldName {
        public static final String ID = "_id";

        public static final String ID_AUTH_PARTNER = "id_auth_partner";

        public static final String ETAT_COMPTE = "etat_compte";

        public static final String RESEAUX_SOCIAUX_ASSOCIES = "reseaux_sociaux_associes";

        public static final String DATE_CREATION_COMPTE = "date_creation_compte";

        public static final String LAST_UPDATED_TIMESTAMP = "last_updated_timestamp";

        public static final String LAST_LOGIN_COMPTE = "last_login_compte";

        public static final String EMAIL = "email";

        public static final String DATA = "data";

        public static final String REGISTRATION = "registration";

        public static final String CLASS = "_class";

    }

}
