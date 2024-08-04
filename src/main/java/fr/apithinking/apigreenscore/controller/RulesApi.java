package fr.apithinking.apigreenscore.controller;

import fr.apithinking.apigreenscore.model.Rule;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;

public interface RulesApi {

    Page<Rule> getRules(
            @Min(value = 0, message = "Page must be greater or equal to 0")
            Integer page,
            @Max(value = 100, message = "Page size must be less or equal to 100")
            Integer size
    );

    Rule getRuleById(
            @NotBlank(message = "Rule identifier must not be blank")
            String ruleId);

}
