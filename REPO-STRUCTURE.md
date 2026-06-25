# MissionQaTest Repository Structure Guide

This document explains how the repository is organized, what each package is responsible for, and where to add new code.

## 1) Project at a glance

- Framework type: Maven + Java + Cucumber BDD
- Test types: UI (Selenium) and API (REST Assured)
- Main test targets:
  - UI: SauceDemo (`https://www.saucedemo.com`)
  - API: ReqRes (`https://reqres.in`)

## 2) Top-level layout

```text
MissionQaTest/
  config.properties
  pom.xml
  README.md
  Screenshots/
  src/
    main/java/
    test/java/
    test/resources/
```

- `pom.xml`: Dependency and build configuration (Cucumber, Selenium, REST Assured, TestNG, Maven Surefire).
- `README.md`: Setup and execution notes.
- `config.properties`: Shared UI locator/config entries used by page objects.
- `Screenshots/`: Failure screenshots captured by hooks.
- `src/main/java/`: Reusable framework/core code.
- `src/test/java/`: Test implementation (steps, pages, hooks, runner).
- `src/test/resources/`: Feature files and test data properties.

## 3) `src/main/java` packages

### `api`
API client layer wrapping HTTP calls.

Contains:
- `GenericApiClient.java`: Base client with reusable GET/POST/PUT/DELETE methods.
- `UserClient.java`: User endpoint methods (list users, single user, create/update/delete user).
- `LoginClient.java`: Login endpoint method(s).

Use this package when:
- Adding or refactoring API endpoint operations.
- Reusing request/response handling across API tests.

### `config`
Configuration loading utilities.

Contains:
- `LoadProp.java`: Loads key-value properties used by tests (for example, browser and URLs from test resources).

Use this package when:
- Adding new configurable values and centralizing their loading.

### `driver`
WebDriver lifecycle and sharing helpers.

Contains:
- `BasePage.java`: Thread-safe `WebDriver` holder/accessor (ThreadLocal pattern).
- `BrowserSetup.java`: Browser bootstrap for Chrome/Edge/Firefox/headless modes.

Use this package when:
- Changing browser startup behavior.
- Updating driver initialization strategy.

### `factory`
Browser capability/options construction.

Contains:
- `BrowserOptionsFactory.java`: Creates browser option objects (incognito, headless, notifications settings, etc.).

Use this package when:
- Tuning browser launch flags or capabilities.

## 4) `src/test/java` packages

### `pages`
UI Page Object Model (POM) layer.

Contains:
- `LoginPage.java`: Login actions and checks.
- `HomePage.java`: Home page interactions/navigation.
- `InventoryPage.java`: Inventory interactions (select/add items).
- `CartPage.java`: Cart validations and item operations.
- `CheckoutPage.java`: Checkout form interactions.
- `CheckoutOverviewPage.java`: Final overview assertions (totals, tax checks).

Use this package when:
- Adding new UI interactions.
- Keeping selectors and page-specific behavior encapsulated.

### `steps`
Cucumber step definitions (glue code between feature text and implementation).

Contains:
- `UISteps.java`: UI scenario step implementations.
- `APISteps.java`: API scenario step implementations.

Use this package when:
- Adding Given/When/Then steps used in `.feature` files.

### `hooks`
Cucumber lifecycle hooks.

Contains:
- `Hook.java`: Setup/teardown logic (driver init, quit, screenshot on failure).

Use this package when:
- Adding common pre/post scenario behavior.

### `runner`
Cucumber execution entry point.

Contains:
- `RunnerTest.java`: Runner annotations/config (feature path, glue packages, reporting plugins).

Use this package when:
- Changing tags, report format, or run configuration.

## 5) `src/test/resources`

### `features`
BDD feature specifications.

Contains:
- `UI-Test.feature`: End-to-end UI checkout journey.
- `API-Test.feature`: User/login API behavior scenarios.
- `SampleTest.feature`: Sample template/placeholder scenarios.

Guideline:
- Keep business-readable scenarios here.
- Keep low-level logic in step definitions/page objects, not in feature files.

### `TestData`
Environment and test data configuration.

Contains:
- `TestData.properties`: Browser name, base URLs, endpoints, and related config values.

Guideline:
- Prefer adding environment-specific values here instead of hardcoding in Java classes.

## 6) How pieces connect (execution flow)

1. `RunnerTest` starts Cucumber.
2. Cucumber reads `.feature` files from `src/test/resources/features`.
3. Matching step methods in `steps` execute.
4. UI steps call `pages` objects; API steps call `api` clients.
5. Hooks in `hooks/Hook.java` run before/after each scenario.
6. Failures trigger screenshot capture into `Screenshots/`.

## 7) Where to add new work

- New UI scenario: add in `features` + implement step(s) in `UISteps` + update/add relevant page object(s).
- New API scenario: add in `features` + implement step(s) in `APISteps` + extend `api` clients.
- New configuration: add key in `src/test/resources/TestData/TestData.properties` and read via `config/LoadProp`.
- New browser behavior: update `factory/BrowserOptionsFactory` and/or `driver/BrowserSetup`.

## 8) Basic run instructions

From repository root:

```bash
mvn clean test
```

Common alternatives:

```bash
mvn test
mvn -Dcucumber.filter.tags="@ui" test
mvn -Dcucumber.filter.tags="@api" test
```

Notes:
- Tag filtering depends on tag usage in feature files.
- Generated reports are configured through the Cucumber runner plugins.

## 9) Maintenance tips

- Keep page objects focused on page behavior, not test assertions unrelated to that page.
- Reuse API client methods rather than duplicating REST request code in steps.
- Centralize configurable values in properties files.
- Add clear scenario names in feature files to make reports easy to read.

