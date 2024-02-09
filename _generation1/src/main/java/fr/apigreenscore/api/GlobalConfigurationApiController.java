package fr.apigreenscore.api;

import fr.apigreenscore.model.GlobalConfiguration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-15T22:08:39.761998+01:00[Europe/Paris]")
@Controller
@RequestMapping("${openapi.greenScoreAPISpecificationForRulesOperationsAndScoreEvaluations.base-path:/api/v0}")
public class GlobalConfigurationApiController implements GlobalConfigurationApi {

    private final GlobalConfigurationApiDelegate delegate;

    public GlobalConfigurationApiController(@Autowired(required = false) GlobalConfigurationApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new GlobalConfigurationApiDelegate() {});
    }

    @Override
    public GlobalConfigurationApiDelegate getDelegate() {
        return delegate;
    }

}
