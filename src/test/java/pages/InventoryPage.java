package pages;

import driver.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class InventoryPage {
    private final WebDriver driver;
    private final By cartButton = By.cssSelector("[data-test='shopping-cart-link']");

    public InventoryPage() {
        this.driver = BasePage.getDriver();
    }

    public void addItemByAddButtonId(String addButtonId) {
        driver.findElement(By.id(addButtonId)).click();
    }

    public void addItemsByAddButtonIds(List<String> addButtonIds) {
        for (String id : addButtonIds) {
            addItemByAddButtonId(id);
        }
    }

    public void addItemsByDisplayNames(List<String> itemNames) {
        for (String itemName : itemNames) {
            addItemByAddButtonId(resolveAddButtonId(itemName));
        }
    }

    public void removeItemsByDisplayNames(CartPage cartPage, List<String> itemNames) {
        for (String itemName : itemNames) {
            cartPage.removeItemById(resolveRemoveButtonId(itemName));
        }
    }

    private String resolveAddButtonId(String item) {
        switch (item.toLowerCase()) {
            case "sauce labs backpack":
                return "add-to-cart-sauce-labs-backpack";
            case "sauce labs fleece jacket":
                return "add-to-cart-sauce-labs-fleece-jacket";
            case "sauce labs bolt t-shirt":
            case "sauce labs bolt t-shirt ":
                return "add-to-cart-sauce-labs-bolt-t-shirt";
            case "sauce labs onesie":
                return "add-to-cart-sauce-labs-onesie";
            default:
                throw new IllegalArgumentException("Unknown inventory item: " + item);
        }
    }

    private String resolveRemoveButtonId(String item) {
        if (item.equalsIgnoreCase("Sauce Labs Fleece Jacket") || item.toLowerCase().contains("fleece")) {
            return "remove-sauce-labs-fleece-jacket";
        }
        throw new IllegalArgumentException("Unknown cart item: " + item);
    }

    public CartPage goToCart() {
        driver.findElement(cartButton).click();
        return new CartPage();
    }
}

