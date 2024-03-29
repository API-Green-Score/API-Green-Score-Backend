package fr.pagesjaunes.api.workflow.sm.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import fr.pagesjaunes.api.workflow.exception.NotFoundEventException;
import fr.pagesjaunes.api.workflow.exception.NotFoundOperationException;
import fr.pagesjaunes.api.workflow.model.Context;
import fr.pagesjaunes.api.workflow.model.ErrorInfo;
import fr.pagesjaunes.api.workflow.model.Event;
import fr.pagesjaunes.api.workflow.model.Operation;
import fr.pagesjaunes.api.workflow.model.SearchEventResult;
import fr.pagesjaunes.api.workflow.model.Step;
import fr.pagesjaunes.api.workflow.model.StepTypeEnum;
import fr.pagesjaunes.api.workflow.sb.traca.SBTracability;
import fr.pagesjaunes.api.workflow.sm.SMEvent;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SMEventImpl implements SMEvent {

    private final SBTracability sbTracability;
//    private final SBMessageService sbMessageService;

    public SMEventImpl(
            final SBTracability sbTracability
//            final SBMessageService sbMessageService
    ) {
        this.sbTracability = sbTracability;
//        this.sbMessageService = sbMessageService;
    }

    @Override
    public Event publish(final Context context) {
        Event event = sbTracability.initializeEvent(context);
        try {
            // Publication de l'événement
//            sbMessageService.publishMessage(event.getOperations().get(0));

            // Historisation de la publication
            sbTracability.addStep(event.getEventId(), event.getOperations().get(0).getOperationId(), Step.builder().type(StepTypeEnum.PUBLISH).build());
        } catch (Exception e) {
            // Historisation de l'erreur
            sbTracability.addStep(event.getEventId(), event.getOperations().get(0).getOperationId(), initializeOnErrorStep(e.getCause()));
            throw e;
        }

        return sbTracability.findEventByEventId(event.getEventId()).orElse(null);
    }

    @Override
    public Event findById(final String eventId) {
        return sbTracability.findEventByEventId(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
    }

    @Override
    public Operation addOperation(final String eventId, final Operation operation) {
        return sbTracability.addOperation(eventId, operation);
    }

    @Override
    public Step addStep(final String eventId, final String operationId, final Step step) {
        return sbTracability.addStep(eventId, operationId, step);
    }

    @Override
    public Event relaunch(final String eventId, final String operationId) {
        // Récupérer l'événement
        Event event = sbTracability.findEventByEventId(eventId).orElseThrow(() -> new NotFoundEventException(eventId));

        // Récupérer l'opération à relancer
        Operation operationToRelaunch = event.getOperations().stream()
                .filter(op -> operationId.equals(op.getOperationId()))
                .findFirst().orElseThrow(() -> new NotFoundOperationException(eventId, operationId));

        try {
            // Réinitialiser l'opération
            sbTracability.reinitializeOperation(eventId, operationToRelaunch);

            // Publication de l'événement
//            sbMessageService.publishMessage(operationToRelaunch);

            // Historisation de la publication
            sbTracability.addStep(event.getEventId(), operationToRelaunch.getOperationId(), Step.builder().type(StepTypeEnum.PUBLISH).build());
        } catch (Exception e) {
            // Historisation de l'erreur
            sbTracability.addStep(eventId, operationId, initializeOnErrorStep(e.getCause()));
            throw e;
        }

        return sbTracability.findEventByEventId(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
    }

    @Override
    public SearchEventResult findEvents(final String ressourceId, final String eventId, final Date eventDateMin, final Date eventDateMax, final String operationId, final String operationName, final Date operationDateMin, final Date operationDateMax, final StepTypeEnum stepType, final String contextData, final String sort) {
        return sbTracability.findEvents(ressourceId, eventId, eventDateMin, eventDateMax, operationId, operationName, operationDateMin, operationDateMax, stepType, contextData, sort);
    }

    @Override
    public void incrementRetry(String eventId, String operationId) {
        sbTracability.incrementRetry(eventId, operationId);
    }

    /**
     * Initialize an event in error to link at an event
     *
     * @param cause exception to analyse
     * @return a ErrorInfo object
     */
    private Step initializeOnErrorStep(final Throwable cause) {
        Step.StepBuilder stepBuilder = Step.builder().type(StepTypeEnum.ON_ERROR);

        if (null != cause) {
            StringWriter sw = new StringWriter();
            cause.printStackTrace(new PrintWriter(sw));
            stepBuilder.errorInfo(ErrorInfo.builder()
                    .type(cause.getClass().getSimpleName())
                    .msg(cause.getMessage())
                    .detail(sw.toString())
                    .build());
        }

        return stepBuilder.build();
    }
}
