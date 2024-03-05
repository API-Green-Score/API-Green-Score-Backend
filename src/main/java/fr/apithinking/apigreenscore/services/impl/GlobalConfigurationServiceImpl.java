package fr.apithinking.apigreenscore.services.impl;

import fr.apithinking.apigreenscore.exception.NotFoundGlobalConfigurationException;
import fr.apithinking.apigreenscore.mapper.ApiGreenscoreMapper;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import fr.apithinking.apigreenscore.provider.mongo.GlobalConfigurationRepository;
import fr.apithinking.apigreenscore.services.GlobalConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GlobalConfigurationServiceImpl implements GlobalConfigurationService {

    private final ApiGreenscoreMapper gcMapper;

    private final GlobalConfigurationRepository gcRepository;

    @Override
    public GlobalConfiguration getGlobalConfiguration(final String pId) {
        return gcMapper.buildGlobalConfiguration(
                gcRepository.findById(pId)
                        .orElseThrow(() -> new NotFoundGlobalConfigurationException("id", pId)));
    }

}
