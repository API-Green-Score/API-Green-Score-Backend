package fr.apithinking.apigreenscore.provider.mongo;

import fr.apithinking.apigreenscore.provider.mongo.model.GlobalConfigurationMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GlobalConfigurationRepository extends MongoRepository<GlobalConfigurationMongo, String> {

}
