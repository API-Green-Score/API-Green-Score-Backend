package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GlobalConfiguration
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-23T21:35:35.651524679Z[GMT]")


public class GlobalConfiguration   {
  @JsonProperty("globalNote")
  private Object globalNote = null;

  @JsonProperty("sections")
  private Object sections = null;

  @JsonProperty("categories")
  private Object categories = null;

  public GlobalConfiguration globalNote(Object globalNote) {
    this.globalNote = globalNote;
    return this;
  }

  /**
   * The global note to be distributed over all enabled rules
   * @return globalNote
   **/
  @Schema(description = "The global note to be distributed over all enabled rules")
  
    public Object getGlobalNote() {
    return globalNote;
  }

  public void setGlobalNote(Object globalNote) {
    this.globalNote = globalNote;
  }

  public GlobalConfiguration sections(Object sections) {
    this.sections = sections;
    return this;
  }

  /**
   * Get sections
   * @return sections
   **/
  @Schema(description = "")
  
    public Object getSections() {
    return sections;
  }

  public void setSections(Object sections) {
    this.sections = sections;
  }

  public GlobalConfiguration categories(Object categories) {
    this.categories = categories;
    return this;
  }

  /**
   * Get categories
   * @return categories
   **/
  @Schema(description = "")
  
    public Object getCategories() {
    return categories;
  }

  public void setCategories(Object categories) {
    this.categories = categories;
  }


  @Override
  public boolean equals(java.lang.Object o) {
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
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
