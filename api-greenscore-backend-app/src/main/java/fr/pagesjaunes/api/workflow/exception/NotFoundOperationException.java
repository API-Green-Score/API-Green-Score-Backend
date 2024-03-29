package fr.pagesjaunes.api.workflow.exception;

public class NotFoundOperationException extends RuntimeException {

    private static final String NOT_FOUND_MESSAGE_PATTERN = "L'opération avec l'id %2$s n'a pas été trouvé au sein de l'événement d'id %1$s.";

    public NotFoundOperationException(final String eventId, final String operationId) {
        super(String.format(NOT_FOUND_MESSAGE_PATTERN, eventId, operationId));
    }
}
