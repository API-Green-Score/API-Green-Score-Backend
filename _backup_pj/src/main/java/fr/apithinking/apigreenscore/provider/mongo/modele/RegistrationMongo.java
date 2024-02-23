package fr.apithinking.apigreenscore.provider.mongo.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.apithinking.apigreenscore.modele.TypeRegistrationStatus;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
public class RegistrationMongo {

    @JsonProperty(FieldName.REGISTRATION_STATUS)
    @Field(name = FieldName.REGISTRATION_STATUS)
    private TypeRegistrationStatus registrationStatus;

    @JsonProperty(FieldName.REGISTRATION_TOKEN)
    @Field(name = FieldName.REGISTRATION_TOKEN)
    private String registrationToken;

    @UtilityClass
    public static final class FieldName {

        public static final String REGISTRATION_STATUS = "registration_status";

        public static final String REGISTRATION_TOKEN = "registration_token";

    }
}
