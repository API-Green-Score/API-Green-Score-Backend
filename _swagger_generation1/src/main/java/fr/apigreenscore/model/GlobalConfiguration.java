package fr.apigreenscore.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.apigreenscore.model.Category;
import fr.apigreenscore.model.Section;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * GlobalConfiguration
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-15T22:08:39.761998+01:00[Europe/Paris]")
public class GlobalConfiguration {

  private BigDecimal globalNote;

  @Valid
  private List<@Valid Section> sections;

  @Valid
  private List<@Valid Category> categories;

  public GlobalConfiguration globalNote(BigDecimal globalNote) {
    this.globalNote = globalNote;
    return this;
  }

  /**
   * The global note to be distributed over all enabled rules
   * @return globalNote
  */
  @Valid 
  @Schema(name = "globalNote", description = "The global note to be distributed over all enabled rules", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("globalNote")
  public BigDecimal getGlobalNote() {
    return globalNote;
  }

  public void setGlobalNote(BigDecimal globalNote) {
    this.globalNote = globalNote;
  }

  public GlobalConfiguration sections(List<@Valid Section> sections) {
    this.sections = sections;
    return this;
  }

  public GlobalConfiguration addSectionsItem(Section sectionsItem) {
    if (this.sections == null) {
      this.sections = new ArrayList<>();
    }
    this.sections.add(sectionsItem);
    return this;
  }

  /**
   * Get sections
   * @return sections
  */
  @Valid 
  @Schema(name = "sections", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sections")
  public List<@Valid Section> getSections() {
    return sections;
  }

  public void setSections(List<@Valid Section> sections) {
    this.sections = sections;
  }

  public GlobalConfiguration categories(List<@Valid Category> categories) {
    this.categories = categories;
    return this;
  }

  public GlobalConfiguration addCategoriesItem(Category categoriesItem) {
    if (this.categories == null) {
      this.categories = new ArrayList<>();
    }
    this.categories.add(categoriesItem);
    return this;
  }

  /**
   * Get categories
   * @return categories
  */
  @Valid 
  @Schema(name = "categories", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("categories")
  public List<@Valid Category> getCategories() {
    return categories;
  }

  public void setCategories(List<@Valid Category> categories) {
    this.categories = categories;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GlobalConfiguration globalConfiguration = (GlobalConfiguration) o;
    return Objects.equals(this.globalNote, globalConfiguration.globalNote) &&
        Objects.equals(this.sections, globalConfiguration.sections) &&
        Objects.equals(this.categories, globalConfiguration.categories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(globalNote, sections, categories);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GlobalConfiguration {\n");
    sb.append("    globalNote: ").append(toIndentedString(globalNote)).append("\n");
    sb.append("    sections: ").append(toIndentedString(sections)).append("\n");
    sb.append("    categories: ").append(toIndentedString(categories)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

