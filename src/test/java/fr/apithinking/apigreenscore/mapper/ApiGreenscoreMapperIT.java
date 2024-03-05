package fr.apithinking.apigreenscore.mapper;

import fr.apithinking.apigreenscore.ApiGreenScoreApplication;
import fr.apithinking.apigreenscore.TestUtils;
import fr.apithinking.apigreenscore.model.Category;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import fr.apithinking.apigreenscore.model.Rule;
import fr.apithinking.apigreenscore.model.Section;
import fr.apithinking.apigreenscore.provider.mongo.model.CategoryMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.GlobalConfigurationMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.RuleMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.SectionMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ApiGreenScoreApplication.class)
@ActiveProfiles("it")
class ApiGreenscoreMapperIT {

    @Autowired
    private ApiGreenscoreMapper mapper;

    @Test
    void should_buildGlobalConfig_whenGlobalConfigMongoGiven() {
        GlobalConfigurationMongo gcMongo = TestUtils.buildGlobalConfigurationMongo(1);

        GlobalConfiguration gc = mapper.buildGlobalConfiguration(gcMongo);

        Assertions.assertNotNull(gc);
        Assertions.assertEquals(gcMongo.getGlobalNote(), gc.getGlobalNote());

        Assertions.assertNotNull(gc.getSections());
        Assertions.assertEquals(gcMongo.getSections().size(), gc.getSections().size());
        assertSection(gcMongo.getSections().get(0), gc.getSections().get(0));
        assertSection(gcMongo.getSections().get(1), gc.getSections().get(1));
        assertSection(gcMongo.getSections().get(2), gc.getSections().get(2));

        Assertions.assertNotNull(gc.getCategories());
        Assertions.assertEquals(gcMongo.getCategories().size(), gc.getCategories().size());
        assertCategory(gcMongo.getCategories().get(0), gc.getCategories().get(0));
        assertCategory(gcMongo.getCategories().get(1), gc.getCategories().get(1));
        assertCategory(gcMongo.getCategories().get(2), gc.getCategories().get(2));
        assertCategory(gcMongo.getCategories().get(3), gc.getCategories().get(3));
    }

    @Test
    void should_buildRule_whenRuleMongoGiven() {
        RuleMongo ruleMongo = TestUtils.buildRuleMongo(1);

        Rule rule = mapper.buildRule(ruleMongo);

        Assertions.assertNotNull(rule);
        Assertions.assertEquals(ruleMongo.getId(), rule.getId());
        Assertions.assertEquals(ruleMongo.getTitle(), rule.getTitle());
        Assertions.assertEquals(ruleMongo.getDescription(), rule.getDescription());
        Assertions.assertEquals(ruleMongo.getDefaultWeight(), rule.getDefaultWeight());

    }

    private void assertSection(SectionMongo sectionExpected, Section section) {
        Assertions.assertEquals(sectionExpected.getDefaultWeight(), section.getDefaultWeight());
        Assertions.assertEquals(sectionExpected.getName(), section.getName());
    }

    private void assertCategory(CategoryMongo categoryExpected, Category category) {
        Assertions.assertEquals(categoryExpected.getName(), category.getName());
        Assertions.assertEquals(categoryExpected.getLetter(), category.getLetter());
        Assertions.assertEquals(categoryExpected.getRangeMin(), category.getRangeMin());
        Assertions.assertEquals(categoryExpected.getRangeMax(), category.getRangeMax());
    }

}
