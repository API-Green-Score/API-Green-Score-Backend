package fr.apithinking.apigreenscore.services;

import fr.apithinking.apigreenscore.exception.NotFoundRuleException;
import fr.apithinking.apigreenscore.mapper.ApiGreenscoreMapper;
import fr.apithinking.apigreenscore.model.Rule;
import fr.apithinking.apigreenscore.provider.mongo.RuleRepository;
import fr.apithinking.apigreenscore.provider.mongo.model.RuleMongo;
import fr.apithinking.apigreenscore.services.impl.RuleServiceImpl;
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
class RulesServiceGetFromIdTest {

    private RulesService rulesService;

    @Mock
    private RuleRepository ruleRepositoryMock;

    @Mock
    private ApiGreenscoreMapper mapperMock;

    @BeforeEach
    void init() {
        rulesService = new RuleServiceImpl(mapperMock, ruleRepositoryMock);
    }

    @Test
    void should_getRule_whenIdGiven() {
        String idRule = "IDRULE";

        ArgumentCaptor<String> idCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<RuleMongo> ruleMongoCapture = ArgumentCaptor.forClass(RuleMongo.class);

        Optional<RuleMongo> ruleMongoMock = Optional.of(RuleMongo.builder().build());
        Mockito.doReturn(ruleMongoMock).when(ruleRepositoryMock).findById(idCapture.capture());

        Rule ruleMock = Rule.builder().build();
        Mockito.doReturn(ruleMock).when(mapperMock).buildRule(ruleMongoCapture.capture());

        Rule rule = rulesService.getRule(idRule);

        Mockito.verify(ruleRepositoryMock).findById(Mockito.anyString());
        Mockito.verify(mapperMock).buildRule(Mockito.any(RuleMongo.class));
        Mockito.verifyNoMoreInteractions(ruleRepositoryMock, mapperMock);

        Assertions.assertEquals(ruleMock, rule);
        Assertions.assertNotNull(idCapture.getValue());
        Assertions.assertEquals(idRule, idCapture.getValue());
        Assertions.assertNotNull(ruleMongoCapture.getValue());
        Assertions.assertEquals(ruleMongoMock.get(), ruleMongoCapture.getValue());
    }

    @Test
    void should_raiseNotFoundRuleException_whenInvalidIdGiven() {
        String idRule = "IDRULE";

        ArgumentCaptor<String> idCapture = ArgumentCaptor.forClass(String.class);

        Optional<RuleMongo> ruleMongoMock = Optional.empty();
        Mockito.doReturn(ruleMongoMock).when(ruleRepositoryMock).findById(idCapture.capture());

        try {
            rulesService.getRule(idRule);
            Assertions.fail("Should raise NotFoundRuleException");
        } catch (NotFoundRuleException ex) {
            Assertions.assertEquals("Rule with id '" + idRule + "' doesn't exist", ex.getMessage());
        } catch (Exception ex) {
            Assertions.fail("Should raise NotFoundRuleException and not a " + ex.getClass().getName() + " exception");
        }

        Mockito.verify(ruleRepositoryMock).findById(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(ruleRepositoryMock, mapperMock);

        Assertions.assertNotNull(idCapture.getValue());
        Assertions.assertEquals(idRule, idCapture.getValue());
    }

}
