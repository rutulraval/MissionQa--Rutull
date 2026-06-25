package pages;

import driver.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutOverviewPage {
    private WebDriver driver;

    @FindBy(css = "[data-test='subtotal-label']")
    private WebElement itemTotalDiv;

    @FindBy(css = "[data-test='tax-label']")
    private WebElement taxDiv;

    public CheckoutOverviewPage() {
        this.driver = BasePage.getDriver();
        PageFactory.initElements(this.driver, this);
    }

    public String getItemTotalText() {
        return itemTotalDiv.getText();
    }

    public String getTaxText() {
        return taxDiv.getText();
    }

    /**
     * Returns numeric subtotal value parsed from the subtotal label (e.g. "Item total: $39.98" -> 39.98)
     */
    public double getSubtotalValue() {
        String txt = getItemTotalText();
        return parseCurrencyValue(txt);
    }

    public double getTaxValue() {
        String txt = getTaxText();
        return parseCurrencyValue(txt);
    }

    /**
     * Sums individual item prices shown on the overview page.
     * Looks for elements with data-test='inventory-item-price'.
     */
    public double getSumOfItemPrices() {
        List<WebElement> prices = driver.findElements(By.cssSelector("[data-test='inventory-item-price']"));
        double sum = 0.0;
        for (WebElement p : prices) {
            String t = p.getText();
            sum += parseCurrencyValue(t);
        }
        return sum;
    }

    private double parseCurrencyValue(String text) {
        if (text == null) return 0.0;
        // extract number like 39.98
        String num = text.replaceAll("[^0-9.]", "");
        if (num.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(num);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    public boolean isItemTotalCorrect() {
        double expected = getSumOfItemPrices();
        double actual = getSubtotalValue();

        return Math.abs(expected - actual) < 0.01;
    }

    public boolean isTaxCorrect(int taxPercentage) {
        double subtotal = getSubtotalValue();
        double displayedTax = getTaxValue();

        double expectedTax = subtotal * taxPercentage / 100.0;

        return Math.abs(expectedTax - displayedTax) < 0.01;
    }
}

