package fr.pagesjaunes.api.workflow.repository;

import java.util.Date;
import java.util.List;

import fr.pagesjaunes.api.workflow.model.Event;
import fr.pagesjaunes.api.workflow.model.EventMetadata;
import fr.pagesjaunes.api.workflow.model.Operation;
import fr.pagesjaunes.api.workflow.model.Step;
import fr.pagesjaunes.api.workflow.model.StepTypeEnum;

public interface EventDAO {

    /**
     * Add an operation to an event
     *
     * @param eventId ID de l'événement à modifier
     * @param operation : Opération liée à l'événement
     */
    Operation addOperation(final String eventId, final Operation operation);

    /**
     * Update an operation to an event
     *
     * @param eventId ID de l'événement à modifier
     * @param operation : Opération liée à l'événement
     */
    Operation updateOperation(final String eventId, final Operation operation);

    /**
     * Incrémente le nombre de retry d'une opération liée à un événement
     *
     * @param eventId : ID de l'événement impacté
     * @param operationId : ID de l'opération impactée
     */
    void incrementRetryOperation(final String eventId, final String operationId);

    /**
     * Add a step to an operation link at an event
     *
     * @param eventId ID de l'événement à modifier
     * @param operationId : ID de l'opération impactée
     * @param step étape à ajouter
     */
    Step addStep(final String eventId, final String operationId, final Step step);

    /**
     * Search events by criteria
     *
     * @param ressourceId      : filtre sur l'identifiant d'une ressource
     * @param ressourceType    : filtre sur le type des ressources
     * @param eventId          : filtre sur l'identifiant d'un événement
     * @param eventType        : filtre sur le type des événements
     * @param eventDateMin     : date minimale de l'événement
     * @param eventDateMax     : date maximale de l'événement
     * @param operationId      : filtre sur l'identifiant d'une opération
     * @param operationName    : filtre sur le nom des opérations
     * @param operationDateMin : date minimale d'une des opérations
     * @param operationDateMax : date maximale d'une des opérations
     * @param stepType         : filtre sur le type des étapes des opérations
     * @param contextData      : filtre sur une donnée du contexte
     * @param sort             : tri à appliquer
     * @return la liste des événements correspondant au(x) filtre(s)
     */
    List<Event> searchEvents(
            final String ressourceId,
            final String eventId,
            final Date eventDateMin,
            final Date eventDateMax,
            final String operationId,
            final String operationName,
            final Date operationDateMin,
            final Date operationDateMax,
            final StepTypeEnum stepType,
            final String contextData,
            final String sort
    );

    /**
     * Search metadatas of events by criteria
     *
     * @param ressourceId      : filtre sur l'identifiant d'une ressource
     * @param ressourceType    : filtre sur le type des ressources
     * @param eventId          : filtre sur l'identifiant d'un événement
     * @param eventType        : filtre sur le type des événements
     * @param eventDateMin     : date minimale de l'événement
     * @param eventDateMax     : date maximale de l'événement
     * @param operationId      : filtre sur l'identifiant d'une opération
     * @param operationName    : filtre sur le nom des opérations
     * @param operationDateMin : date minimale d'une des opérations
     * @param operationDateMax : date maximale d'une des opérations
     * @param stepType         : filtre sur le type des étapes des opérations
     * @param contextData      : filtre sur une donnée du contexte
     * @return metadatas liées aux événements correspondant au(x) filtre(s)
     */
    EventMetadata searchEventMetadata(
            final String ressourceId,
            final String eventId,
            final Date eventDateMin,
            final Date eventDateMax,
            final String operationId,
            final String operationName,
            final Date operationDateMin,
            final Date operationDateMax,
            final StepTypeEnum stepType,
            final String contextData
    );
}
