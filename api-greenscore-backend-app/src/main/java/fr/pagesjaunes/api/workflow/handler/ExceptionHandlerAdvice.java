package fr.pagesjaunes.api.workflow.handler;

import fr.pagesjaunes.api.workflow.exception.BadRequestSearchEventException;
import fr.pagesjaunes.api.workflow.exception.NotFoundEventException;
import fr.pagesjaunes.api.workflow.exception.NotFoundOperationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    public ProblemDetail handleNotFoundEvent(NotFoundEventException e, NativeWebRequest request) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    public ProblemDetail handleNotFoundOperation(NotFoundOperationException e, NativeWebRequest request) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    public ProblemDetail handleBadRequestSearchEventException(BadRequestSearchEventException e, NativeWebRequest request) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
