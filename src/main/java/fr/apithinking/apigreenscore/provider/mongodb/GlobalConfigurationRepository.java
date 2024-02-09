package fr.apithinking.apigreenscore.provider.mongodb;

import fr.apithinking.apigreenscore.provider.mongodb.model.GlobalConfigurationMongodb;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GlobalConfigurationRepository extends MongoRepository<GlobalConfigurationMongodb, String> {

    @Override
    Optional<GlobalConfigurationMongodb> findById(String id);

    Optional<GlobalConfigurationMongodb> findByActive(boolean active);
}
