package fr.apithinking.apigreenscore.api.services;

import fr.apithinking.apigreenscore.api.RulesApi;
import fr.apithinking.apigreenscore.api.controller.RulesApiController;
import fr.apithinking.apigreenscore.model.Rule;
import org.springframework.data.domain.Page;

/**
 * A delegate to be called by the {@link RulesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
public interface RulesService {

    /**
     * GET /rules/{ruleId} : Get rule by ID
     * get a single rule
     *
     * @param ruleId ID of rule to return (required)
     * @return Success (status code 200)
     * or Invalid ID supplied (status code 400)
     * or Rule not found (status code 404)
     * @see RulesApi#getRuleById
     */
    Rule getRule(String ruleId);

    /**
     * GET /rules : Get all available rules
     * Get all available rules with pagination and sorted by ruleId
     *
     * @param page Zero-based page index (optional, default to 0)
     * @param size The size of the page to be returned (optional, default to 20)
     * @return Success (status code 200)
     * @see RulesApi#getRules
     */
    Page<Rule> getRules(
            Integer page,
            Integer size);

}
