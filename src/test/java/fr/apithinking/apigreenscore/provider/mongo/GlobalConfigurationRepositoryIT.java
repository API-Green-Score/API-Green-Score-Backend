package fr.apithinking.apigreenscore.provider.mongo;

import fr.apithinking.apigreenscore.ApiGreenScoreApplication;
import fr.apithinking.apigreenscore.TestUtils;
import fr.apithinking.apigreenscore.provider.mongo.model.CategoryMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.GlobalConfigurationMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.SectionMongo;
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
class GlobalConfigurationRepositoryIT {

    @Autowired
    private GlobalConfigurationRepository gcRepository;

    @BeforeEach
    void initDatabase() {
        insertData();
    }

    @AfterEach
    void cleanDatabase() {
        gcRepository.deleteAll();
    }

    @Test
    void should_findGlobalConfig_whenIdGiven() {

        GlobalConfigurationMongo gc = gcRepository.findById("ID-3").orElse(null);

        Assertions.assertNotNull(gc);
        Assertions.assertNotNull(gc.getId());
        Assertions.assertEquals("ID-3", gc.getId());
        Assertions.assertEquals(0.5, gc.getGlobalNote().floatValue());

        Assertions.assertNotNull(gc.getSections());
        Assertions.assertEquals(3, gc.getSections().size());
        assertSection("section1-3", 0.3, gc.getSections().get(0));
        assertSection("section2-3", 0.2, gc.getSections().get(1));
        assertSection("section3-3", 0.1, gc.getSections().get(2));

        Assertions.assertNotNull(gc.getCategories());
        Assertions.assertEquals(4, gc.getCategories().size());
        assertCategorie("A", "category1-3", 0.3, 0.8, gc.getCategories().get(0));
        assertCategorie("B", "category2-3", 0.6, 0.9, gc.getCategories().get(1));
        assertCategorie("C", "category3-3", 0.1, 0.3, gc.getCategories().get(2));
        assertCategorie("D", "category4-3", 0.2, 0.7, gc.getCategories().get(3));
    }

    private void assertSection(String expectedName, double expectedDefaultWeight, SectionMongo section) {
        Assertions.assertEquals(BigDecimal.valueOf(expectedDefaultWeight), section.getDefaultWeight());
        Assertions.assertEquals(expectedName, section.getName());
    }

    private void assertCategorie(
            String expectedLetter,
            String expectedName,
            double expectedRangeMin,
            double expectedRangeMax,
            CategoryMongo cat) {
        Assertions.assertEquals(expectedLetter, cat.getLetter());
        Assertions.assertEquals(expectedName, cat.getName());
        Assertions.assertEquals(BigDecimal.valueOf(expectedRangeMin), cat.getRangeMin());
        Assertions.assertEquals(BigDecimal.valueOf(expectedRangeMax), cat.getRangeMax());
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

    private GlobalConfigurationMongo insertData(int index) {
        GlobalConfigurationMongo gc = TestUtils.buildGlobalConfigurationMongo(index);
        return gcRepository.save(gc);
    }

}
