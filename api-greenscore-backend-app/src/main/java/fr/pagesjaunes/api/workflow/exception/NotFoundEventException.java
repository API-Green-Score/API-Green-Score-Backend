package fr.pagesjaunes.api.workflow.exception;

public class NotFoundEventException extends RuntimeException {

    private static final String NOT_FOUND_MESSAGE_PATTERN = "L'événement avec l'id %1$s n'a pas été trouvé en base.";

    public NotFoundEventException(final String eventId) {
        super(String.format(NOT_FOUND_MESSAGE_PATTERN, eventId));
    }
}
