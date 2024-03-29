package fr.pagesjaunes.api.workflow;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableMongoRepositories(repositoryBaseClass = SimpleMongoRepository.class)
public class ApiGreenscoreApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiGreenscoreApplication.class)
                .run(args);
    }

}
