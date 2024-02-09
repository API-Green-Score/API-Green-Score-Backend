package fr.apithinking.apigreenscore.controller;

import fr.apithinking.apigreenscore.model.Rule;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import jakarta.validation.constraints.*;
import java.util.List;

public interface RulesApi {

    /**
     * GET /rules/{ruleId} : Get rule by ID
     * get a single rule
     *
     * @param ruleId ID of rule to return (required)
     * @return Success (status code 200)
     *         or Invalid ID supplied (status code 400)
     *         or Rule not found (status code 404)
     */
    ResponseEntity<Rule> getRuleById(
            @NotNull(message = "La ruleId ne peut pas être nul") String ruleId
    );


    /**
     * GET /rules : Get all available rules
     * Get all available rules with pagination and sorted by ruleId
     *
     * @param page Zero-based page index (optional, default to 0)
     * @param size The size of the page to be returned (optional, default to 20)
     * @return Success (status code 200)
     */
    ResponseEntity<List<Rule>> getRules(
        @Min(0) Integer page,
        @Min(1) Integer size
    );

}
