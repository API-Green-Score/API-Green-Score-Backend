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
public final class Header {

    @Field(value = "name")
    private String name;

    @Field(value = "value")
    private String value;
}
