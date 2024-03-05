package fr.apithinking.apigreenscore;

import fr.apithinking.apigreenscore.provider.mongo.model.CategoryMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.GlobalConfigurationMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.RuleMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.SectionMongo;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class TestUtils {

    public static GlobalConfigurationMongo buildGlobalConfigurationMongo(
            int index
    ) {
        GlobalConfigurationMongo.GlobalConfigurationMongoBuilder gcMongoBuilder = GlobalConfigurationMongo.builder()
                .id("ID-" + index)
                .globalNote(BigDecimal.valueOf(0.5))
                .sections(List.of(
                        SectionMongo.builder()
                                .name("section1-" + index)
                                .defaultWeight(BigDecimal.valueOf(0.3))
                                .build(),
                        SectionMongo.builder()
                                .name("section2-" + index)
                                .defaultWeight(BigDecimal.valueOf(0.2))
                                .build(),
                        SectionMongo.builder()
                                .name("section3-" + index)
                                .defaultWeight(BigDecimal.valueOf(0.1))
                                .build()
                ))
                .categories(List.of(
                        CategoryMongo.builder()
                                .name("category1-" + index)
                                .letter("A")
                                .rangeMin(BigDecimal.valueOf(0.3))
                                .rangeMax(BigDecimal.valueOf(0.8))
                                .build(),
                        CategoryMongo.builder()
                                .name("category2-" + index)
                                .letter("B")
                                .rangeMin(BigDecimal.valueOf(0.6))
                                .rangeMax(BigDecimal.valueOf(0.9))
                                .build(),
                        CategoryMongo.builder()
                                .name("category3-" + index)
                                .letter("C")
                                .rangeMin(BigDecimal.valueOf(0.1))
                                .rangeMax(BigDecimal.valueOf(0.3))
                                .build(),
                        CategoryMongo.builder()
                                .name("category4-" + index)
                                .letter("D")
                                .rangeMin(BigDecimal.valueOf(0.2))
                                .rangeMax(BigDecimal.valueOf(0.7))
                                .build()
                ));

        return gcMongoBuilder.build();
    }

    public static RuleMongo buildRuleMongo(int index) {
        return RuleMongo.builder()
                .id("ID-" + index)
                .title("title-" + index)
                .description("description-" + index)
                .build();
    }

}
