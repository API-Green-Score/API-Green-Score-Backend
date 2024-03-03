package fr.apithinking.apigreenscore.mapper;

import fr.apithinking.apigreenscore.ApiGreenScoreApplication;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import fr.apithinking.apigreenscore.provider.mongo.model.CategoryMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.GlobalConfigurationMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.SectionMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(classes = ApiGreenScoreApplication.class)
@ActiveProfiles("it")
public class ApiGreenscoreMapperIT {

    @Autowired
    private ApiGreenscoreMapper mapper;

    @Test
    void shouldBuildGlobalConfigurationMongoWhenGlobalConfigurationGiven() {
        GlobalConfigurationMongo gcMongo = GlobalConfigurationMongo.builder()
                .globalNote(BigDecimal.valueOf(0.5))
                .sections(List.of(
                        SectionMongo.builder()
                                .name("section1")
                                .defaultWeight(BigDecimal.valueOf(0.3))
                                .build(),
                        SectionMongo.builder()
                                .name("section2")
                                .defaultWeight(BigDecimal.valueOf(0.2))
                                .build(),
                        SectionMongo.builder()
                                .name("section3")
                                .defaultWeight(BigDecimal.valueOf(0.1))
                                .build()
                ))
                .categories(List.of(
                        CategoryMongo.builder()
                                .name("category1")
                                .letter("A")
                                .rangeMin(BigDecimal.valueOf(0.3))
                                .rangeMax(BigDecimal.valueOf(0.8))
                                .build(),
                        CategoryMongo.builder()
                                .name("category2")
                                .letter("B")
                                .rangeMin(BigDecimal.valueOf(0.6))
                                .rangeMax(BigDecimal.valueOf(0.9))
                                .build(),
                        CategoryMongo.builder()
                                .name("category3")
                                .letter("C")
                                .rangeMin(BigDecimal.valueOf(0.1))
                                .rangeMax(BigDecimal.valueOf(0.3))
                                .build(),
                        CategoryMongo.builder()
                                .name("category4")
                                .letter("D")
                                .rangeMin(BigDecimal.valueOf(0.3))
                                .rangeMax(BigDecimal.valueOf(0.9))
                                .build()
                ))
                .build();

        GlobalConfiguration gc = mapper.buildGlobalConfiguration(gcMongo);

        Assertions.assertNotNull(gc);
        Assertions.assertEquals(gcMongo.getGlobalNote(), gc.getGlobalNote());

        Assertions.assertNotNull(gc.getSections());
        Assertions.assertEquals(gcMongo.getSections().size(), gc.getSections().size());
        Assertions.assertEquals(gcMongo.getSections().get(0).getName(), gc.getSections().get(0).getName());
        Assertions.assertEquals(gcMongo.getSections().get(0).getDefaultWeight(), gc.getSections().get(0).getDefaultWeight());
        Assertions.assertEquals(gcMongo.getSections().get(1).getName(), gc.getSections().get(1).getName());
        Assertions.assertEquals(gcMongo.getSections().get(1).getDefaultWeight(), gc.getSections().get(1).getDefaultWeight());
        Assertions.assertEquals(gcMongo.getSections().get(2).getName(), gc.getSections().get(2).getName());
        Assertions.assertEquals(gcMongo.getSections().get(2).getDefaultWeight(), gc.getSections().get(2).getDefaultWeight());

        Assertions.assertNotNull(gc.getCategories());
        Assertions.assertEquals(gcMongo.getCategories().size(), gc.getCategories().size());
        Assertions.assertEquals(gcMongo.getCategories().get(0).getName(), gc.getCategories().get(0).getName());
        Assertions.assertEquals(gcMongo.getCategories().get(0).getLetter(), gc.getCategories().get(0).getLetter());
        Assertions.assertEquals(gcMongo.getCategories().get(0).getRangeMin(), gc.getCategories().get(0).getRangeMin());
        Assertions.assertEquals(gcMongo.getCategories().get(0).getRangeMax(), gc.getCategories().get(0).getRangeMax());
        Assertions.assertEquals(gcMongo.getCategories().get(1).getName(), gc.getCategories().get(1).getName());
        Assertions.assertEquals(gcMongo.getCategories().get(1).getLetter(), gc.getCategories().get(1).getLetter());
        Assertions.assertEquals(gcMongo.getCategories().get(1).getRangeMin(), gc.getCategories().get(1).getRangeMin());
        Assertions.assertEquals(gcMongo.getCategories().get(1).getRangeMax(), gc.getCategories().get(1).getRangeMax());
        Assertions.assertEquals(gcMongo.getCategories().get(2).getName(), gc.getCategories().get(2).getName());
        Assertions.assertEquals(gcMongo.getCategories().get(2).getLetter(), gc.getCategories().get(2).getLetter());
        Assertions.assertEquals(gcMongo.getCategories().get(2).getRangeMin(), gc.getCategories().get(2).getRangeMin());
        Assertions.assertEquals(gcMongo.getCategories().get(2).getRangeMax(), gc.getCategories().get(2).getRangeMax());
        Assertions.assertEquals(gcMongo.getCategories().get(3).getName(), gc.getCategories().get(3).getName());
        Assertions.assertEquals(gcMongo.getCategories().get(3).getLetter(), gc.getCategories().get(3).getLetter());
        Assertions.assertEquals(gcMongo.getCategories().get(3).getRangeMin(), gc.getCategories().get(3).getRangeMin());
        Assertions.assertEquals(gcMongo.getCategories().get(3).getRangeMax(), gc.getCategories().get(3).getRangeMax());
    }

}
