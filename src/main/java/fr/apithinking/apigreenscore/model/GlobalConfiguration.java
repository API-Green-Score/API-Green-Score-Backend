package fr.apithinking.apigreenscore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldNameConstants
public class GlobalConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = Fields.ID, description = "id of global configuration", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.ID)
    private String id;

    @Schema(name = Fields.GLOBAL_NOTE, description = "The global note to be distributed over all enabled rules", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.GLOBAL_NOTE)
    private BigDecimal globalNote;

    @Schema(name = Fields.SECTIONS, requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.SECTIONS)
    private List<Section> sections;

    @Schema(name = Fields.CATEGORIES, requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(Fields.CATEGORIES)
    private List<Category> categories;

}

