# URL Configuration Guide - Multi-Feature Support

## Overview
Your framework now supports different URLs for different test features using a centralized configuration approach.

## Implementation Details

### 1. **TestData.properties** (Configuration)
```properties
Browser=chrome
url=https://saucedemo.com          # Used by UI-Test.feature
api_url=https://reqres.in          # Used by API-Test.feature
ScreenshotLocation=./Screenshots/
```

**Location:** `src/test/java/TestData/TestData.properties`

### 2. **StepDefinition.java** (Navigation Steps)
Three navigation methods are available:

```java
// For UI tests - opens saucedemo.com
@Given("^I am on the home page$")
public void iAmOnTheHomePage() {
    HomePage.homePage();  // Uses url property
}

// Generic step - navigate to any URL using {string} parameter
@Given("^I navigate to \"([^\"]*)\"$")
public void iNavigateToUrl(String url) {
    BasePage.getDriver().get(url);
}

// For API tests - opens reqres.in
@Given("^I open the API endpoint$")
public void iOpenTheAPIEndpoint() {
    BasePage.getDriver().get(LoadProp.getProperty("api_url"));
}
```

### 3. **API-Test.feature** (Using Background)
```gherkin
Feature: API test
Background:
  Given I open the API endpoint    # Runs before EVERY scenario

Scenario: Should see LIST USERS of all existing users
  Given I get the default list of users for on 1st page
  ...
```

The `Background` section ensures every API test scenario automatically:
- Opens the browser
- Navigates to https://reqres.in
- Then runs the actual test steps

### 4. **UI-Test.feature** (Unchanged)
```gherkin
Feature: Checkout items in the basket
Scenario: Check item total cost and tax
  Given I am on the home page    # Opens saucedemo.com
  ...
```

## How It Works

### Test Execution Flow

#### For API Tests:
```
[Test Starts]
    ↓
[Hook.initializeTest()] → Launches browser, sets timeouts
    ↓
[Background: I open the API endpoint] → Navigates to https://reqres.in
    ↓
[Test Scenario Steps] → Execute RestAssured API calls
    ↓
[Hook.screenshot()] → Cleanup and screenshots
    ↓
[Test Ends]
```

#### For UI Tests:
```
[Test Starts]
    ↓
[Hook.initializeTest()] → Launches browser, sets timeouts
    ↓
[Given I am on the home page] → Navigates to https://saucedemo.com
    ↓
[Test Scenario Steps] → Execute UI automation
    ↓
[Hook.screenshot()] → Cleanup and screenshots
    ↓
[Test Ends]
```

## Adding More Feature URLs

To add a new feature with a different URL:

1. **Add to TestData.properties:**
   ```properties
   my_feature_url=https://example.com
   ```

2. **Add to StepDefinition.java:**
   ```java
   @Given("^I open the my feature page$")
   public void iOpenMyFeaturePage() {
       BasePage.getDriver().get(LoadProp.getProperty("my_feature_url"));
   }
   ```

3. **Add to your feature file:**
   ```gherkin
   Feature: My Feature
   Background:
     Given I open the my feature page
   ```

## ThreadLocal WebDriver Integration

Both the UI-Test and API-Test features work seamlessly with:
- **ThreadLocal<WebDriver>** for parallel test execution
- **Centralized BasePage.getDriver()** for thread-safe access
- **Automatic cleanup** via `BasePage.removeDriver()` after each test

This ensures:
- ✅ No driver instance conflicts between tests
- ✅ No memory leaks from ThreadLocal
- ✅ Safe parallel test execution
- ✅ Different URLs per feature

## Current Test Results
- **API-Test.feature:** 7 scenarios, each opening https://reqres.in via Background
- **UI-Test.feature:** 1 scenario, opening https://saucedemo.com via step
- **Both features:** Running independently with their respective URLs

