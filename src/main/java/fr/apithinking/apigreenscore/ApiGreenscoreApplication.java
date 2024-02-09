package fr.apithinking.apigreenscore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

//@SpringBootApplication(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
//@ComponentScan(
//    basePackages = {
//        "fr.apithinking.apigreenscore",
//        "org.openapitools.configuration"
//    },
//    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
//)
@SpringBootApplication
@EnableMongoRepositories(repositoryBaseClass = SimpleMongoRepository.class)
public class ApiGreenscoreApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ApiGreenscoreApplication.class, args);
        new SpringApplicationBuilder(ApiGreenscoreApplication.class)
                .run(args);
//        new SpringApplicationBuilder(OpenApiGeneratorApplication.class).run(args);
    }

    //    @Bean(name = "fr.apithinking.apigreenscore.OpenApiGeneratorApplication.jsonNullableModule")
    //    public Module jsonNullableModule() {
//        return new JsonNullableModule();
//    }

}