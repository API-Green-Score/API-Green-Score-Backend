package fr.apithinking.apigreenscore.controller;

import fr.apithinking.apigreenscore.model.Rule;
import fr.apithinking.apigreenscore.provider.mongo.model.RuleMongo;
import fr.apithinking.apigreenscore.services.RulesService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("ut")
class RuleApiControllerTest {

    private RulesApi ruleController;

    @Mock
    private RulesService rulesServiceMock;

    @BeforeEach
    void init() {
        ruleController = new RulesApiController(rulesServiceMock);
    }

    @Test
    void should_getRule_whenIdGiven() {
        String idRule = "IDRULE";

        ArgumentCaptor<String> idCapture = ArgumentCaptor.forClass(String.class);

        Rule ruleMock = Rule.builder().build();
        Mockito.doReturn(ruleMock).when(rulesServiceMock).getRule(idCapture.capture());

        Rule rule = ruleController.getRuleById(idRule);

        Mockito.verify(rulesServiceMock).getRule(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(rulesServiceMock);

        Assertions.assertEquals(ruleMock, rule);
        Assertions.assertNotNull(idCapture.getValue());
        Assertions.assertEquals(idRule, idCapture.getValue());
    }

    @Test
    void should_getRules_whenPageSizeAndNumberGiven() {
        int page = 0;
        int size = 10;

        ArgumentCaptor<Integer> pageCapture = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> sizeCapture = ArgumentCaptor.forClass(Integer.class);

        Page<RuleMongo> pageRuleMongo = new PageImpl<>(
                List.of(
                        RuleMongo.builder().build()
                ),
                PageRequest.of(page, size),
                15);

        Mockito.doReturn(pageRuleMongo).when(rulesServiceMock).getRules(
                pageCapture.capture(),
                sizeCapture.capture()
        );

        Page<Rule> rulesPage = ruleController.getRules(page, size);

        Mockito.verify(rulesServiceMock).getRules(Mockito.anyInt(), Mockito.anyInt());
        Mockito.verifyNoMoreInteractions(rulesServiceMock);

        Assertions.assertEquals(pageRuleMongo, rulesPage);
        Assertions.assertNotNull(pageCapture.getValue());
        Assertions.assertEquals(page, pageCapture.getValue());
        Assertions.assertNotNull(sizeCapture.getValue());
        Assertions.assertEquals(size, sizeCapture.getValue());
    }

}
