package fr.apithinking.apigreenscore.exception;

public class NotFoundUserException extends RuntimeException {

    private static final String NOT_FOUND_MESSAGE_PATTERN = "L'utilisateur avec l'%1$s '%2$s' n'a pas été trouvé en base.";

    public NotFoundUserException(final String field, String fieldWording) {
        super(String.format(NOT_FOUND_MESSAGE_PATTERN, fieldWording, field));
    }
}
