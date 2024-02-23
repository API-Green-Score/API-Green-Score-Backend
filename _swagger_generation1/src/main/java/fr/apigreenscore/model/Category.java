package fr.apigreenscore.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Category
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-15T22:08:39.761998+01:00[Europe/Paris]")
public class Category {

  private String letter;

  private String name;

  private BigDecimal rangeMin;

  private BigDecimal rangeMax;

  public Category letter(String letter) {
    this.letter = letter;
    return this;
  }

  /**
   * Category letter
   * @return letter
  */
  
  @Schema(name = "letter", description = "Category letter", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("letter")
  public String getLetter() {
    return letter;
  }

  public void setLetter(String letter) {
    this.letter = letter;
  }

  public Category name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Category name
   * @return name
  */
  
  @Schema(name = "name", description = "Category name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category rangeMin(BigDecimal rangeMin) {
    this.rangeMin = rangeMin;
    return this;
  }

  /**
   * the range minimum value for the category
   * @return rangeMin
  */
  @Valid 
  @Schema(name = "rangeMin", description = "the range minimum value for the category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rangeMin")
  public BigDecimal getRangeMin() {
    return rangeMin;
  }

  public void setRangeMin(BigDecimal rangeMin) {
    this.rangeMin = rangeMin;
  }

  public Category rangeMax(BigDecimal rangeMax) {
    this.rangeMax = rangeMax;
    return this;
  }

  /**
   * the range maximum value for the category
   * @return rangeMax
  */
  @Valid 
  @Schema(name = "rangeMax", description = "the range maximum value for the category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rangeMax")
  public BigDecimal getRangeMax() {
    return rangeMax;
  }

  public void setRangeMax(BigDecimal rangeMax) {
    this.rangeMax = rangeMax;
  }

  @Override
  public boolean equals(Object o) {
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

