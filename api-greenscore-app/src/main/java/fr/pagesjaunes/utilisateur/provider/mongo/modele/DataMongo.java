package fr.pagesjaunes.utilisateur.provider.mongo.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.pagesjaunes.utilisateur.provider.mongo.modele.commun.CommunMongo;
import fr.pagesjaunes.utilisateur.provider.mongo.modele.pjprofil.PjProfilMongo;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
public class DataMongo {

    @JsonProperty(FieldName.PJ_PROFIL)
    @Field(FieldName.PJ_PROFIL)
    private PjProfilMongo pjProfil;

    @JsonProperty(FieldName.COMMUN)
    @Field(FieldName.COMMUN)
    private CommunMongo commun;

    @UtilityClass
    public static class FieldName {
        public static final String PJ_PROFIL = "pj_profil";

        public static final String COMMUN = "commun";

    }

}
