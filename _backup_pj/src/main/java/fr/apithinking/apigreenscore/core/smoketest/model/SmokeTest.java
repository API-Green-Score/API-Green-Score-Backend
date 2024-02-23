package fr.apithinking.apigreenscore.core.smoketest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SmokeTest {

    private String name;

    private List<SmokeTestCase> cases;

    private SmokeTestStatus status;

    @JsonProperty(value = "nbTestSuccess")
    private int testSuccess;

    @JsonProperty(value = "nbTestWarning")
    private int testWarning;

    @JsonProperty(value = "nbTestError")
    private int testError;

    @JsonProperty(value = "nbTestIgnore")
    private int testIgnore;
}