package fr.apithinking.apigreenscore.coreweb.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Generic error handler for converting exceptions to 'Problem' responses.
 */
@RestControllerAdvice
public class RestErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    ProblemDetail handleException(Exception e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return problemDetail;
    }
}
