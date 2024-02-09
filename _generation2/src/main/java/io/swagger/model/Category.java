package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Category
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-23T21:35:35.651524679Z[GMT]")


public class Category   {
  @JsonProperty("letter")
  private Object letter = null;

  @JsonProperty("name")
  private Object name = null;

  @JsonProperty("rangeMin")
  private Object rangeMin = null;

  @JsonProperty("rangeMax")
  private Object rangeMax = null;

  public Category letter(Object letter) {
    this.letter = letter;
    return this;
  }

  /**
   * Category letter
   * @return letter
   **/
  @Schema(description = "Category letter")
  
    public Object getLetter() {
    return letter;
  }

  public void setLetter(Object letter) {
    this.letter = letter;
  }

  public Category name(Object name) {
    this.name = name;
    return this;
  }

  /**
   * Category name
   * @return name
   **/
  @Schema(description = "Category name")
  
    public Object getName() {
    return name;
  }

  public void setName(Object name) {
    this.name = name;
  }

  public Category rangeMin(Object rangeMin) {
    this.rangeMin = rangeMin;
    return this;
  }

  /**
   * the range minimum value for the category
   * @return rangeMin
   **/
  @Schema(description = "the range minimum value for the category")
  
    public Object getRangeMin() {
    return rangeMin;
  }

  public void setRangeMin(Object rangeMin) {
    this.rangeMin = rangeMin;
  }

  public Category rangeMax(Object rangeMax) {
    this.rangeMax = rangeMax;
    return this;
  }

  /**
   * the range maximum value for the category
   * @return rangeMax
   **/
  @Schema(description = "the range maximum value for the category")
  
    public Object getRangeMax() {
    return rangeMax;
  }

  public void setRangeMax(Object rangeMax) {
    this.rangeMax = rangeMax;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(this.letter, category.letter) &&
        Objects.equals(this.name, category.name) &&
        Objects.equals(this.rangeMin, category.rangeMin) &&
        Objects.equals(this.rangeMax, category.rangeMax);
  }

  @Override
  public int hashCode() {
    return Objects.hash(letter, name, rangeMin, rangeMax);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Category {\n");
    
    sb.append("    letter: ").append(toIndentedString(letter)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    rangeMin: ").append(toIndentedString(rangeMin)).append("\n");
    sb.append("    rangeMax: ").append(toIndentedString(rangeMax)).append("\n");
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
