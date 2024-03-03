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
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = Fields.NAME, description = "Section name", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.NAME)
    private String name;

    @Schema(name = Fields.DEFAULT_WEIGHT, description = "Section default weight", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.DEFAULT_WEIGHT)
    private BigDecimal defaultWeight;

}

