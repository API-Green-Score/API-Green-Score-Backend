package fr.apithinking.apigreenscore.services.impl;

import fr.apithinking.apigreenscore.exception.NotFoundGlobalConfigurationException;
import fr.apithinking.apigreenscore.mapper.ApiGreenscoreMapper;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import fr.apithinking.apigreenscore.provider.mongo.GlobalConfigurationRepository;
import fr.apithinking.apigreenscore.provider.mongo.model.GlobalConfigurationMongo;
import fr.apithinking.apigreenscore.services.GlobalConfigurationService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class GlobalConfigurationServiceImpl implements GlobalConfigurationService {

    private final ApiGreenscoreMapper gcMapper;

    private final GlobalConfigurationRepository gcRepository;

    @Override
    public Page<GlobalConfiguration> getGlobalConfigurations(Integer page, Integer size) {

        long configsTotalCount = 0;
        List<GlobalConfiguration> configs = new ArrayList<>();

        if (size <= 0) {
            configsTotalCount = gcRepository.count();
            return new PageImpl<>(configs, Pageable.unpaged(), configsTotalCount);
        }

        List<GlobalConfigurationMongo> configsMongo = gcRepository.findAll(PageRequest.of(page, size)).getContent();
        if (CollectionUtils.isNotEmpty(configsMongo)) {
            for (GlobalConfigurationMongo configMongo : configsMongo) {
                GlobalConfiguration config = gcMapper.buildGlobalConfiguration(configMongo);
                configs.add(config);
            }
        }

        return new PageImpl<>(configs, PageRequest.of(page, size), configsTotalCount);
    }

    @Override
    public GlobalConfiguration getGlobalConfiguration(final String pId) {
        return gcMapper.buildGlobalConfiguration(
                gcRepository.findById(pId)
                        .orElseThrow(() -> new NotFoundGlobalConfigurationException("id", pId)));
    }

}
