package fr.pagesjaunes.utilisateur;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

@SpringBootApplication
@EnableMongoRepositories(repositoryBaseClass = SimpleMongoRepository.class)
public class ApiUtilisateurApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiUtilisateurApplication.class)
                .run(args);
    }

}
