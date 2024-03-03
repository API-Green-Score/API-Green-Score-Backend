package fr.apithinking.apigreenscore.provider.mongo.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@Builder
@FieldNameConstants
public class CategoryMongo {

    @Field(Fields.LETTER)
    private String letter;

    @Field(Fields.NAME)
    private String name;

    @Field("range_min")
    private BigDecimal rangeMin;

    @Field("range_max")
    private BigDecimal rangeMax;

}

