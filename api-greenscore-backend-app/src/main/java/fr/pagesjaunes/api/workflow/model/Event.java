package fr.pagesjaunes.api.workflow.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Document(collection = Event.COLLECTION_NAME)
public final class Event {
    public static final String COLLECTION_NAME = "ActionHistory";
    public static final String EV_ID_FIELD_NAME = "ev_id";

    @MongoId
    @Field(value = "_id")
    @JsonIgnore
    private ObjectId id;

    @Field(value = "ev_id")
    @FieldNameConstants.Include
    @JsonProperty(value = "event_id")
    private String eventId;

    @Field(value = "ev_date")
    @FieldNameConstants.Include
    @JsonProperty(value = "event_date")
    private Date eventDate;

    @Field(value = "operations")
    private List<Operation> operations;

    @Field(value = "parent_ev_id")
    @JsonProperty(value = "parent_event_id")
    private String parentEventId;
}
