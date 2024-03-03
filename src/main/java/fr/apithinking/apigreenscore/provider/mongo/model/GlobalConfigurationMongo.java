package fr.apithinking.apigreenscore.provider.mongo.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldNameConstants
@Document(GlobalConfigurationMongo.COLLECTION)
@TypeAlias(GlobalConfigurationMongo.COLLECTION)
public class GlobalConfigurationMongo {

    public static final String COLLECTION = "GlobalConfiguration";

    @Id
    @Field("_id")
    private String id;

    @Field("global_note")
    private BigDecimal globalNote;

    @Field(Fields.SECTIONS)
    private List<SectionMongo> sections;

    @Field(Fields.CATEGORIES)
    private List<CategoryMongo> categories;

}
