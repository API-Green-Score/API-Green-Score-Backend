package fr.apithinking.apigreenscore.provider.mongo;

import fr.apithinking.apigreenscore.ApiGreenScoreApplication;
import fr.apithinking.apigreenscore.TestUtils;
import fr.apithinking.apigreenscore.provider.mongo.model.GlobalConfigurationMongo;
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
public class GlobalConfigurationRepositoryIT {

    @Autowired
    private GlobalConfigurationRepository gcRepository;

    @BeforeEach
    public void initDatabase() {
        insertData();
    }

    @AfterEach
    public void cleanDatabase() {
        gcRepository.deleteAll();
    }

    @Test
    void should_find_globalConfig_when_id_given() {

        GlobalConfigurationMongo gc = gcRepository.findById("ID3").orElse(null);

        Assertions.assertNotNull(gc);
        Assertions.assertNotNull(gc.getId());
        Assertions.assertEquals("ID3", gc.getId());
        Assertions.assertEquals(0.5, gc.getGlobalNote().floatValue());

        Assertions.assertNotNull(gc.getSections());
        Assertions.assertEquals(3, gc.getSections().size());
        Assertions.assertEquals(BigDecimal.valueOf(0.3), gc.getSections().get(0).getDefaultWeight());
        Assertions.assertEquals("section1-3", gc.getSections().get(0).getName());
        Assertions.assertEquals(BigDecimal.valueOf(0.2), gc.getSections().get(1).getDefaultWeight());
        Assertions.assertEquals("section2-3", gc.getSections().get(1).getName());
        Assertions.assertEquals(BigDecimal.valueOf(0.1), gc.getSections().get(2).getDefaultWeight());
        Assertions.assertEquals("section3-3", gc.getSections().get(2).getName());

        Assertions.assertNotNull(gc.getCategories());
        Assertions.assertEquals(4, gc.getCategories().size());
        Assertions.assertEquals("A", gc.getCategories().get(0).getLetter());
        Assertions.assertEquals("category1-3", gc.getCategories().get(0).getName());
        Assertions.assertEquals(BigDecimal.valueOf(0.3), gc.getCategories().get(0).getRangeMin());
        Assertions.assertEquals(BigDecimal.valueOf(0.8), gc.getCategories().get(0).getRangeMax());
        Assertions.assertEquals("B", gc.getCategories().get(1).getLetter());
        Assertions.assertEquals("category2-3", gc.getCategories().get(1).getName());
        Assertions.assertEquals(BigDecimal.valueOf(0.6), gc.getCategories().get(1).getRangeMin());
        Assertions.assertEquals(BigDecimal.valueOf(0.9), gc.getCategories().get(1).getRangeMax());
        Assertions.assertEquals("C", gc.getCategories().get(2).getLetter());
        Assertions.assertEquals("category3-3", gc.getCategories().get(2).getName());
        Assertions.assertEquals(BigDecimal.valueOf(0.1), gc.getCategories().get(2).getRangeMin());
        Assertions.assertEquals(BigDecimal.valueOf(0.3), gc.getCategories().get(2).getRangeMax());
        Assertions.assertEquals("D", gc.getCategories().get(3).getLetter());
        Assertions.assertEquals("category4-3", gc.getCategories().get(3).getName());
        Assertions.assertEquals(BigDecimal.valueOf(0.2), gc.getCategories().get(3).getRangeMin());
        Assertions.assertEquals(BigDecimal.valueOf(0.7), gc.getCategories().get(3).getRangeMax());
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
