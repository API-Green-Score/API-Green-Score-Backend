package fr.pagesjaunes.api.workflow.sm;

import java.util.Date;

import fr.pagesjaunes.api.workflow.model.Context;
import fr.pagesjaunes.api.workflow.model.Event;
import fr.pagesjaunes.api.workflow.model.Operation;
import fr.pagesjaunes.api.workflow.model.SearchEventResult;
import fr.pagesjaunes.api.workflow.model.Step;
import fr.pagesjaunes.api.workflow.model.StepTypeEnum;
public interface SMEvent {

    /**
     * Ajout d'un événement
     *
     * @param event : Evénement à ajouter
     * @return l'événement ajouté
     */
    Event publish(final Context context);

    /**
     * Recherche d'un événement à partir de son ID
     *
     * @param eventId : id de l'événement à rechercher
     * @return l'événement ré-exécuté.
     */
    Event findById(final String eventId);

    /**
     * Ajout d'une opération liée à un événement
     *
     * @param eventId : ID de l'événement impacté
     * @param operation : Operation à lier
     * @return l'opération ajoutée
     */
    Operation addOperation(final String eventId, final Operation operation);

    /**
     * Ajout d'une étape d'exécution à une opération liée à un événement
     *
     * @param eventId : ID de l'événement impacté
     * @param operationId : ID de l'opération impactée
     * @param step : Etape à ajouter
     * @return l'étape ajoutée
     */
    Step addStep(final String eventId, final String operationId, final Step step);

    /**
     * Ré-exécution de l'événement
     *
     * @param eventId : ID de l'événement à relancer
     * @param operationId : ID de l'opération impactée
     * @return l'événement ré-exécuté.
     */
    Event relaunch(final String eventId, final String operationId);

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

    /**
     * Incrémente le nombre de retry d'une opération liée à un événement
     *
     * @param eventId : ID de l'événement impacté
     * @param operationId : ID de l'opération impactée
     */
    void incrementRetry(String eventId, String operationId);
}
