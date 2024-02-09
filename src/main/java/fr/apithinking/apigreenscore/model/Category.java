package fr.apithinking.apigreenscore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {

  @Schema(name = "letter", description = "Category letter", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("letter")
  private String letter;

  @Schema(name = "name", description = "Category name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  private String name;

  @Schema(name = "rangeMin", description = "the range minimum value for the category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rangeMin")
  private int rangeMin;

  @Schema(name = "rangeMax", description = "the range maximum value for the category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rangeMax")
  private int rangeMax;

}

