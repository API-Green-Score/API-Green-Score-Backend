package fr.apithinking.apigreenscore.provider.mongodb;

import fr.apithinking.apigreenscore.ApiGreenscoreApplication;
import fr.apithinking.apigreenscore.model.Category;
import fr.apithinking.apigreenscore.model.Section;
import fr.apithinking.apigreenscore.provider.mongodb.model.GlobalConfigurationMongodb;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = ApiGreenscoreApplication.class)
@ActiveProfiles("it")
class GlobalConfigurationRepositoryIT {

    @Autowired
    private GlobalConfigurationRepository gcRepository;

    @AfterEach
    public void cleanDatabase() {
        gcRepository.deleteAll();
    }

    @Test
    void shouldFindById() {
//    void testFindById() {
        insertGlobalConfigurationToSearch();

        GlobalConfigurationMongodb config = gcRepository.findById("3").orElse(null);

        Assertions.assertNotNull(config);
        Assertions.assertEquals("3", config.getId());
    }

    @Test
    void shouldFindTheActiveOne() {
        insertGlobalConfigurationToSearch();

        GlobalConfigurationMongodb config = gcRepository.findByActive(true).orElse(null);

        Assertions.assertNotNull(config);
        Assertions.assertEquals("1", config.getId());
    }

    private void insertGlobalConfigurationToSearch() {
        insertGlobalConfiguration(0);
        insertGlobalConfiguration(1, true);
        insertGlobalConfiguration(2);
        insertGlobalConfiguration(3);
        insertGlobalConfiguration(4);
        insertGlobalConfiguration(5);
        insertGlobalConfiguration(6);
        insertGlobalConfiguration(7);
        insertGlobalConfiguration(8);
        insertGlobalConfiguration(9);
    }

    private GlobalConfigurationMongodb insertGlobalConfiguration(int index) {
        return insertGlobalConfiguration(index, false);
    }

    private GlobalConfigurationMongodb insertGlobalConfiguration(int index, boolean isActive) {
        GlobalConfigurationMongodb config = GlobalConfigurationMongodb.builder()
                .id(index + "")
                .name("Global configuration " + index)
                .description("Global configuration DESCRIPTION " + index)
                .globalNote(6000)
                .section(Section.builder().name("Archi").weight(25f - index).build())
                .section(Section.builder().name("Design").weight(40f + index).build())
                .section(Section.builder().name("Usage").weight(25f - index).build())
                .section(Section.builder().name("Log").weight(10f + index).build())
                .category(Category.builder().name("Excellent").letter("A").rangeMin(6000 + index).build())
                .category(Category.builder().name("Acceptable").letter("B").rangeMin(3000 - index).rangeMax(5999 + index).build())
                .category(Category.builder().name("Average").letter("C").rangeMin(2000 + index).rangeMax(2999 - index).build())
                .category(Category.builder().name("Poor").letter("D").rangeMin(1000 - index).rangeMax(1999 + index).build())
                .category(Category.builder().name("Very Poor").letter("E").rangeMin(0).rangeMax(999 - index).build())
                .category(Category.builder().name("Not evaluated").letter("N/A").build())
                .active(isActive)
                .build();

        GlobalConfigurationMongodb savedConfig = gcRepository.save(config);

        return savedConfig;
    }

//    private Date modifyDate(final Date pDate, final int pChangeValue) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(pDate);
//        cal.add(Calendar.DAY_OF_YEAR, pChangeValue);
//        return cal.getTime();
//    }
}
