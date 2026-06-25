package pages;

import driver.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private final WebDriver driver;
    @FindBy(css = "[data-test='shopping-cart-badge']")
    private WebElement cartCountSpan;
    private final By cartItemRows = By.cssSelector(".cart_item");
    private final By cartItemQuantity = By.cssSelector(".cart_quantity");
    private final By checkoutButton = By.id("checkout");

    public CartPage() {
        this.driver = BasePage.getDriver();
        PageFactory.initElements(this.driver, this);
    }

    public int getCartItemCount() {
        try {
            String text = cartCountSpan.getText();
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Integer> getCartItemQuantities() {
        return driver.findElements(cartItemRows)
                .stream()
                .map(row -> row.findElement(cartItemQuantity).getText())
                .map(text -> {
                    try {
                        return Integer.parseInt(text.trim());
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .collect(Collectors.toList());
    }

    public void removeItemById(String removeButtonId) {
        driver.findElement(By.id(removeButtonId)).click();
    }

    public CheckoutPage clickCheckout() {
        driver.findElement(checkoutButton).click();
        return new CheckoutPage();
    }
}

