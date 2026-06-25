package mission;

import driver.BasePage;
import org.openqa.selenium.support.PageFactory;

public class SamplePage extends BasePage {
    public SamplePage() {
        PageFactory.initElements(BasePage.getDriver(), this);
    }

/**
 * You can use this class to add page objects and methods.
 */
}
