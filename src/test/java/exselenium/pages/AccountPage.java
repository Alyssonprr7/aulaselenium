package exselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage {

    private final WebDriver driver;

    private final By accountCreated = By.cssSelector("[data-qa='account-created']");
    private final By accountDeleted = By.cssSelector("[data-qa='account-deleted']");
    private final By continueButton = By.cssSelector("[data-qa='continue-button']");
    private final By loggedInAs = By.xpath("//a[contains(., 'Logged in as')]");
    private final By deleteAccount = By.cssSelector("a[href='/delete_account']");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getAccountCreatedText() {
        return driver.findElement(accountCreated).getText();
    }

    public void continueClick() {
        driver.findElement(continueButton).click();
    }

    public String getLoggedInAsText() {
        return driver.findElement(loggedInAs).getText();
    }

    public void deleteAccount() {
        driver.findElement(deleteAccount).click();
    }

    public String getAccountDeletedText() {
        return driver.findElement(accountDeleted).getText();
    }
}
