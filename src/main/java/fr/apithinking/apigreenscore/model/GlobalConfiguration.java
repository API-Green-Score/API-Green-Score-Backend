package fr.apithinking.apigreenscore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class GlobalConfiguration {

  @Schema(name = "globalNote", description = "The global note to be distributed over all enabled rules", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("globalNote")
  private int globalNote;

  @Singular
  @Valid
  @Schema(name = "sections", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sections")
  private List<@Valid Section> sections;

  @Singular
  @Valid
  @Schema(name = "categories", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("categories")
  private List<@Valid Category> categories;

//  public GlobalConfiguration addSectionsItem(Section sectionsItem) {
//    if (this.sections == null) {
//      this.sections = new ArrayList<>();
//    }
//    this.sections.add(sectionsItem);
//    return this;
//  }
//
//  public GlobalConfiguration addCategoriesItem(Category categoriesItem) {
//    if (this.categories == null) {
//      this.categories = new ArrayList<>();
//    }
//    this.categories.add(categoriesItem);
//    return this;
//  }

}

