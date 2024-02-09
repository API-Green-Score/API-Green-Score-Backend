package fr.apigreenscore.api;

import fr.apigreenscore.model.GlobalConfiguration;
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
 * A delegate to be called by the {@link GlobalConfigurationApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-15T22:08:39.761998+01:00[Europe/Paris]")
public interface GlobalConfigurationApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /global_configuration : Get global configuration data
     * Get all data about global configuration of evaluation grid (table notes, section weigths, global note)
     *
     * @return Success (status code 200)
     * @see GlobalConfigurationApi#getGlobalConfiguration
     */
    default ResponseEntity<GlobalConfiguration> getGlobalConfiguration() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"globalNote\" : 0.8008281904610115, \"categories\" : [ { \"rangeMax\" : 5.962133916683182, \"letter\" : \"letter\", \"rangeMin\" : 1.4658129805029452, \"name\" : \"name\" }, { \"rangeMax\" : 5.962133916683182, \"letter\" : \"letter\", \"rangeMin\" : 1.4658129805029452, \"name\" : \"name\" } ], \"sections\" : [ { \"name\" : \"name\", \"weight\" : 6.027456183070403 }, { \"name\" : \"name\", \"weight\" : 6.027456183070403 } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
