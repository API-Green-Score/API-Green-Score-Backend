/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.52).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Rule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-23T21:35:35.651524679Z[GMT]")
@Validated
public interface RulesApi {

    @Operation(summary = "Get rule by ID", description = "get a single rule", tags={ "rule" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rule.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        
        @ApiResponse(responseCode = "404", description = "Rule not found") })
    @RequestMapping(value = "/rules/{ruleId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Rule> getRuleById(@Parameter(in = ParameterIn.PATH, description = "ID of rule to return", required=true, schema=@Schema()) @PathVariable("ruleId") Object ruleId
);


    @Operation(summary = "Get all available rules", description = "Get all available rules with pagination and sorted by ruleId", tags={ "rule" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))) })
    @RequestMapping(value = "/rules",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Object> getRules(@DecimalMin("0")@Parameter(in = ParameterIn.QUERY, description = "Zero-based page index" ,schema=@Schema( defaultValue="0")) @Valid @RequestParam(value = "page", required = false, defaultValue="0") Object page
, @DecimalMin("1")@Parameter(in = ParameterIn.QUERY, description = "The size of the page to be returned" ,schema=@Schema( defaultValue="20")) @Valid @RequestParam(value = "size", required = false, defaultValue="20") Object size
);

}

