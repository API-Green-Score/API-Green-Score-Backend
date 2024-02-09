package fr.apithinking.apigreenscore.controller;

import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import org.springframework.http.ResponseEntity;

public interface GlobalConfigurationApi {

    /**
     * GET /global_configuration : Get global configuration data
     * Get all data about global configuration of evaluation grid (table notes, section weigths, global note)
     *
     * @return Success (status code 200)
     */
    ResponseEntity<GlobalConfiguration> getGlobalConfiguration();

}
