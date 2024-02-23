package fr.apigreenscore.api;

import fr.apigreenscore.model.Rule;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

/**
 * A delegate to be called by the {@link RulesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-15T22:08:39.761998+01:00[Europe/Paris]")
public interface RulesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /rules/{ruleId} : Get rule by ID
     * get a single rule
     *
     * @param ruleId ID of rule to return (required)
     * @return Success (status code 200)
     *         or Invalid ID supplied (status code 400)
     *         or Rule not found (status code 404)
     * @see RulesApi#getRuleById
     */
    default ResponseEntity<Rule> getRuleById(String ruleId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"evaluation\" : true, \"score\" : 5.962133916683182, \"totalWeight\" : 6.027456183070403, \"description\" : \"description\", \"secionWeight\" : 0.8008281904610115, \"section\" : \"section\", \"comment\" : \"comment\", \"id\" : \"id\", \"itemsAnalyzed\" : \"itemsAnalyzed\", \"creationDate\" : \"2000-01-23T04:56:07.000+00:00\", \"points\" : 1.4658129805029452 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /rules : Get all available rules
     * Get all available rules with pagination and sorted by ruleId
     *
     * @param page Zero-based page index (optional, default to 0)
     * @param size The size of the page to be returned (optional, default to 20)
     * @return Success (status code 200)
     * @see RulesApi#getRules
     */
    default ResponseEntity<List<Rule>> getRules(Integer page,
        Integer size) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"evaluation\" : true, \"score\" : 5.962133916683182, \"totalWeight\" : 6.027456183070403, \"description\" : \"description\", \"secionWeight\" : 0.8008281904610115, \"section\" : \"section\", \"comment\" : \"comment\", \"id\" : \"id\", \"itemsAnalyzed\" : \"itemsAnalyzed\", \"creationDate\" : \"2000-01-23T04:56:07.000+00:00\", \"points\" : 1.4658129805029452 }, { \"evaluation\" : true, \"score\" : 5.962133916683182, \"totalWeight\" : 6.027456183070403, \"description\" : \"description\", \"secionWeight\" : 0.8008281904610115, \"section\" : \"section\", \"comment\" : \"comment\", \"id\" : \"id\", \"itemsAnalyzed\" : \"itemsAnalyzed\", \"creationDate\" : \"2000-01-23T04:56:07.000+00:00\", \"points\" : 1.4658129805029452 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
