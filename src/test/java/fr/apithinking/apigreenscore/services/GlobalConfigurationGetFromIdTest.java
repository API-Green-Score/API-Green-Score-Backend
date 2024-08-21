package fr.apithinking.apigreenscore.services;

import fr.apithinking.apigreenscore.exception.NotFoundGlobalConfigurationException;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("ut")
class GlobalConfigurationGetFromIdTest {

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
    void should_getGlobalConfiguration_whenIdGiven() {
        String idgc = "IDGC";

        ArgumentCaptor<String> idCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<GlobalConfigurationMongo> gcMongoCapture = ArgumentCaptor.forClass(GlobalConfigurationMongo.class);

        Optional<GlobalConfigurationMongo> gcMongoMock = Optional.of(GlobalConfigurationMongo.builder().build());
        Mockito.doReturn(gcMongoMock).when(gcRepositoryMock).findById(idCapture.capture());

        GlobalConfiguration gcMock = GlobalConfiguration.builder().build();
        Mockito.doReturn(gcMock).when(mapperMock).buildGlobalConfiguration(gcMongoCapture.capture());

        GlobalConfiguration gc = gcService.getGlobalConfiguration(idgc);

        Mockito.verify(gcRepositoryMock).findById(Mockito.anyString());
        Mockito.verify(mapperMock).buildGlobalConfiguration(Mockito.any(GlobalConfigurationMongo.class));
        Mockito.verifyNoMoreInteractions(gcRepositoryMock, mapperMock);

        Assertions.assertEquals(gcMock, gc);
        Assertions.assertNotNull(idCapture.getValue());
        Assertions.assertEquals(idgc, idCapture.getValue());
        Assertions.assertNotNull(gcMongoCapture.getValue());
        Assertions.assertEquals(gcMongoMock.get(), gcMongoCapture.getValue());
    }

    @Test
    void should_raiseNotFoundGlobalConfigurationException_whenInvalidIdGiven() {
        String idgc = "IDGC";

        ArgumentCaptor<String> idCapture = ArgumentCaptor.forClass(String.class);

        Optional<GlobalConfigurationMongo> gcMongoMock = Optional.empty();
        Mockito.doReturn(gcMongoMock).when(gcRepositoryMock).findById(idCapture.capture());

        try {
            gcService.getGlobalConfiguration(idgc);
            Assertions.fail("Should raise NotFoundGlobalConfigurationException");
        } catch (NotFoundGlobalConfigurationException ex) {
            Assertions.assertEquals("GlobalConfiguration with id '" + idgc + "' doesn't exist", ex.getMessage());
        } catch (Exception ex) {
            Assertions.fail("Should raise NotFoundGlobalConfigurationException and not a " + ex.getClass().getName() + " exception");
        }

        Mockito.verify(gcRepositoryMock).findById(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(gcRepositoryMock, mapperMock);

        Assertions.assertNotNull(idCapture.getValue());
        Assertions.assertEquals(idgc, idCapture.getValue());
    }

}
