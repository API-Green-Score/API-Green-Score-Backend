package fr.pagesjaunes.api.workflow.model;

import java.util.Date;
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
public final class Operation {

    @Field(value = "op_id")
    @JsonProperty(value = "operation_id")
    private String operationId;

    @Field(value = "op_date")
    @FieldNameConstants.Include
    @JsonProperty(value = "operation_date")
    private Date operationDate;

    @Field(value = "op_name")
    @JsonProperty(value = "operation_name")
    private String operationName;

    @Field(value = "context")
    private Context context;

    @Field(value = "headers")
    private List<Header> headers;

    @Field(value = "steps")
    private List<Step> steps;

    @Field(value = "nb_retry")
    @JsonProperty(value = "nb_retry")
    private Integer nbRetry;
}
