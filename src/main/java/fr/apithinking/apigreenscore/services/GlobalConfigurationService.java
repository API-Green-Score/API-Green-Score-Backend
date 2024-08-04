package fr.apithinking.apigreenscore.services;

import fr.apithinking.apigreenscore.controller.GlobalConfigurationApiController;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import org.springframework.data.domain.Page;

/**
 * A delegate to be called by the {@link GlobalConfigurationApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
public interface GlobalConfigurationService {

    Page<GlobalConfiguration> getGlobalConfigurations(
            Integer page,
            Integer size);

    GlobalConfiguration getGlobalConfiguration(String pId);

}
