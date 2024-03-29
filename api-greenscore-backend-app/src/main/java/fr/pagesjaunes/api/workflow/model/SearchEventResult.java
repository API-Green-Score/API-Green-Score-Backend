package fr.pagesjaunes.api.workflow.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public final class SearchEventResult {

    @JsonProperty(value = "events")
    private List<Event> events;

    @JsonProperty(value = "metadata")
    private EventMetadata metadata;
}
