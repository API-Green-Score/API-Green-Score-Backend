package fr.apithinking.apigreenscore.mapper;

import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import fr.apithinking.apigreenscore.model.Rule;
import fr.apithinking.apigreenscore.provider.mongo.model.GlobalConfigurationMongo;
import fr.apithinking.apigreenscore.provider.mongo.model.RuleMongo;
import org.mapstruct.Mapper;

@Mapper
public interface ApiGreenscoreMapper {

    GlobalConfiguration buildGlobalConfiguration(GlobalConfigurationMongo source);

    Rule buildRule(RuleMongo source);

}
