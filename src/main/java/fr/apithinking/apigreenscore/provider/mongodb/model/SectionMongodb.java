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
public class SectionMongodb {

  @Field(value = Fields.NAME)
  private String name;

  @Field(value = Fields.WEIGHT)
  private float weight;

}

