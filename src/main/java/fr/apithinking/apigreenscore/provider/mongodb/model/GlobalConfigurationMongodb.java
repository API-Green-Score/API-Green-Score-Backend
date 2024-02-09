package fr.apithinking.apigreenscore.provider.mongodb.model;

import fr.apithinking.apigreenscore.model.Category;
import fr.apithinking.apigreenscore.model.Section;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = GlobalConfigurationMongodb.COLLECTION)
@Data
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class GlobalConfigurationMongodb {

  public static final String COLLECTION = "GlobalConfiguration";

  public static final String FIELD_GLOBALE_NOTE = "globale_note";

  @Id
  private String id;

  @Field(Fields.NAME)
  private String name;

  @Field(Fields.DESCRIPTION)
  private String description;

  @Field(Fields.ACTIVE)
  private boolean active;

  @FieldNameConstants.Exclude
  @Field(FIELD_GLOBALE_NOTE)
  private int globalNote;

  @Singular
  @Field(Fields.SECTIONS)
  private List<Section> sections;

  @Singular
  @Field(Fields.CATEGORIES)
  private List<Category> categories;

}

