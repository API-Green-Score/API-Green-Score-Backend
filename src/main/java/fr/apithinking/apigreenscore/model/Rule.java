package fr.apithinking.apigreenscore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class Rule {

  @Schema(name = "id", description = "Rule identifier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  private String id;

  @Schema(name = "section", description = "Rule section", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("section")
  private String section;

  @Schema(name = "title", description = "Items analyzed by the rule", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  private String title;

  @Schema(name = "description", description = "Rule description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  private String description;

  @Schema(name = "ruleWeightInSection", description = "Rule weight in section", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ruleWeightInSection")
  private float ruleWeightInSection;

  @Schema(name = "ruleWeightTotal", description = "Total rule weight regarding all rules weight", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ruleWeightTotal")
  private float ruleWeightTotal;

  @Schema(name = "points", description = "Rule points regarding all rules", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("points")
  private int points;

  @Schema(name = "evaluation", description = "True if rule has positive evaluation, false otherwise", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("evaluation")
  private Boolean evaluation;

  @Schema(name = "score", description = "Rule score infered from evaluation and points", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("score")
  private int score;

  @Schema(name = "comment", description = "Evaluation comment for rule", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("comment")
  private String comment;

  @Valid
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @Schema(name = "creationDate", description = "Rule creation date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("creationDate")
  private OffsetDateTime creationDate;

}

