package fr.pagesjaunes.api.workflow.su.impl;

import fr.pagesjaunes.api.workflow.exception.BadRequestSearchEventException;
import fr.pagesjaunes.api.workflow.model.Context;
import fr.pagesjaunes.api.workflow.model.Event;
import fr.pagesjaunes.api.workflow.model.SearchEventResult;
import fr.pagesjaunes.api.workflow.model.Step;
import fr.pagesjaunes.api.workflow.model.StepTypeEnum;
import fr.pagesjaunes.api.workflow.sm.SMEvent;
import fr.pagesjaunes.api.workflow.su.SUEvent;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SUEventImpl.API)
@Tag(name = "Events")
public class SUEventImpl implements SUEvent {

    protected static final String API = "/events";

    private final SMEvent smEvent;

    public SUEventImpl(final SMEvent smEvent) {
        this.smEvent = smEvent;
    }

    @Override
    @PostMapping
    @Operation(description = "Ajout d'un événement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    public ResponseEntity<Event> publish(@RequestBody final Context context) {
        return ResponseEntity
                .created(URI.create(API))
                .body(smEvent.publish(context));
    }

    @Override
    @GetMapping(path = "/{event_id}")
    @Operation(description = "Recherche d'un événement par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", content = @Content)
    })
    public ResponseEntity<Event> findById(@Parameter(description = "Id de l'événement") @PathVariable("event_id") final String eventId) {
        return ResponseEntity
                .ok(smEvent.findById(eventId));
    }

    @Override
    @PostMapping(path = "/{event_id}/operations")
    @Operation(description = "Ajout d'une opération liée à un événement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = fr.pagesjaunes.api.workflow.model.Operation.class))),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    public ResponseEntity<fr.pagesjaunes.api.workflow.model.Operation> addOperation(
            @Parameter(description = "Id de l'événement") @PathVariable("event_id") final String eventId,
            @RequestBody final fr.pagesjaunes.api.workflow.model.Operation operation) {
        return ResponseEntity
                .created(URI.create(API + "/" + eventId + "/operations"))
                .body(smEvent.addOperation(eventId, operation));
    }

    @Override
    @PostMapping(path = "/{event_id}/operations/{operation_id}/retry")
    @Operation(description = "Incrémente le nombre de retry d'une opération liée à un événement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    public ResponseEntity<Void> incrementRetry(
            @Parameter(description = "Id de l'événement") @PathVariable("event_id") final String eventId,
            @Parameter(description = "Id de l'opération") @PathVariable("operation_id") final String operationId) {
        smEvent.incrementRetry(eventId, operationId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping(path = "/{event_id}/operations/{operation_id}/steps")
    @Operation(description = "Ajout d'une étape d'exécution à une opération liée à un événement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Step.class))),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    public ResponseEntity<Step> addStep(
            @Parameter(description = "Id de l'événement") @PathVariable("event_id") final String eventId,
            @Parameter(description = "Id de l'opération") @PathVariable("operation_id") final String operationId,
            @RequestBody final Step step) {
        return ResponseEntity
                .created(URI.create(API + "/" + eventId + "/operations/" + operationId + "/steps"))
                .body(smEvent.addStep(eventId, operationId, step));
    }

    @Override
    @PatchMapping(path = "/{event_id}/operations/{operation_id}")
    @Operation(description = "Relance d'une opération, si c'est l'opération 'initialize_event' alors l'événement sera retransmis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    public ResponseEntity<Event> relaunch(
            @Parameter(description = "Id de l'événement lié à l'opération à rejouer") @PathVariable("event_id") final String eventId,
            @Parameter(description = "Id de l'opération à relancer") @PathVariable("operation_id") final String operationId
    ) {

        return ResponseEntity.ok(smEvent.relaunch(eventId, operationId));
    }

    @Override
    @GetMapping
    @Operation(description = "Recherche d'événements liés à des exécutions de workflow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    public ResponseEntity<SearchEventResult> findEvents(
            @RequestParam(name = "ressource_id", required = false)
            @Parameter(description = "filtre sur l'identifiant d'une ressource")
            final String ressourceId,
            @RequestParam(name = "event_id", required = false)
            @Parameter(description = "filtre sur l'identifiant d'un événement")
            final String eventId,
            @RequestParam(name = "event_date_min", required = false)
            @Parameter(description = "date minimale de l'exécution de l'événement")
            final Date eventDateMin,
            @RequestParam(name = "event_date_max", required = false)
            @Parameter(description = "date maximale de l'exécution de l'événement")
            final Date eventDateMax,
            @RequestParam(name = "operation_id", required = false)
            @Parameter(description = "filtre sur l'identifiant d'une opération")
            final String operationId,
            @RequestParam(name = "operation_name", required = false)
            @Parameter(description = "filtre sur le nom des opérations")
            final String operationName,
            @RequestParam(name = "operation_date_min", required = false)
            @Parameter(description = "date minimale de l'exécution d'une des opérations")
            final Date operationDateMin,
            @RequestParam(name = "operation_date_max", required = false)
            @Parameter(description = "date maximale de l'exécution d'une des opérations")
            final Date operationDateMax,
            @RequestParam(name = "step_type", required = false)
            @Parameter(description = "filtre sur le type des étapes des opérations (ON_ERROR, PUBLISH, SUBSCRIBE, ...)")
            final StepTypeEnum stepType,
            @RequestParam(name = "context_data", required = false)
            @Parameter(description = "filtre sur une donnée fonctionnelle")
            final String contextData,
            @RequestParam(name = "sort", required = false, defaultValue = "eventDate,desc")
            @Parameter(description = "tri à appliquer sous la forme '{fieldName}(,('asc'|'desc'))?' (nom du champ en camel case) - ex : 'eventId' or 'worcName,asc' or 'workflowExecution.wfName,desc'")
            final String sort
    ) {
        if (eventDateMin != null && eventDateMax != null && eventDateMax.getTime() < eventDateMin.getTime()) {
            throw new BadRequestSearchEventException("The event date MAX is in the past of the event date MIN");
        }
        if (operationDateMin != null && operationDateMax != null && operationDateMax.getTime() < operationDateMin.getTime()) {
            throw new BadRequestSearchEventException("The operation date MAX is in the past of the operation date MIN");
        }

        return ResponseEntity.ok(smEvent.findEvents(ressourceId, eventId, eventDateMin, eventDateMax, operationId, operationName, operationDateMin, operationDateMax, stepType, contextData, sort));
    }
}
