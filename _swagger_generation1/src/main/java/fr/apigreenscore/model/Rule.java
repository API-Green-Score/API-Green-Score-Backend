package fr.apigreenscore.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Rule
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-15T22:08:39.761998+01:00[Europe/Paris]")
public class Rule {

  private String id;

  private String section;

  private String itemsAnalyzed;

  private String description;

  private BigDecimal secionWeight;

  private BigDecimal totalWeight;

  private BigDecimal points;

  private Boolean evaluation;

  private BigDecimal score;

  private String comment;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime creationDate;

  public Rule id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Rule identifier
   * @return id
  */
  
  @Schema(name = "id", description = "Rule identifier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Rule section(String section) {
    this.section = section;
    return this;
  }

  /**
   * Rule section
   * @return section
  */
  
  @Schema(name = "section", description = "Rule section", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("section")
  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  public Rule itemsAnalyzed(String itemsAnalyzed) {
    this.itemsAnalyzed = itemsAnalyzed;
    return this;
  }

  /**
   * Items analyzed by the rule
   * @return itemsAnalyzed
  */
  
  @Schema(name = "itemsAnalyzed", description = "Items analyzed by the rule", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("itemsAnalyzed")
  public String getItemsAnalyzed() {
    return itemsAnalyzed;
  }

  public void setItemsAnalyzed(String itemsAnalyzed) {
    this.itemsAnalyzed = itemsAnalyzed;
  }

  public Rule description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Rule description
   * @return description
  */
  
  @Schema(name = "description", description = "Rule description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Rule secionWeight(BigDecimal secionWeight) {
    this.secionWeight = secionWeight;
    return this;
  }

  /**
   * Rule weight in section
   * @return secionWeight
  */
  @Valid 
  @Schema(name = "secionWeight", description = "Rule weight in section", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("secionWeight")
  public BigDecimal getSecionWeight() {
    return secionWeight;
  }

  public void setSecionWeight(BigDecimal secionWeight) {
    this.secionWeight = secionWeight;
  }

  public Rule totalWeight(BigDecimal totalWeight) {
    this.totalWeight = totalWeight;
    return this;
  }

  /**
   * Total rule weight regarding all rules weight
   * @return totalWeight
  */
  @Valid 
  @Schema(name = "totalWeight", description = "Total rule weight regarding all rules weight", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalWeight")
  public BigDecimal getTotalWeight() {
    return totalWeight;
  }

  public void setTotalWeight(BigDecimal totalWeight) {
    this.totalWeight = totalWeight;
  }

  public Rule points(BigDecimal points) {
    this.points = points;
    return this;
  }

  /**
   * Rule points regarding all rules
   * @return points
  */
  @Valid 
  @Schema(name = "points", description = "Rule points regarding all rules", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("points")
  public BigDecimal getPoints() {
    return points;
  }

  public void setPoints(BigDecimal points) {
    this.points = points;
  }

  public Rule evaluation(Boolean evaluation) {
    this.evaluation = evaluation;
    return this;
  }

  /**
   * True if rule has positive evaluation, false otherwise
   * @return evaluation
  */
  
  @Schema(name = "evaluation", description = "True if rule has positive evaluation, false otherwise", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("evaluation")
  public Boolean getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(Boolean evaluation) {
    this.evaluation = evaluation;
  }

  public Rule score(BigDecimal score) {
    this.score = score;
    return this;
  }

  /**
   * Rule score infered from evaluation and points
   * @return score
  */
  @Valid 
  @Schema(name = "score", description = "Rule score infered from evaluation and points", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("score")
  public BigDecimal getScore() {
    return score;
  }

  public void setScore(BigDecimal score) {
    this.score = score;
  }

  public Rule comment(String comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Evaluation comment for rule
   * @return comment
  */
  
  @Schema(name = "comment", description = "Evaluation comment for rule", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("comment")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Rule creationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Rule creation date
   * @return creationDate
  */
  @Valid 
  @Schema(name = "creationDate", description = "Rule creation date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("creationDate")
  public OffsetDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rule rule = (Rule) o;
    return Objects.equals(this.id, rule.id) &&
        Objects.equals(this.section, rule.section) &&
        Objects.equals(this.itemsAnalyzed, rule.itemsAnalyzed) &&
        Objects.equals(this.description, rule.description) &&
        Objects.equals(this.secionWeight, rule.secionWeight) &&
        Objects.equals(this.totalWeight, rule.totalWeight) &&
        Objects.equals(this.points, rule.points) &&
        Objects.equals(this.evaluation, rule.evaluation) &&
        Objects.equals(this.score, rule.score) &&
        Objects.equals(this.comment, rule.comment) &&
        Objects.equals(this.creationDate, rule.creationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, section, itemsAnalyzed, description, secionWeight, totalWeight, points, evaluation, score, comment, creationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rule {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    section: ").append(toIndentedString(section)).append("\n");
    sb.append("    itemsAnalyzed: ").append(toIndentedString(itemsAnalyzed)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    secionWeight: ").append(toIndentedString(secionWeight)).append("\n");
    sb.append("    totalWeight: ").append(toIndentedString(totalWeight)).append("\n");
    sb.append("    points: ").append(toIndentedString(points)).append("\n");
    sb.append("    evaluation: ").append(toIndentedString(evaluation)).append("\n");
    sb.append("    score: ").append(toIndentedString(score)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
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

