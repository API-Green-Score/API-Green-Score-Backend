package fr.pagesjaunes.api.workflow.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

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
public final class EventMetadata {

    private Count count;

    @Field(value = "facet")
    @JsonProperty(value = "facet")
    private FacetResult facetResult;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Count {
        @JsonProperty(value = "lower_bound")
        private long lowerBound;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FacetResult {
        @Field(value = "event_types_facet")
        @JsonProperty(value = "event_types_facet")
        private Facet eventTypesFacet;

        @Field(value = "operation_names_facet")
        @JsonProperty(value = "operation_names_facet")
        private Facet operationTypesFacet;

        @Field(value = "ressource_types_facet")
        @JsonProperty(value = "ressource_types_facet")
        private Facet ressourceTypesFacet;

        @Field(value = "step_types_facet")
        @JsonProperty(value = "step_types_facet")
        private Facet stepTypesFacet;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Facet {
        List<Bucket> buckets;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bucket {
        @Field(value = "_id")
        private String id;

        private long count;
    }
}
