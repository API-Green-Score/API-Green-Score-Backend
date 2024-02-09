package fr.apithinking.apigreenscore.provider.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMongodb {

  public static final String FIELD_RANGE_MIN = "range_min";
  public static final String FIELD_RANGE_MAX = "range_max";

  @Field(Fields.LETTER)
  private String letter;

  @Field(Fields.NAME)
  private String name;

  @FieldNameConstants.Exclude
  @Field(FIELD_RANGE_MIN)
  private int rangeMin;

  @FieldNameConstants.Exclude
  @Field(FIELD_RANGE_MAX)
  private int rangeMax;

}

