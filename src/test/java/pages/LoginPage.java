package pages;

import driver.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    @FindBy(id = "user-name")
    private WebElement username;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public LoginPage() {
        this.driver = BasePage.getDriver();
        PageFactory.initElements(this.driver, this);
    }

    public InventoryPage login(String user, String pass) {
        username.clear();
        username.sendKeys(user);
        password.clear();
        password.sendKeys(pass);
        loginButton.click();
        return new InventoryPage();
    }
}

