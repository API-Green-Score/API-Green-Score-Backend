package fr.apithinking.apigreenscore.services;

import fr.apithinking.apigreenscore.TestUtils;
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
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
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
    void should_getRuleList_whenSizeGreaterThan0() {
        int page = 0;
        int size = 10;

        RuleMongo ruleMongo1 = TestUtils.buildRuleMongo(1);
        RuleMongo ruleMongo2 = TestUtils.buildRuleMongo(2);
        RuleMongo ruleMongo3 = TestUtils.buildRuleMongo(3);
        RuleMongo ruleMongo4 = TestUtils.buildRuleMongo(4);
        RuleMongo ruleMongo5 = TestUtils.buildRuleMongo(5);
        RuleMongo ruleMongo6 = TestUtils.buildRuleMongo(6);
        RuleMongo ruleMongo7 = TestUtils.buildRuleMongo(7);
        RuleMongo ruleMongo8 = TestUtils.buildRuleMongo(8);
        RuleMongo ruleMongo9 = TestUtils.buildRuleMongo(9);
        RuleMongo ruleMongo10 = TestUtils.buildRuleMongo(10);


        Page<RuleMongo> pageRuleMongo = new PageImpl<>(
                List.of(
                        ruleMongo1,
                        ruleMongo2,
                        ruleMongo3,
                        ruleMongo4,
                        ruleMongo5,
                        ruleMongo6,
                        ruleMongo7,
                        ruleMongo8,
                        ruleMongo9,
                        ruleMongo10
                ),
                PageRequest.of(page, size),
                15);
        ArgumentCaptor<Pageable> pageableCapture = ArgumentCaptor.forClass(Pageable.class);
        Mockito.doReturn(pageRuleMongo).when(ruleRepositoryMock).findAll(pageableCapture.capture());

        ArgumentCaptor<RuleMongo> ruleMongoCapture = ArgumentCaptor.forClass(RuleMongo.class);
        Mockito.doReturn(Rule.builder().build()).when(mapperMock).buildRule(ruleMongoCapture.capture());

        Page<Rule> rulePage = rulesService.getRules(page, size);

        Mockito.verify(ruleRepositoryMock).findAll(Mockito.any(Pageable.class));
        Mockito.verify(mapperMock, Mockito.times(10)).buildRule(Mockito.any(RuleMongo.class));
        Mockito.verifyNoMoreInteractions(ruleRepositoryMock, mapperMock);

        assertRulePage(rulePage);

        assertPageableCapture(pageableCapture);

        assertRuleMongoCapture(ruleMongoCapture, ruleMongo1, ruleMongo2, ruleMongo3, ruleMongo4, ruleMongo5, ruleMongo6, ruleMongo7, ruleMongo8, ruleMongo9, ruleMongo10);
    }

    private static void assertRuleMongoCapture(ArgumentCaptor<RuleMongo> ruleMongoCapture, RuleMongo ruleMongo1, RuleMongo ruleMongo2, RuleMongo ruleMongo3, RuleMongo ruleMongo4, RuleMongo ruleMongo5, RuleMongo ruleMongo6, RuleMongo ruleMongo7, RuleMongo ruleMongo8, RuleMongo ruleMongo9, RuleMongo ruleMongo10) {
        Assertions.assertNotNull(ruleMongoCapture.getValue());
        Assertions.assertNotNull(ruleMongoCapture.getAllValues());
        Assertions.assertEquals(10, ruleMongoCapture.getAllValues().size());
        Assertions.assertEquals(ruleMongo1, ruleMongoCapture.getAllValues().get(0));
        Assertions.assertEquals(ruleMongo2, ruleMongoCapture.getAllValues().get(1));
        Assertions.assertEquals(ruleMongo3, ruleMongoCapture.getAllValues().get(2));
        Assertions.assertEquals(ruleMongo4, ruleMongoCapture.getAllValues().get(3));
        Assertions.assertEquals(ruleMongo5, ruleMongoCapture.getAllValues().get(4));
        Assertions.assertEquals(ruleMongo6, ruleMongoCapture.getAllValues().get(5));
        Assertions.assertEquals(ruleMongo7, ruleMongoCapture.getAllValues().get(6));
        Assertions.assertEquals(ruleMongo8, ruleMongoCapture.getAllValues().get(7));
        Assertions.assertEquals(ruleMongo9, ruleMongoCapture.getAllValues().get(8));
        Assertions.assertEquals(ruleMongo10, ruleMongoCapture.getAllValues().get(9));
    }

    private static void assertPageableCapture(ArgumentCaptor<Pageable> pageableCapture) {
        Assertions.assertNotNull(pageableCapture.getValue());
        Assertions.assertEquals(0, pageableCapture.getValue().getPageNumber());
        Assertions.assertEquals(10, pageableCapture.getValue().getPageSize());
    }

    private static void assertRulePage(Page<Rule> rulePage) {
        Assertions.assertNotNull(rulePage);
        Assertions.assertNotNull(rulePage.getContent());
        Assertions.assertEquals(10, rulePage.getContent().size());
        Assertions.assertEquals(0, rulePage.getNumber());
        Assertions.assertEquals(10, rulePage.getNumberOfElements());
        Assertions.assertEquals(1, rulePage.getTotalPages());
        Assertions.assertEquals(10, rulePage.getSize());
        Assertions.assertEquals(10, rulePage.getTotalElements());
        Assertions.assertEquals(Sort.unsorted(), rulePage.getSort());
        Assertions.assertNotNull(rulePage.getPageable());
        Assertions.assertFalse(rulePage.getPageable().isUnpaged());
    }

}
