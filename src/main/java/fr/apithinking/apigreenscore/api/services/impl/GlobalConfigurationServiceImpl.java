package fr.apithinking.apigreenscore.api.services.impl;

import fr.apithinking.apigreenscore.api.services.GlobalConfigurationService;
import fr.apithinking.apigreenscore.exception.NotFoundGlobalConfigurationException;
import fr.apithinking.apigreenscore.mapper.ApiGreenscoreMapper;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import fr.apithinking.apigreenscore.provider.mongo.GlobalConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GlobalConfigurationServiceImpl implements GlobalConfigurationService {

    @Autowired
    private ApiGreenscoreMapper gcMapper;

    @Autowired
    private GlobalConfigurationRepository gcRepository;

    @Override
    public GlobalConfiguration getGlobalConfiguration(final String pId) {
        return gcMapper.buildGlobalConfiguration(
                gcRepository.findById(pId)
                        .orElseThrow(() -> new NotFoundGlobalConfigurationException("id", pId)));
    }

}
