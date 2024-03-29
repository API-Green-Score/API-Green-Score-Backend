package fr.pagesjaunes.api.workflow.model;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Context {

    @Field("ressource_id")
    @JsonProperty("ressource_id")
    private String ressourceId;

    private Map<String, Object> details;

}
