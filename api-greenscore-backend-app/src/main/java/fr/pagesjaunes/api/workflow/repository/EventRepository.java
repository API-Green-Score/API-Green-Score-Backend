package fr.pagesjaunes.api.workflow.repository;

import java.util.Optional;

import fr.pagesjaunes.api.workflow.model.Event;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {

    Optional<Event> findByEventId(String eventId);


}
