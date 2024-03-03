package fr.apithinking.apigreenscore.api;

import fr.apithinking.apigreenscore.model.Rule;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;

public interface RulesApi {

    /**
     * GET /rules/{ruleId} : Get rule by ID
     * get a single rule
     *
     * @param ruleId ID of rule to return (required)
     * @return Success (status code 200)
     * or Invalid ID supplied (status code 400)
     * or Rule not found (status code 404)
     */
    Rule getRuleById(
            @NotBlank(message = "Rule identifier must not be blank")
            String ruleId);


    /**
     * GET /rules : Get all available rules
     * Get all available rules with pagination and sorted by ruleId
     *
     * @param page Zero-based page index (optional, default to 0)
     * @param size The size of the page to be returned (optional, default to 20)
     * @return Success (status code 200)
     */
    Page<Rule> getRules(
            @Min(value = 0, message = "Page must be greater or equal to 0")
            Integer page,
            @Min(value = 1, message = "Page size must be greater than 0")
            @Max(value = 100, message = "Page size must be less or equal to 100")
            Integer size
    );

}
