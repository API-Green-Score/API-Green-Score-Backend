package fr.apithinking.apigreenscore.provider.mongo.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@Builder
@FieldNameConstants
public class SectionMongo {

    @Field(Fields.NAME)
    private String name;

    @Field("default_weight")
    private BigDecimal defaultWeight;

}

