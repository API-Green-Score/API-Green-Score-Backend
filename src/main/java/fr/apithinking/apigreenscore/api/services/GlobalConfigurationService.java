package fr.apithinking.apigreenscore.api.services;

import fr.apithinking.apigreenscore.api.controller.GlobalConfigurationApiController;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;

/**
 * A delegate to be called by the {@link GlobalConfigurationApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
public interface GlobalConfigurationService {

    GlobalConfiguration getGlobalConfiguration(String pId);

}
