package fr.apithinking.apigreenscore.api.controller;

import fr.apithinking.apigreenscore.api.GlobalConfigurationApi;
import fr.apithinking.apigreenscore.api.services.GlobalConfigurationService;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global_configuration")
@Tag(name = "score configuration", description = "Everything about grid score configuration")
@Validated // for ExceptionHandlerAdvice to be catched
@Slf4j
public class GlobalConfigurationApiController implements GlobalConfigurationApi {

    private static final String PARAM_ID_GLOBAL_CONF = "id_globalconf";

    @Autowired
    private GlobalConfigurationService gcService;

    @GetMapping(path = "/{" + PARAM_ID_GLOBAL_CONF + "}")
    @Operation(
            operationId = "getGlobalConfiguration",
            summary = "Get global configuration data by id",
            description = "Get all data about global configuration of evaluation grid (table notes, section weigths, global note)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalConfiguration.class)))
    })
    @Override
    public GlobalConfiguration getGlobalConfiguration(
            @Parameter(description = "Global configuration id") @PathVariable(PARAM_ID_GLOBAL_CONF) final String pIdGlobalConf
    ) {
        return gcService.getGlobalConfiguration(pIdGlobalConf);
    }

}
