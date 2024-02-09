package fr.pagesjaunes.utilisateur.handler;

import fr.pagesjaunes.utilisateur.ApiUtilisateurApplication;
import fr.pagesjaunes.utilisateur.exception.NotFoundUserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ApiUtilisateurApplication.class)
@ActiveProfiles("it")
class ExceptionHandlerAdviceIT {

    @Autowired
    ExceptionHandlerAdvice handler;

    @Test
    void handleNotFoundOperationExceptionTest() {
        NotFoundUserException nfException = new NotFoundUserException("USER_ID", "id");

        ProblemDetail response = handler.handleNotFoundUserException(nfException);

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void handleIllegalArgumentExceptionTest() {
        IllegalArgumentException iaException = new IllegalArgumentException("Exception remontée par @Min par exemple");

        ProblemDetail response = handler.handleIllegalArgumentException(iaException);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

}
