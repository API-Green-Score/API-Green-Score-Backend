package fr.apithinking.apigreenscore.controller;

import fr.apithinking.apigreenscore.model.GlobalConfiguration;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;

public interface GlobalConfigurationApi {

    Page<GlobalConfiguration> getGlobalConfigurations(
            @Min(value = 0, message = "Page must be greater or equal to 0")
            Integer page,
            @Max(value = 100, message = "Page size must be less or equal to 100")
            Integer size
    );

    GlobalConfiguration getGlobalConfiguration(
            @NotBlank(message = "Global configuration identifier must not be blank")
            String pIdGlobalConf);

}
