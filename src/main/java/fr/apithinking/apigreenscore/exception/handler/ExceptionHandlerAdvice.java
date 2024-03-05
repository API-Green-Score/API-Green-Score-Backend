package fr.apithinking.apigreenscore.exception.handler;

import com.mongodb.MongoWriteException;
import fr.apithinking.apigreenscore.exception.NotFoundGlobalConfigurationException;
import fr.apithinking.apigreenscore.exception.NotFoundRuleException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    public ProblemDetail handleNotFoundGlobalConfigurationException(NotFoundGlobalConfigurationException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    public ProblemDetail handleNotFoundRuleException(NotFoundRuleException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    public ProblemDetail handleMongoWriteException(MongoWriteException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error during creation of data in the database : " + e.getMessage());
    }

    // validation error managment for type @NotNull for example, ... inside beans to be validated
    @ExceptionHandler
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorMessages = ex
                .getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessages.toString());
    }

}
