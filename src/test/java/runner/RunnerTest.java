package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps", "hooks"},
        tags = "@ui or @api",
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/cucumber-pretty.html",
                "json:test-output/cucumber-reports/CucumberTestReport.json"
        })
public class RunnerTest extends AbstractTestNGCucumberTests {
}
