package fr.pagesjaunes.api.workflow.sb.traca;

import java.util.Date;
import java.util.Optional;

import fr.pagesjaunes.api.workflow.model.Context;
import fr.pagesjaunes.api.workflow.model.Event;
import fr.pagesjaunes.api.workflow.model.Operation;
import fr.pagesjaunes.api.workflow.model.SearchEventResult;
import fr.pagesjaunes.api.workflow.model.Step;
import fr.pagesjaunes.api.workflow.model.StepTypeEnum;

public interface SBTracability {

    /**
     * Initialize a new event entity
     *
     * @param context : context of the event
     */
    Event initializeEvent(final Context context);

    /**
     * Add an operation to an event
     *
     * @param eventId ID de l'événement à modifier
     * @param operation : Opération liée àl'événement
     */
    Operation addOperation(final String eventId, final Operation operation);

    /**
     * Reinitialize an operation to an event
     *
     * @param eventId ID de l'événement à modifier
     * @param operation : Opération à réinitialiser liée à l'événement
     */
    void reinitializeOperation(final String eventId, final Operation operation);

    /**
     * Incrémente le nombre de retry d'une opération liée à un événement
     *
     * @param eventId : ID de l'événement impacté
     * @param operationId : ID de l'opération impactée
     */
    void incrementRetry(String eventId, String operationId);

    /**
     * Add a step to an operation link at an event
     *
     * @param eventId ID de l'événement à modifier
     * @param operationId : ID de l'opération impactée
     * @param step étape à ajouter
     */
    Step addStep(final String eventId, final String operationId, final Step step);

    /**
     * Save a workfow event
     *
     * @param event : workfow event to save
     */
    Event saveWorkflowEvent(final Event event);

    /**
     * Delete all the events in error
     *
     * @param event : event to remove
     */
    void deleteEvent(final Event event);

    /**
     * Find an event in error through its key
     *
     * @param eventId : ID of event to find
     */
    Optional<Event> findEventByEventId(final String eventId);

    /**
     * Update the event
     *
     * @param event : event
     */
    void updateEvent(final Event event);

    /**
     * Recherche d'événements liés à des exécutions de workflow
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
    SearchEventResult findEvents(
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
}
