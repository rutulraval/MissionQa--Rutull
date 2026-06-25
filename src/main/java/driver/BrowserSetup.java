package driver;

import config.LoadProp;
import factory.BrowserOptionsFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.text.MessageFormat;


public class BrowserSetup extends BasePage {

    public static String browser = null;
    private static final String CHROME_WIN = "src\\test\\java\\BrowserDirectory\\chromedriver.exe";
    private static final String EDGE = "src\\test\\java\\BrowserDirectory\\MicrosoftWebDriver.exe";
    private static final String FIREFOX_WIN = "src\\test\\java\\BrowserDirectory\\geckodriver.exe";
    private static final String CHROME_MAC = "src/test/java/BrowserDirectory/chromedriver-Mac";


    /**
     * Browser property location /src/test/java/TestData/TestData.properties
     */


    /**
     * Function for multi browser
     */
    public void selectBrowser() {
        browser = LoadProp.getProperty("Browser");

        if (browser.equalsIgnoreCase("Chrome")) {
            //System.setProperty("webdriver.chrome.driver", CHROME_WIN);
            WebDriverManager.chromedriver().setup();
            BasePage.setDriver(
                    new ChromeDriver(
                            BrowserOptionsFactory.getChromeOptions()));
        } else if (browser.equalsIgnoreCase("edge")) {
            //System.setProperty("webdriver.edge.driver", EDGE);
            WebDriverManager.edgedriver().setup();
            BasePage.setDriver(
                    new EdgeDriver(
                            BrowserOptionsFactory.getEdgeOptions()));
        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            BasePage.setDriver(
                    new FirefoxDriver(
                            BrowserOptionsFactory.getFirefoxOptions()));
        } else if (browser.equalsIgnoreCase("chromeMac")) {
            //System.setProperty("webdriver.chrome.driver", CHROME_MAC);
            WebDriverManager.chromedriver().setup();
            BasePage.setDriver(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("chromeHeadless")) {
            //System.setProperty("webdriver.chrome.driver", CHROME_MAC);
            WebDriverManager.chromedriver().setup();
            BasePage.setDriver(
                    new ChromeDriver(
                            BrowserOptionsFactory.getChromeHeadlessOptions()));
        }  else {
            throw new IllegalArgumentException(MessageFormat.format("Wrong Browser: {0}", browser));
        }
    }
}
