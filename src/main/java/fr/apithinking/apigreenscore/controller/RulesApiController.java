package fr.apithinking.apigreenscore.controller;

import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import fr.apithinking.apigreenscore.model.Rule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Validated
@Tag(name = "rule", description = "Everything about available rules")
@RestController
//@RequestMapping("${openapi.greenScoreAPISpecificationForRulesOperationsAndScoreEvaluations.base-path:/api/v0}")
@RequestMapping("/rules")
public class RulesApiController implements RulesApi {

//    private final RulesApiDelegate delegate;

//    public RulesApiController(@Autowired(required = false) RulesApiDelegate delegate) {
//        this.delegate = Optional.ofNullable(delegate).orElse(new RulesApiDelegate() {});
//    }

//    @Override
//    public RulesApiDelegate getDelegate() {
//        return delegate;
//    }

//    @Operation(
//            operationId = "getRuleById",
//            summary = "Get rule by ID",
//            description = "get a single rule",
//            tags = { "rule" },
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Success", content = {
//                            @Content(mediaType = "application/json", schema = @Schema(implementation = Rule.class))
//                    }),
//                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
//                    @ApiResponse(responseCode = "404", description = "Rule not found")
//            }
//    )
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/rules/{ruleId}",
//            produces = { "application/json" }
//    )
    @Override
    @GetMapping(path = "/{ruleId}")
    @Operation(
            summary = "Get rule by ID",
            description = "get a single rule"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = GlobalConfiguration.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Rule not found")
    })
    public ResponseEntity<Rule> getRuleById(
            @Parameter(name = "ruleId", description = "ID of rule to return", required = true, in = ParameterIn.PATH) @PathVariable("ruleId") String ruleId) {
        return new ResponseEntity<>(Rule.builder().id(ruleId).build(), null, HttpStatusCode.valueOf(200));
    }

//    @Operation(
//            operationId = "getRules",
//            summary = "Get all available rules",
//            description = "Get all available rules with pagination and sorted by ruleId",
//            tags = { "rule" },
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Success", content = {
//                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Rule.class)))
//                    })
//            }
//    )
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/rules",
//            produces = { "application/json" }
//    )
    @Override
    @GetMapping
    @Operation(
            summary = "Get all available rules",
            description = "Get all available rules with pagination and sorted by ruleId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = GlobalConfiguration.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Rule not found")
    })
    public ResponseEntity<List<Rule>> getRules(
            @Parameter(name = "page", description = "Zero-based page index", in = ParameterIn.QUERY) @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @Parameter(name = "size", description = "The size of the page to be returned", in = ParameterIn.QUERY) @RequestParam(value = "size", required = false, defaultValue = "20") Integer size
    ) {
        return new ResponseEntity<>(
                List.of(
                        Rule.builder().id("1").build(),
                        Rule.builder().id("2").build(),
                        Rule.builder().id("3").build()
                ), null, HttpStatusCode.valueOf(200));
    }

}
