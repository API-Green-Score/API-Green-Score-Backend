package fr.pagesjaunes.api.workflow.model;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Step {

    @Field(value = "type")
    private StepTypeEnum type;

    @Field(value = "error_info")
    @JsonProperty(value = "error_info")
    private ErrorInfo errorInfo;

}
