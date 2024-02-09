package fr.apithinking.apigreenscore.service;

import fr.apithinking.apigreenscore.controller.GlobalConfigurationApi;
import fr.apithinking.apigreenscore.controller.GlobalConfigurationApiController;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import org.springframework.http.ResponseEntity;

/**
 * A delegate to be called by the {@link GlobalConfigurationApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
public interface GlobalConfigurationApiDelegate {

    /**
     * GET /global_configuration : Get global configuration data
     * Get all data about global configuration of evaluation grid (table notes, section weigths, global note)
     *
     * @return Success (status code 200)
     * @see GlobalConfigurationApi#getGlobalConfiguration
     */
    ResponseEntity<GlobalConfiguration> getGlobalConfiguration();

}
