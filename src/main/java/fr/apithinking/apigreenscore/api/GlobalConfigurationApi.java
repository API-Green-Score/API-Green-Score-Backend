package fr.apithinking.apigreenscore.api;

import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import jakarta.validation.constraints.NotBlank;

public interface GlobalConfigurationApi {

    /**
     * GET /global_configuration : Get global configuration data by id
     * Get all data about global configuration of evaluation grid (table notes, section weigths, global note)
     *
     * @return Success (status code 200)
     */
    GlobalConfiguration getGlobalConfiguration(
            @NotBlank(message = "Global configuration identifier must not be blank")
            String pIdGlobalConf);

}
