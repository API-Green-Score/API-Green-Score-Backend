package fr.apithinking.apigreenscore.services;

import fr.apithinking.apigreenscore.mapper.ApiGreenscoreMapper;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import fr.apithinking.apigreenscore.provider.mongo.GlobalConfigurationRepository;
import fr.apithinking.apigreenscore.provider.mongo.model.GlobalConfigurationMongo;
import fr.apithinking.apigreenscore.services.impl.GlobalConfigurationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("ut")
class GlobalConfigurationGetListPaginatedTest {

    private GlobalConfigurationService gcService;

    @Mock
    private GlobalConfigurationRepository gcRepositoryMock;

    @Mock
    private ApiGreenscoreMapper mapperMock;

    @BeforeEach
    void init() {
        gcService = new GlobalConfigurationServiceImpl(mapperMock, gcRepositoryMock);
    }

    @Test
    void should_returnEmptyPage_whenNoConfigurationsExist() {
        int page = 0;
        int size = 10;

        Mockito.doReturn(Page.empty()).when(gcRepositoryMock).findAll(Mockito.any(Pageable.class));

        Page<GlobalConfiguration> configPage = gcService.getGlobalConfigurations(page, size);

        Mockito.verify(gcRepositoryMock).findAll(Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(gcRepositoryMock, mapperMock);

        Assertions.assertNotNull(configPage);
        Assertions.assertTrue(configPage.getContent().isEmpty());
        Assertions.assertEquals(0, configPage.getTotalElements());
    }

    @Test
    void should_returnPaginatedConfigurations_whenConfigurationsExist() {
        int page = 0;
        int size = 10;

        List<GlobalConfigurationMongo> configsMongo = List.of(
                GlobalConfigurationMongo.builder().build(),
                GlobalConfigurationMongo.builder().build()
        );

        Mockito.doReturn(new PageImpl<>(configsMongo)).when(gcRepositoryMock).findAll(Mockito.any(Pageable.class));

        GlobalConfiguration config1 = GlobalConfiguration.builder().build();
        GlobalConfiguration config2 = GlobalConfiguration.builder().build();

        // Use lenient stubbing to avoid UnnecessaryStubbingException
        Mockito.lenient().doReturn(config1).when(mapperMock).buildGlobalConfiguration(configsMongo.get(0));
        Mockito.lenient().doReturn(config2).when(mapperMock).buildGlobalConfiguration(configsMongo.get(1));

        Page<GlobalConfiguration> configPage = gcService.getGlobalConfigurations(page, size);

        Mockito.verify(gcRepositoryMock).findAll(Mockito.any(Pageable.class));
        Mockito.verify(mapperMock, Mockito.times(2)).buildGlobalConfiguration(Mockito.any(GlobalConfigurationMongo.class));
        Mockito.verifyNoMoreInteractions(gcRepositoryMock, mapperMock);

        Assertions.assertNotNull(configPage);
        Assertions.assertEquals(2, configPage.getTotalElements());
        Assertions.assertEquals(config1, configPage.getContent().get(0));
        Assertions.assertEquals(config2, configPage.getContent().get(1));
    }

    @Test
    void should_returnUnpaged_whenPageSizeIsZero() {
        int page = 0;
        int size = 0;
        assertUnpagedResult(page, size);
    }

    @Test
    void should_returnUnpaged_whenPageSizeIsNegative() {
        int page = 0;
        int size = -1;
        assertUnpagedResult(page, size);
    }

    private void assertUnpagedResult(int page, int size) {
        long count = 15;

        Mockito.doReturn(count).when(gcRepositoryMock).count();

        Page<GlobalConfiguration> configPage = gcService.getGlobalConfigurations(page, size);

        Mockito.verify(gcRepositoryMock).count();
        Mockito.verifyNoMoreInteractions(gcRepositoryMock, mapperMock);

        Assertions.assertNotNull(configPage);
        Assertions.assertTrue(configPage.getPageable().isUnpaged());
        Assertions.assertEquals(count, configPage.getTotalElements());
    }

}
