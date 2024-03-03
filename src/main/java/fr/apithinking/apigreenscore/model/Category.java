package fr.apithinking.apigreenscore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@FieldNameConstants
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = Fields.LETTER, description = "Category letter", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.LETTER)
    private String letter;

    @Schema(name = Fields.NAME, description = "Category name", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.NAME)
    private String name;

    @Valid
    @Schema(name = Fields.RANGE_MIN, description = "the range minimum value for the category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty(Fields.RANGE_MIN)
    private BigDecimal rangeMin;

    @Valid
    @Schema(name = Fields.RANGE_MAX, description = "the range maximum value for the category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty(Fields.RANGE_MAX)
    private BigDecimal rangeMax;

}

