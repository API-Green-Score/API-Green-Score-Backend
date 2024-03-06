package fr.apithinking.apigreenscore.provider.mongo;

import fr.apithinking.apigreenscore.ApiGreenScoreApplication;
import fr.apithinking.apigreenscore.TestUtils;
import fr.apithinking.apigreenscore.provider.mongo.model.RuleMongo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest(classes = ApiGreenScoreApplication.class)
@ActiveProfiles("it")
class RuleRepositoryIT {

    @Autowired
    private RuleRepository ruleRepository;

    @BeforeEach
    void initDatabase() {
        insertData();
    }

    @AfterEach
    void cleanDatabase() {
        ruleRepository.deleteAll();
    }

    @Test
    void should_findRuleMongo_whenIdGiven() {

        RuleMongo rule = ruleRepository.findById("ID-3").orElse(null);

        Assertions.assertNotNull(rule);
        Assertions.assertNotNull(rule.getId());
        Assertions.assertEquals("ID-3", rule.getId());
        Assertions.assertEquals("title-3", rule.getTitle());
        Assertions.assertEquals("description-3", rule.getDescription());
        Assertions.assertEquals(BigDecimal.valueOf(0.4), rule.getDefaultWeight());
    }

    private void insertData() {
        insertData(1);
        insertData(5);
        insertData(6);
        insertData(2);
        insertData(3);
        insertData(7);
        insertData(8);
        insertData(4);
        insertData(9);
    }

    private RuleMongo insertData(int index) {
        RuleMongo rule = TestUtils.buildRuleMongo(index);
        return ruleRepository.save(rule);
    }

}
