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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("ut")
class RulesServiceTest {

    private RulesService rulesService;

    @Mock
    private RuleRepository ruleRepositoryMock;

    @Mock
    private ApiGreenscoreMapper mapperMock;

    @BeforeEach
    public void init() {
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

    @Test
    void should_getOnlyCount_whenGetRulesCalled_givenPageSize0() {
        int page = 0;
        int size = 0;

        long count = 15;

        Mockito.doReturn(count).when(ruleRepositoryMock).count();

        Page<Rule> rulePage = rulesService.getRules(page, size);

        Mockito.verify(ruleRepositoryMock).count();
        Mockito.verifyNoMoreInteractions(ruleRepositoryMock, mapperMock);

        Assertions.assertNotNull(rulePage);
        Assertions.assertNotNull(rulePage.getContent());
        Assertions.assertEquals(0, rulePage.getNumber());
        Assertions.assertEquals(0, rulePage.getNumberOfElements());
        Assertions.assertEquals(1, rulePage.getTotalPages());
        Assertions.assertEquals(0, rulePage.getSize());
        Assertions.assertEquals(15, rulePage.getTotalElements());
        Assertions.assertEquals(0, rulePage.getContent().size());
        Assertions.assertEquals(Sort.unsorted(), rulePage.getSort());
        Assertions.assertNotNull(rulePage.getPageable());
        Assertions.assertTrue(rulePage.getPageable().isUnpaged());
    }

    @Test
    void should_getEmptyRuleList_whenSizeGreaterThan0Given() {
        int page = 0;
        int size = 10;

        ArgumentCaptor<Pageable> pageableCapture = ArgumentCaptor.forClass(Pageable.class);
        Mockito.doReturn(Page.empty()).when(ruleRepositoryMock).findAll(pageableCapture.capture());

        Page<Rule> rulePage = rulesService.getRules(page, size);

        Mockito.verify(ruleRepositoryMock).findAll(Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(ruleRepositoryMock, mapperMock);

        Assertions.assertNotNull(rulePage);
        Assertions.assertNotNull(rulePage.getContent());
        Assertions.assertEquals(0, rulePage.getNumber());
        Assertions.assertEquals(0, rulePage.getNumberOfElements());
        Assertions.assertEquals(0, rulePage.getTotalPages());
        Assertions.assertEquals(10, rulePage.getSize());
        Assertions.assertEquals(-1, rulePage.getTotalElements());
        Assertions.assertEquals(0, rulePage.getContent().size());
        Assertions.assertEquals(Sort.unsorted(), rulePage.getSort());
        Assertions.assertNotNull(rulePage.getPageable());
        Assertions.assertFalse(rulePage.getPageable().isUnpaged());
    }

}
