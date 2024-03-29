package fr.pagesjaunes.api.workflow.sb.traca.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.pagesjaunes.api.workflow.model.Context;
import fr.pagesjaunes.api.workflow.model.Event;
import fr.pagesjaunes.api.workflow.model.EventMetadata;
import fr.pagesjaunes.api.workflow.model.Header;
import fr.pagesjaunes.api.workflow.model.Operation;
import fr.pagesjaunes.api.workflow.model.SearchEventResult;
import fr.pagesjaunes.api.workflow.model.Step;
import fr.pagesjaunes.api.workflow.model.StepTypeEnum;
import fr.pagesjaunes.api.workflow.repository.EventDAO;
import fr.pagesjaunes.api.workflow.repository.EventRepository;
import fr.pagesjaunes.api.workflow.sb.traca.SBTracability;
//import fr.pagesjaunes.socletechnique.core.support.HeadersStorage;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SBTracabilityImpl implements SBTracability {

    private final EventRepository eventRepository;
    private final EventDAO eventDAO;
//    private final ObjectProvider<HeadersStorage> headersStorageProvider;

    public SBTracabilityImpl(
            final EventRepository eventRepository,
            final EventDAO eventDAO
//            @Qualifier(value = HeadersStorage.REQUEST_HEADERS_TO_FORWARD_STORAGE_BEAN_NAME) final ObjectProvider<HeadersStorage> headersStorageProvider
    ) {
        this.eventRepository = eventRepository;
        this.eventDAO = eventDAO;
//        this.headersStorageProvider = headersStorageProvider;
    }

    @Override
    public Event initializeEvent(final Context context) {
        String eventId = UUID.randomUUID().toString();
        String operationId = UUID.randomUUID().toString();
        Date eventDate = new Date();
        List<Header> headers = initializeHeaders(eventId, operationId);

        Event.EventBuilder eventBuilder = Event.builder()
                .eventId(eventId)
                .eventDate(eventDate)
                .operations(List.of(Operation.builder()
                        .operationId(operationId)
                        .operationDate(eventDate)
                        .operationName("initialize-event")
                        .context(context)
                        .headers(headers)
                        .steps(List.of(Step.builder().type(StepTypeEnum.INIT).build()))
                        .build()));

        headers.stream().filter(h -> "X-PJ-PARENT-EVENT-ID".equalsIgnoreCase(h.getName())).findFirst().ifPresent(header -> eventBuilder.parentEventId(header.getValue()));
        return eventRepository.save(eventBuilder.build());
    }

    @Override
    public Operation addOperation(final String eventId, final Operation operation) {
        operation.setOperationId(UUID.randomUUID().toString());
        operation.setOperationDate(new Date());
        operation.setSteps(List.of(Step.builder().type(StepTypeEnum.INIT).build()));

        return eventDAO.addOperation(eventId, operation);
    }

    @Override
    public void reinitializeOperation(final String eventId, final Operation operation) {
        operation.setOperationDate(new Date());
        operation.setSteps(List.of(Step.builder().type(StepTypeEnum.INIT).build()));
        operation.setNbRetry(null);

        eventDAO.updateOperation(eventId, operation);
    }

    @Override
    public void incrementRetry(String eventId, String operationId) {
        eventDAO.incrementRetryOperation(eventId, operationId);
    }

    @Override
    public Step addStep(final String eventId, final String operationId, final Step step) {
        return eventDAO.addStep(eventId, operationId, step);
    }

    @Override
    public Event saveWorkflowEvent(final Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(final Event event) {
        eventRepository.delete(event);
    }

    @Override
    public Optional<Event> findEventByEventId(final String eventId) {
        return eventRepository.findByEventId(eventId);
    }

    @Override
    public void updateEvent(final Event event) {
        eventRepository.save(event);
    }

    @Override
    public SearchEventResult findEvents(final String ressourceId, final String eventId, final Date eventDateMin, final Date eventDateMax, final String operationId, final String operationName, final Date operationDateMin, final Date operationDateMax, final StepTypeEnum stepType, final String contextData, String sort) {
        List<Event> events = eventDAO.searchEvents(
                ressourceId,
                eventId,
                eventDateMin,
                eventDateMax,
                operationId,
                operationName,
                operationDateMin,
                operationDateMax,
                stepType,
                contextData,
                sort
        );

        EventMetadata eventMetadata = eventDAO.searchEventMetadata(
                ressourceId,
                eventId,
                eventDateMin,
                eventDateMax,
                operationId,
                operationName,
                operationDateMin,
                operationDateMax,
                stepType,
                contextData
        );

        return SearchEventResult.builder().events(events).metadata(eventMetadata).build();
    }

    private List<Header> initializeHeaders(final String eventId, final String operationId) {
//        HeadersStorage headersStorage = headersStorageProvider.getIfUnique();
//        List<Header> headers = (null == headersStorage)
//                ? new ArrayList<>()
//                : headersStorage.getHeaders().stream().map(httpHeader -> Header.builder().name(httpHeader.getName().toUpperCase()).value(httpHeader.getValue()).build()).collect(Collectors.toList());
        List<Header> headers = new ArrayList<>();

        Optional<Header> requestIdHeaderOptional = headers.stream().filter(h -> "X-PJ-REQUEST-ID".equalsIgnoreCase(h.getName())).findFirst();
        if (requestIdHeaderOptional.isEmpty()) {
            headers.add(Header.builder().name("X-PJ-REQUEST-ID").value(UUID.randomUUID().toString()).build());
        }

        Optional<Header> eventIdHeaderOptional = headers.stream().filter(h -> "X-PJ-EVENT-ID".equalsIgnoreCase(h.getName())).findFirst();
        if (eventIdHeaderOptional.isPresent()) {
            headers.add(Header.builder().name("X-PJ-PARENT-EVENT-ID").value(eventIdHeaderOptional.get().getValue()).build());
            headers.stream().filter(h -> "X-PJ-EVENT-ID".equalsIgnoreCase(h.getName())).findFirst().ifPresent(headers::remove);
            headers.stream().filter(h -> "X-PJ-OP-ID".equalsIgnoreCase(h.getName())).findFirst().ifPresent(headers::remove);
        }
        headers.add(Header.builder().name("X-PJ-EVENT-ID").value(eventId).build());
        headers.add(Header.builder().name("X-PJ-OP-ID").value(operationId).build());

        return headers;
    }
}
