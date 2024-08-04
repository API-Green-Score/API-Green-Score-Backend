package fr.apithinking.apigreenscore.services;

import fr.apithinking.apigreenscore.controller.RulesApiController;
import fr.apithinking.apigreenscore.model.Rule;
import org.springframework.data.domain.Page;

/**
 * A delegate to be called by the {@link RulesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
public interface RulesService {

    Page<Rule> getRules(
            Integer page,
            Integer size);

    Rule getRule(String ruleId);

}
