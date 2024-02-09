package fr.pagesjaunes.utilisateur.provider.mongo.modele.commun;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
public class InfosMdpMongo {

    @JsonProperty(FieldName.MDP_TYPE)
    @Field(FieldName.MDP_TYPE)
    private String type;

    @JsonProperty(FieldName.MDP_DATE_MODIF)
    @Field(FieldName.MDP_DATE_MODIF)
    private Date dateModif;

    @UtilityClass
    public static class FieldName {
        public static final String MDP_TYPE = "mdp_type";

        public static final String MDP_DATE_MODIF = "mdp_date_modif";

    }
}
