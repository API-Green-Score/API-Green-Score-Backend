package fr.apithinking.apigreenscore.controller;

import fr.apithinking.apigreenscore.model.Rule;
import fr.apithinking.apigreenscore.services.RulesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rules")
@Tag(name = "rule", description = "Everything about available rules")
@Validated // for ExceptionHandlerAdvice to be catched
@AllArgsConstructor
@Slf4j
public class RulesApiController implements RulesApi {

    private final RulesService rulesService;

    @GetMapping(path = "/{ruleId}")
    @Operation(
            operationId = "getRuleById",
            summary = "Get rule by ID",
            description = "get a single rule"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rule.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Rule not found")
    })
    @Override
    public Rule getRuleById(
            @Parameter(name = "ruleId", description = "ID of rule to return", required = true, in = ParameterIn.PATH) @PathVariable("ruleId") String ruleId
    ) {
        return rulesService.getRule(ruleId);
    }

    @GetMapping(path = "/")
    @Operation(
            operationId = "getRules",
            summary = "Get all available rules",
            description = "Get all available rules with pagination and sorted by ruleId"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rule.class)))
    })
    @Override
    public Page<Rule> getRules(
            @Parameter(name = "page", description = "Zero-based page index", in = ParameterIn.QUERY) @RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
            @Parameter(name = "size", description = "The size of the page to be returned", in = ParameterIn.QUERY) @RequestParam(value = "size", required = true, defaultValue = "20") Integer size
    ) {
        return rulesService.getRules(page, size);
    }

}
