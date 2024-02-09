package fr.apithinking.apigreenscore.exception;

public class NotFoundGlobalConfigurationException extends RuntimeException {

    private static final String NOT_FOUND_MESSAGE_PATTERN = "Global configuration with id %1$s doesn't exist";

    public NotFoundGlobalConfigurationException(final String id) {
        super(String.format(NOT_FOUND_MESSAGE_PATTERN, id));
    }
}
