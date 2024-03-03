package fr.apithinking.apigreenscore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@FieldNameConstants
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = Fields.ID, description = "Rule identifier", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.ID)
    private String id;

    @Schema(name = Fields.TITLE, description = "Rule title", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.TITLE)
    private String title;

    @Schema(name = Fields.DESCRIPTION, description = "Rule description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty(Fields.DESCRIPTION)
    private String description;

    @Schema(name = Fields.DEFAULT_WEIGHT, description = "Rule default weight", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.DEFAULT_WEIGHT)
    private BigDecimal defaultWeight;

}

