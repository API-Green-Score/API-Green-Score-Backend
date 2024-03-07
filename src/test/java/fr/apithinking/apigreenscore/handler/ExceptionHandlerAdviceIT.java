package fr.apithinking.apigreenscore.handler;

import com.mongodb.MongoWriteException;
import com.mongodb.WriteError;
import fr.apithinking.apigreenscore.ApiGreenScoreApplication;
import fr.apithinking.apigreenscore.exception.NotFoundGlobalConfigurationException;
import fr.apithinking.apigreenscore.exception.NotFoundRuleException;
import fr.apithinking.apigreenscore.exception.handler.ExceptionHandlerAdvice;
import jakarta.validation.ConstraintViolationException;
import org.bson.BsonDocument;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

@SpringBootTest(classes = ApiGreenScoreApplication.class)
@ActiveProfiles("it")
class ExceptionHandlerAdviceIT {

    @Autowired
    ExceptionHandlerAdvice handler;

    @Test
    void should_handleNotFoundOperationException() {
        NotFoundRuleException nfException = new NotFoundRuleException("RULE_ID", "id");

        ProblemDetail response = handler.handleNotFoundRuleException(nfException);

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        Assertions.assertEquals("Rule with RULE_ID 'id' doesn't exist", response.getDetail());
    }

    @Test
    void should_handleNotFoundGlobalConfigurationException() {
        NotFoundGlobalConfigurationException nfException = new NotFoundGlobalConfigurationException("GC_ID", "id");

        ProblemDetail response = handler.handleNotFoundGlobalConfigurationException(nfException);

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        Assertions.assertEquals("GlobalConfiguration with GC_ID 'id' doesn't exist", response.getDetail());
    }

    @Test
    void should_handleIllegalArgumentException() {
        IllegalArgumentException iaException = new IllegalArgumentException("Exception remontée par @Min par exemple");

        ProblemDetail response = handler.handleIllegalArgumentException(iaException);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void should_handleMongoWriteExceptionWhenOtherMongoWriteError() {
        MongoWriteException ex = new MongoWriteException(new WriteError(1, "Other write mongo error", new BsonDocument()), null);

        ProblemDetail response = handler.handleMongoWriteException(ex);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        Assertions.assertTrue(response.getDetail().startsWith("Error during creation of data in the database : "));
        Assertions.assertTrue(response.getDetail().contains("Other write mongo error"));
    }

    @Test
    void should_handleConstraintViolationException() {
        ConstraintViolationException ex = new ConstraintViolationException(
                "Error message exception",
                Set.of(ConstraintViolationImpl.forParameterValidation(
                        "Error message parameter / messageTemplate",
                        null,
                        null,
                        "Error message parameter / interpolatedMessage",
                        null,
                        null,
                        null,
                        "Error message parameter / value",
                        null,
                        null,
                        null,
                        null
                )));

        ProblemDetail response = handler.handleConstraintViolationException(ex);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        Assertions.assertEquals("[Error message parameter / interpolatedMessage]", response.getDetail());
    }

}
