package fr.apithinking.apigreenscore.handler;

import fr.apithinking.apigreenscore.exception.NotFoundGlobalConfigurationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    public ProblemDetail handleNotFoundFavoriException(NotFoundGlobalConfigurationException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
