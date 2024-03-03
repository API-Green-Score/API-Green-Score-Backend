package fr.apithinking.apigreenscore.exception;

public class NotFoundGlobalConfigurationException extends RuntimeException {

    private static final String NOT_FOUND_MESSAGE_PATTERN = "GlobalConfiguration with %1$s '%2$s' doesn't exist";

    public NotFoundGlobalConfigurationException(final String fieldWording, final String value) {
        super(String.format(NOT_FOUND_MESSAGE_PATTERN, fieldWording, value));
    }

}
