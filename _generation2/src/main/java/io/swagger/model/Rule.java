package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Rule
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-23T21:35:35.651524679Z[GMT]")


public class Rule   {
  @JsonProperty("id")
  private Object id = null;

  @JsonProperty("section")
  private Object section = null;

  @JsonProperty("title")
  private Object title = null;

  @JsonProperty("description")
  private Object description = null;

  @JsonProperty("ruleWeightInSection")
  private Object ruleWeightInSection = null;

  @JsonProperty("ruleWeightTotal")
  private Object ruleWeightTotal = null;

  @JsonProperty("points")
  private Object points = null;

  @JsonProperty("evaluation")
  private Object evaluation = null;

  @JsonProperty("score")
  private Object score = null;

  @JsonProperty("comment")
  private Object comment = null;

  @JsonProperty("creationDate")
  private Object creationDate = null;

  public Rule id(Object id) {
    this.id = id;
    return this;
  }

  /**
   * Rule identifier
   * @return id
   **/
  @Schema(description = "Rule identifier")
  
    public Object getId() {
    return id;
  }

  public void setId(Object id) {
    this.id = id;
  }

  public Rule section(Object section) {
    this.section = section;
    return this;
  }

  /**
   * Rule section
   * @return section
   **/
  @Schema(description = "Rule section")
  
    public Object getSection() {
    return section;
  }

  public void setSection(Object section) {
    this.section = section;
  }

  public Rule title(Object title) {
    this.title = title;
    return this;
  }

  /**
   * Items analyzed by the rule
   * @return title
   **/
  @Schema(description = "Items analyzed by the rule")
  
    public Object getTitle() {
    return title;
  }

  public void setTitle(Object title) {
    this.title = title;
  }

  public Rule description(Object description) {
    this.description = description;
    return this;
  }

  /**
   * Rule description
   * @return description
   **/
  @Schema(description = "Rule description")
  
    public Object getDescription() {
    return description;
  }

  public void setDescription(Object description) {
    this.description = description;
  }

  public Rule ruleWeightInSection(Object ruleWeightInSection) {
    this.ruleWeightInSection = ruleWeightInSection;
    return this;
  }

  /**
   * Rule weight in section
   * @return ruleWeightInSection
   **/
  @Schema(description = "Rule weight in section")
  
    public Object getRuleWeightInSection() {
    return ruleWeightInSection;
  }

  public void setRuleWeightInSection(Object ruleWeightInSection) {
    this.ruleWeightInSection = ruleWeightInSection;
  }

  public Rule ruleWeightTotal(Object ruleWeightTotal) {
    this.ruleWeightTotal = ruleWeightTotal;
    return this;
  }

  /**
   * Total rule weight regarding all rules weight
   * @return ruleWeightTotal
   **/
  @Schema(description = "Total rule weight regarding all rules weight")
  
    public Object getRuleWeightTotal() {
    return ruleWeightTotal;
  }

  public void setRuleWeightTotal(Object ruleWeightTotal) {
    this.ruleWeightTotal = ruleWeightTotal;
  }

  public Rule points(Object points) {
    this.points = points;
    return this;
  }

  /**
   * Rule points regarding all rules
   * @return points
   **/
  @Schema(description = "Rule points regarding all rules")
  
    public Object getPoints() {
    return points;
  }

  public void setPoints(Object points) {
    this.points = points;
  }

  public Rule evaluation(Object evaluation) {
    this.evaluation = evaluation;
    return this;
  }

  /**
   * True if rule has positive evaluation, false otherwise
   * @return evaluation
   **/
  @Schema(description = "True if rule has positive evaluation, false otherwise")
  
    public Object getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(Object evaluation) {
    this.evaluation = evaluation;
  }

  public Rule score(Object score) {
    this.score = score;
    return this;
  }

  /**
   * Rule score infered from evaluation and points
   * @return score
   **/
  @Schema(description = "Rule score infered from evaluation and points")
  
    public Object getScore() {
    return score;
  }

  public void setScore(Object score) {
    this.score = score;
  }

  public Rule comment(Object comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Evaluation comment for rule
   * @return comment
   **/
  @Schema(description = "Evaluation comment for rule")
  
    public Object getComment() {
    return comment;
  }

  public void setComment(Object comment) {
    this.comment = comment;
  }

  public Rule creationDate(Object creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Rule creation date
   * @return creationDate
   **/
  @Schema(description = "Rule creation date")
  
    public Object getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Object creationDate) {
    this.creationDate = creationDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rule rule = (Rule) o;
    return Objects.equals(this.id, rule.id) &&
        Objects.equals(this.section, rule.section) &&
        Objects.equals(this.title, rule.title) &&
        Objects.equals(this.description, rule.description) &&
        Objects.equals(this.ruleWeightInSection, rule.ruleWeightInSection) &&
        Objects.equals(this.ruleWeightTotal, rule.ruleWeightTotal) &&
        Objects.equals(this.points, rule.points) &&
        Objects.equals(this.evaluation, rule.evaluation) &&
        Objects.equals(this.score, rule.score) &&
        Objects.equals(this.comment, rule.comment) &&
        Objects.equals(this.creationDate, rule.creationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, section, title, description, ruleWeightInSection, ruleWeightTotal, points, evaluation, score, comment, creationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rule {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    section: ").append(toIndentedString(section)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    ruleWeightInSection: ").append(toIndentedString(ruleWeightInSection)).append("\n");
    sb.append("    ruleWeightTotal: ").append(toIndentedString(ruleWeightTotal)).append("\n");
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
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
