package fr.apithinking.apigreenscore.provider.mongo;

import fr.apithinking.apigreenscore.provider.mongo.model.RuleMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RuleRepository extends MongoRepository<RuleMongo, String> {

}
