package fr.apithinking.apigreenscore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class Section {

  @Schema(name = "name", description = "Section name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  private String name;

  @Schema(name = "weight", description = "Section weight", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("weight")
  private float weight;

}

