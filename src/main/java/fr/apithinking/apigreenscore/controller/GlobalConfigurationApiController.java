package fr.apithinking.apigreenscore.controller;

import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import fr.apithinking.apigreenscore.services.GlobalConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/global_configuration")
@Tag(name = "score configuration", description = "Everything about grid score configuration")
@Validated // for ExceptionHandlerAdvice to be catched
@AllArgsConstructor
@Slf4j
public class GlobalConfigurationApiController implements GlobalConfigurationApi {

    private static final String PARAM_ID_GLOBAL_CONF = "id_globalconf";

    private final GlobalConfigurationService gcService;

    @GetMapping(path = "/")
    @Operation(
            operationId = "getGlobalConfigurations",
            summary = "Get all available global configurations",
            description = "Get all available configurations with pagination and sorted by globalConfiguration id"
    )
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalConfiguration.class)))
    @Override
    public Page<GlobalConfiguration> getGlobalConfigurations(
            @Parameter(name = "page", description = "Zero-based page index", in = ParameterIn.QUERY) @RequestParam(value = "page", defaultValue = "0") Integer page,
            @Parameter(name = "size", description = "The size of the page to be returned", in = ParameterIn.QUERY) @RequestParam(value = "size", defaultValue = "20") Integer size
    ) {
        return gcService.getGlobalConfigurations(page, size);
    }

    @GetMapping(path = "/{" + PARAM_ID_GLOBAL_CONF + "}")
    @Operation(
            operationId = "getGlobalConfiguration",
            summary = "Get global configuration data by id",
            description = "Get all data about global configuration of evaluation grid (table notes, section weigths, global note)"
    )
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalConfiguration.class)))
    @ApiResponse(responseCode = "400", description = "Invalid ID supplied")
    @ApiResponse(responseCode = "404", description = "Global configuration not found")
    @Override
    public GlobalConfiguration getGlobalConfiguration(
            @Parameter(description = "Global configuration id") @PathVariable(PARAM_ID_GLOBAL_CONF) final String pIdGlobalConf
    ) {
        return gcService.getGlobalConfiguration(pIdGlobalConf);
    }

}
