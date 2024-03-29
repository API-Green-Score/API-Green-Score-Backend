package fr.pagesjaunes.api.workflow.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.search.DateRangeSearchOperator;
import com.mongodb.client.model.search.DateRangeSearchOperatorBase;
import com.mongodb.client.model.search.SearchCollector;
import com.mongodb.client.model.search.SearchFacet;
import com.mongodb.client.model.search.SearchOperator;
import com.mongodb.client.model.search.SearchOptions;
import com.mongodb.client.model.search.SearchPath;

import fr.pagesjaunes.api.workflow.model.Event;
import fr.pagesjaunes.api.workflow.model.EventMetadata;
import fr.pagesjaunes.api.workflow.model.Operation;
import fr.pagesjaunes.api.workflow.model.Step;
import fr.pagesjaunes.api.workflow.model.StepTypeEnum;
import fr.pagesjaunes.api.workflow.repository.EventDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventDAOImpl implements EventDAO {

    @Value("${confpj.mongodb.workflow.search.index:tracability}")
    private String searchIndex;

    @Value("${confpj.mongodb.workflow.search.limit:1000}")
    private long searchLimit;

    private final MongoTemplate mongoTemplate;

    public EventDAOImpl(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Operation addOperation(final String eventId, final Operation operation) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Event.EV_ID_FIELD_NAME).is(eventId));

        Update update = new Update();
        update.addToSet("operations", operation);
        mongoTemplate.findAndModify(query, update, Event.class);

        return operation;
    }

    @Override
    public Operation updateOperation(final String eventId, final Operation operation) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Event.EV_ID_FIELD_NAME).is(eventId).and("operations.op_id").is(operation.getOperationId()));

        Update update = new Update();
        update.set("operations.$", operation);
        mongoTemplate.findAndModify(query, update, Event.class);

        return operation;
    }

    @Override
    public void incrementRetryOperation(final String eventId, final String operationId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Event.EV_ID_FIELD_NAME).is(eventId).and("operations.op_id").is(operationId));

        Update update = new Update();
        update.inc("operations.$.nb_retry");
        mongoTemplate.findAndModify(query, update, Event.class);
    }

    @Override
    public Step addStep(final String eventId, final String operationId, final Step step) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Event.EV_ID_FIELD_NAME).is(eventId).and("operations.op_id").is(operationId));

        Update update = new Update();
        update.addToSet("operations.$.steps", step);
        mongoTemplate.findAndModify(query, update, Event.class);

        return step;
    }

    @Override
    public List<Event> searchEvents(final String ressourceId, final String eventId, final Date eventDateMin, final Date eventDateMax, final String operationId, final String operationName, final Date operationDateMin, final Date operationDateMax, final StepTypeEnum stepType, final String contextData, final String sort) {
        AggregationOperation searchOperation = Aggregation.stage(
            Aggregates.search(
                    initializeSearchOperator(ressourceId, eventId, eventDateMin, eventDateMax, operationId, operationName, operationDateMin, operationDateMax, stepType, contextData),
                    SearchOptions.searchOptions().index(searchIndex)
            )
        );

        TypedAggregation<Event> aggregation = Aggregation.newAggregation(Event.class,
                searchOperation,
                Aggregation.sort(initializeSortParam(sort)),
                Aggregation.limit(searchLimit)
        );

        return mongoTemplate.aggregate(aggregation, Event.class).getMappedResults();
    }

    @Override
    public EventMetadata searchEventMetadata(final String ressourceId, final String eventId, final Date eventDateMin, final Date eventDateMax, final String operationId, final String operationName, final Date operationDateMin, final Date operationDateMax, final StepTypeEnum stepType, final String contextData) {
        AggregationOperation facetOperation = Aggregation.stage(
                Aggregates.searchMeta(
                        SearchCollector.facet(
                                initializeSearchOperator(ressourceId, eventId, eventDateMin, eventDateMax, operationId, operationName, operationDateMin, operationDateMax, stepType, contextData),
                                List.of(
                                        SearchFacet.stringFacet("event_types_facet", SearchPath.fieldPath("operations.context.event_type")),
                                        SearchFacet.stringFacet("operation_names_facet", SearchPath.fieldPath("operations.op_name")),
                                        SearchFacet.stringFacet("ressource_types_facet", SearchPath.fieldPath("operations.context.ressource_type")),
                                        SearchFacet.stringFacet("step_types_facet", SearchPath.fieldPath("operations.steps.type"))
                                )
                        ),
                        SearchOptions.searchOptions().index(searchIndex)
                )
        );

        return mongoTemplate.aggregate(Aggregation.newAggregation(facetOperation), Event.COLLECTION_NAME, EventMetadata.class).getUniqueMappedResult();
    }

    private SearchOperator initializeSearchOperator(String ressourceId, String eventId, Date eventDateMin, Date eventDateMax, String operationId, String operationName, Date operationDateMin, Date operationDateMax, StepTypeEnum stepType, String contextData) {
        List<SearchOperator> operators = new ArrayList<>();

        manageStringFieldOperator(operators, eventId, "ev_id");
        manageStringFieldOperator(operators, ressourceId, "operations.context.ressource_id");
        manageStringFieldOperator(operators, operationId, "operations.op_id");
        manageStringFieldOperator(operators, operationName, "operations.op_name");
        if (stepType != null) {
            operators.add(SearchOperator.text(SearchPath.fieldPath("operations.steps.type"), stepType.name()));
        }
        manageStringWildcardOperator(operators, contextData, "operations.context.*");
        manageDateFieldOperator(operators, eventDateMin, eventDateMax, "ev_date");
        manageDateFieldOperator(operators, operationDateMin, operationDateMax, "operations.op_date");

        return SearchOperator.compound().must(operators);
    }

    private void manageStringFieldOperator(List<SearchOperator> operators, String filter, String fieldPath) {
        if (StringUtils.isNotBlank(filter)) {
            operators.add(SearchOperator.text(SearchPath.fieldPath(fieldPath), filter));
        }
    }

    private void manageStringWildcardOperator(List<SearchOperator> operators, String filter, String wildcardPath) {
        if (StringUtils.isNotBlank(filter)) {
            operators.add(SearchOperator.text(SearchPath.wildcardPath(wildcardPath), filter));
        }
    }

    private void manageDateFieldOperator(List<SearchOperator> operators, Date dateMin, Date dateMax, String fieldPath) {
        if (dateMin != null || dateMax != null) {
            DateRangeSearchOperatorBase dateRangeOperatorBase = SearchOperator.dateRange(SearchPath.fieldPath(fieldPath));
            DateRangeSearchOperator dateRangeOperator;
            if (dateMin != null && dateMax != null) {
                dateRangeOperator = dateRangeOperatorBase.gteLte(dateMin.toInstant(), dateMax.toInstant());
            } else if (dateMin != null) {
                dateRangeOperator = dateRangeOperatorBase.gte(dateMin.toInstant());
            } else {
                dateRangeOperator = dateRangeOperatorBase.lte(dateMax.toInstant());
            }
            operators.add(dateRangeOperator);
        }
    }

    private Sort initializeSortParam(String sortValue) {
        Sort sort;

        if (StringUtils.isEmpty(sortValue)) {
            sort = Sort.by(List.of(Sort.Order.desc(Event.Fields.EVENT_DATE)));
        } else {
            String[] sortValues = sortValue.split(",");
            if (sortValues.length == 1) {
                sort = Sort.by(List.of(Sort.Order.by(sortValues[0])));
            } else if (sortValues.length == 2) {
                if ("ASC".equalsIgnoreCase(sortValues[1])) {
                    sort = Sort.by(List.of(Sort.Order.asc(sortValues[0])));
                } else if ("DESC".equalsIgnoreCase(sortValues[1])) {
                    sort = Sort.by(List.of(Sort.Order.desc(sortValues[0])));
                } else {
                    LOGGER.warn(String.format("Impossible d'interpréter le sens du tri (%s) -> application du sens de tri par défaut", sortValues[1]));
                    sort = Sort.by(List.of(Sort.Order.by(sortValues[0])));
                }
            } else {
                LOGGER.warn(String.format("Impossible d'interpréter le paramètre 'sort' (%s) -> application du tri descendant sur la date d'événement", sortValue));
                sort = Sort.by(List.of(Sort.Order.desc(Event.Fields.EVENT_DATE)));
            }
        }

        return sort;
    }
}
