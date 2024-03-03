package fr.apithinking.apigreenscore.provider.mongo.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@Builder
@FieldNameConstants
@Document(RuleMongo.COLLECTION)
@TypeAlias(RuleMongo.COLLECTION)
public class RuleMongo {

    public static final String COLLECTION = "Rules";

    @Id
    @Field("_id")
    private String id;

    @Field(Fields.TITLE)
    private String title;

    @Field(Fields.DESCRIPTION)
    private String description;

    @Field("default_weight")
    private BigDecimal defaultWeight;

}

