package fr.apithinking.apigreenscore.handler;

import fr.apithinking.apigreenscore.ApiGreenscoreApplication;
import fr.apithinking.apigreenscore.exception.NotFoundGlobalConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ApiGreenscoreApplication.class)
@ActiveProfiles("it")
class ExceptionHandlerAdviceIT {

    @Autowired
    ExceptionHandlerAdvice handler;

    @Test
    void shouldHandleNotFoundException() {
        NotFoundGlobalConfigurationException nfException = new NotFoundGlobalConfigurationException("MY_ID");
        ProblemDetail response = handler.handleNotFoundFavoriException(nfException);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void shouldHandleIllegalArgumentException() {
        IllegalArgumentException iaException = new IllegalArgumentException("My Exception message");
        ProblemDetail response = handler.handleIllegalArgumentException(iaException);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

}
