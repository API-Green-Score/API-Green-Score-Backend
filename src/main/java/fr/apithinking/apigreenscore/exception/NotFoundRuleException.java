package fr.apithinking.apigreenscore.exception;

public class NotFoundRuleException extends RuntimeException {

    private static final String NOT_FOUND_MESSAGE_PATTERN = "Rule with %1$s '%2$s' doesn't exist";

    public NotFoundRuleException(final String fieldWording, final String value) {
        super(String.format(NOT_FOUND_MESSAGE_PATTERN, fieldWording, value));
    }

}
