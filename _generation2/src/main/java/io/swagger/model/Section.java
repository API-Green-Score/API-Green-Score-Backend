package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Section
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-23T21:35:35.651524679Z[GMT]")


public class Section   {
  @JsonProperty("name")
  private Object name = null;

  @JsonProperty("weight")
  private Object weight = null;

  public Section name(Object name) {
    this.name = name;
    return this;
  }

  /**
   * Section name
   * @return name
   **/
  @Schema(description = "Section name")
  
    public Object getName() {
    return name;
  }

  public void setName(Object name) {
    this.name = name;
  }

  public Section weight(Object weight) {
    this.weight = weight;
    return this;
  }

  /**
   * Section weight
   * @return weight
   **/
  @Schema(description = "Section weight")
  
    public Object getWeight() {
    return weight;
  }

  public void setWeight(Object weight) {
    this.weight = weight;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Section section = (Section) o;
    return Objects.equals(this.name, section.name) &&
        Objects.equals(this.weight, section.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, weight);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Section {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
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
