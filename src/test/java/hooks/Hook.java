package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.cucumber.java.Scenario;
import driver.BasePage;
import driver.BrowserSetup;
import config.LoadProp;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Date;

public class Hook extends BasePage {

    BrowserSetup browsersetup = new BrowserSetup();
    private static final int WAIT_SEC = 20;


    @Before("@ui")
    public void initializeTest() {
        browsersetup.selectBrowser();
        BasePage.getDriver().manage().deleteAllCookies();
        BasePage.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(WAIT_SEC));
        BasePage.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_SEC));
        BasePage.getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(WAIT_SEC));
    }

    /**
     * Executed after each UI tagged scenario
     */
    @After("@ui")
    public void screenshot(Scenario scenario) {
        // If driver was not created (e.g., API-only scenario), skip UI teardown
        if (BasePage.getDriver() == null) {
            return;
        }

        if (scenario.isFailed()) {
            String screenShotFilename = scenario.getName().replace(" ", "")
                    + new Timestamp(new Date().getTime()).toString().replaceAll("[^a-zA-Z0-9]", "")
                    + "_" + LoadProp.getProperty("Browser") + ".jpg";
            try {
                File scrFile = ((TakesScreenshot) BasePage.getDriver()).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(LoadProp.getProperty("ScreenshotLocation") + screenShotFilename));
            } catch (IOException e) {
                throw new IllegalStateException("Unable to capture screenshot for scenario: " + scenario.getName(), e);
            }
        }

        // Attempt clean shutdown of the browser session
        try {
            BasePage.getDriver().quit();
        } catch (Exception ex) {
            System.err.println("Browser session already closed for scenario: " + scenario.getName() + ": " + ex.getMessage());
        } finally {
            // ensure ThreadLocal reference is removed to prevent memory leaks
            BasePage.removeDriver();
        }
    }
}

