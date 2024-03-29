package fr.pagesjaunes.api.workflow.model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ErrorInfo {

    @Field(value = "type")
    private String type;

    @Field(value = "msg")
    private String msg;

    @Field(value = "detail")
    private String detail;
}
