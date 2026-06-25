package pages;

import driver.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InventoryPage {
    private final WebDriver driver;

    @FindBy(css = "[data-test='shopping-cart-link']")
    private WebElement cartButton;

    public InventoryPage() {
        this.driver = BasePage.getDriver();
        PageFactory.initElements(this.driver, this);
    }

    public void addItemsByDisplayNames(List<String> itemNames) {
        for (String itemName : itemNames) {
            String addButtonXPath = String.format(
                    "//div[@class='inventory_item'][.//div[@data-test='inventory-item-name' and normalize-space()='%s']]//button[contains(@id,'add-to-cart')]",
                    itemName);
            driver.findElement(By.xpath(addButtonXPath)).click();
        }
    }

    public CartPage goToCart() {
        cartButton.click();
        return new CartPage();
    }
}

