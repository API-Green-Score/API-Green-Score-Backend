package fr.pagesjaunes.socletechnique.core.smoketest;

import fr.pagesjaunes.socletechnique.core.smoketest.model.SmokeTest;
import fr.pagesjaunes.socletechnique.core.smoketest.model.SmokeTestCase;
import fr.pagesjaunes.socletechnique.core.smoketest.model.SmokeTestStatus;
import fr.pagesjaunes.socletechnique.core.smoketest.model.SmokeTestSuite;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author gdespres
 */
@Endpoint(id = "smoketest")
public class SmokeTestEndpoint {

    private final HealthEndpoint healthEndPoint;

    public SmokeTestEndpoint(HealthEndpoint healthEndPoint) {

        this.healthEndPoint = healthEndPoint;
    }

    @ReadOperation
    public SmokeTestSuite smokeTest() {

        CompositeHealth health = (CompositeHealth) healthEndPoint.health();

        SmokeTestSuite suite = new SmokeTestSuite();

        suite.setStatus(convert(health.getStatus()));

        List<SmokeTest> results = new ArrayList<>();
        for (Entry<String, HealthComponent> entry : health.getComponents().entrySet()) {

            String name = entry.getKey();
            HealthComponent component = entry.getValue();

            SmokeTest smokeTest = new SmokeTest().setName(name);
            if (component instanceof Health componentHealth) {
                smokeTest.setStatus(convert(componentHealth.getStatus()));
                List<SmokeTestCase> cases = buildCases(componentHealth, name, componentHealth.getStatus(), componentHealth.getDetails());
                smokeTest.setCases(cases);
                smokeTest.setTestSuccess(countSmokeTestCaseStatus(cases, SmokeTestStatus.SUCCESS));
                smokeTest.setTestWarning(countSmokeTestCaseStatus(cases, SmokeTestStatus.WARNING));
                smokeTest.setTestError(countSmokeTestCaseStatus(cases, SmokeTestStatus.ERROR));
                smokeTest.setTestIgnore(countSmokeTestCaseStatus(cases, SmokeTestStatus.IGNORE));
            } else if (component instanceof CompositeHealth compositeHealth) {
                smokeTest.setStatus(convert(compositeHealth.getStatus()));
                List<SmokeTestCase> cases = buildCases(compositeHealth, name, compositeHealth.getStatus(), compositeHealth.getComponents());
                smokeTest.setCases(cases);
                smokeTest.setTestSuccess(countSmokeTestCaseStatus(cases, SmokeTestStatus.SUCCESS));
                smokeTest.setTestWarning(countSmokeTestCaseStatus(cases, SmokeTestStatus.WARNING));
                smokeTest.setTestError(countSmokeTestCaseStatus(cases, SmokeTestStatus.ERROR));
                smokeTest.setTestIgnore(countSmokeTestCaseStatus(cases, SmokeTestStatus.IGNORE));
            }
            results.add(smokeTest);
        }
        suite.setResults(results);

        suite.setTestSuccess(countSmokeTestStatus(results, SmokeTestStatus.SUCCESS));
        suite.setTestWarning(countSmokeTestStatus(results, SmokeTestStatus.WARNING));
        suite.setTestError(countSmokeTestStatus(results, SmokeTestStatus.ERROR));
        suite.setTestIgnore(countSmokeTestStatus(results, SmokeTestStatus.IGNORE));

        return suite;
    }

    private List<SmokeTestCase> buildCases(final HealthComponent health, final String name, final Status status, final Map<String, ?> details) {

        if (details != null) {
            List<SmokeTestCase> cases = new ArrayList<>();
            for (Entry<String, ?> entry : details.entrySet()) {
                if (entry.getValue() instanceof Health) {
                    Health subHealth = (Health) entry.getValue();
                    cases.add(new SmokeTestCase(generateId(subHealth, entry.getKey()), entry.getKey(), convert(subHealth.getStatus()), convertMessage(status, subHealth.getDetails())));
                }
            }
            if (cases.isEmpty()) {
                cases.add(new SmokeTestCase(generateId(health, name), name, convert(status), convertMessage(status, details)));
            }
            return cases;
        }
        return Collections.emptyList();
    }

    private String generateId(HealthComponent health, String name) {
        String id = health.getClass().getName() + "|" + name;
        return UUID.nameUUIDFromBytes(id.getBytes(StandardCharsets.UTF_8)).toString();
    }

    private String convertMessage(final Status status, final Map<String, ?> details) {

        if (StringUtils.isNoneBlank(status.getDescription())) {
            return status.getDescription();
        } else if (details != null && !details.isEmpty()) {
            return Objects.toString(details);
        }
        return null;
    }

    private int countSmokeTestCaseStatus(final List<SmokeTestCase> cases, final SmokeTestStatus status) {

        int count = 0;
        for (SmokeTestCase smokeTestCase : cases) {
            if (smokeTestCase.getStatus() == status) {
                ++count;
            }
        }
        return count;
    }

    private int countSmokeTestStatus(final List<SmokeTest> results, final SmokeTestStatus status) {

        int count = 0;
        for (SmokeTest smokeTest : results) {
            switch (status) {
                case SUCCESS:
                    count += smokeTest.getTestSuccess();
                    break;
                case WARNING:
                    count += smokeTest.getTestWarning();
                    break;
                case ERROR:
                    count += smokeTest.getTestError();
                    break;
                case IGNORE:
                    count += smokeTest.getTestIgnore();
                    break;
                default:
                    break;
            }
        }
        return count;
    }

    private SmokeTestStatus convert(final Status status) {

        if (Status.UP.equals(status)) {
            return SmokeTestStatus.SUCCESS;
        } else if (Status.DOWN.equals(status)) {
            return SmokeTestStatus.ERROR;
        } else if (Status.OUT_OF_SERVICE.equals(status) || Status.UNKNOWN.equals(status)) {
            return SmokeTestStatus.IGNORE;
        }

        return null;
    }
}
