package fr.apithinking.apigreenscore;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

@SpringBootApplication
@EnableMongoRepositories(repositoryBaseClass = SimpleMongoRepository.class)
public class ApiGreenscoreApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiGreenscoreApplication.class)
                .run(args);
    }

}
