package fr.apithinking.apigreenscore.controller;

import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import fr.apithinking.apigreenscore.services.GlobalConfigurationService;
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

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("ut")
class GlobalConfigurationApiControllerTest {

    private GlobalConfigurationApi gcController;

    @Mock
    private GlobalConfigurationService gcServiceMock;

    @BeforeEach
    void init() {
        gcController = new GlobalConfigurationApiController(gcServiceMock);
    }

    @Test
    void should_getGlobalConfiguration_whenIdGiven() {
        String idgc = "IDGC";

        ArgumentCaptor<String> idCapture = ArgumentCaptor.forClass(String.class);

        GlobalConfiguration gcMock = GlobalConfiguration.builder().build();
        Mockito.doReturn(gcMock).when(gcServiceMock).getGlobalConfiguration(idCapture.capture());

        GlobalConfiguration gc = gcController.getGlobalConfiguration(idgc);

        Mockito.verify(gcServiceMock).getGlobalConfiguration(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(gcServiceMock);

        Assertions.assertEquals(gcMock, gc);
        Assertions.assertNotNull(idCapture.getValue());
        Assertions.assertEquals(idgc, idCapture.getValue());
    }

}
