package fr.apithinking.apigreenscore.controller;

import fr.apithinking.apigreenscore.service.GlobalConfigurationApiDelegate;
import fr.apithinking.apigreenscore.model.Category;
import fr.apithinking.apigreenscore.model.GlobalConfiguration;

import fr.apithinking.apigreenscore.model.Section;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "score configuration", description = "Everything about grid score configuration")
@RestController
@RequestMapping("/global_configuration")
public class GlobalConfigurationApiController implements GlobalConfigurationApi {

//    private final GlobalConfigurationApiDelegate delegate;

    public GlobalConfigurationApiController(@Autowired(required = false) GlobalConfigurationApiDelegate delegate) {
//        this.delegate = Optional.ofNullable(delegate).orElse(new GlobalConfigurationApiDelegate() {});
    }
//
//    @Override
//    public GlobalConfigurationApiDelegate getDelegate() {
//        return delegate;
//    }

//    @Operation(
//            operationId = "getGlobalConfiguration",
//            summary = "Get global configuration data",
//            description = "Get all data about global configuration of evaluation grid (table notes, section weigths, global note)",
//            tags = { "score configuration" },
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Success", content = {
//                            @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalConfiguration.class))
//                    })
//            }
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = GlobalConfiguration.class)))
//    })
//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/global_configuration",
//            produces = { "application/json" }
//    )
    @Override
    @GetMapping
    @Operation(
        summary = "Get global configuration data",
        description = "Get all data about global configuration of evaluation grid (table notes, section weigths, global note)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = GlobalConfiguration.class)))
    })
    public ResponseEntity<GlobalConfiguration> getGlobalConfiguration() {
        return new ResponseEntity<>(
                GlobalConfiguration.builder()
                        .globalNote(1)
                        .categories(List.of(
                                Category.builder().letter("A").build(),
                                Category.builder().letter("B").build()))
                        .sections(List.of(
                                Section.builder().name("section1").build(),
                                Section.builder().name("section2").build()))
                        .build(),
                null,
                HttpStatusCode.valueOf(200));
    }

}
