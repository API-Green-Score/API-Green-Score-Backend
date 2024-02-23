package fr.apithinking.apigreenscore.core.smoketest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmokeTestCase {

    private String id;

    private final String name;

    private final SmokeTestStatus status;

    private final String message;
}
