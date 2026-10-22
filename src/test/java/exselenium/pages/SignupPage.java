package exselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SignupPage {

    private final WebDriver driver;

    private final By titleMr = By.id("id_gender1");
    private final By titleMrs = By.id("id_gender2");
    private final By password = By.id("password");
    private final By days = By.id("days");
    private final By months = By.id("months");
    private final By years = By.id("years");
    private final By newsletter = By.id("newsletter");
    private final By specialOffers = By.id("optin");
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("[data-qa='create-account']");

    public SignupPage(WebDriver driver) {
        this.driver = driver;
    }

    public SignupPage fillForm(SignupData data) {
        driver.findElement("Mrs".equals(data.title) ? titleMrs : titleMr).click();
        driver.findElement(password).sendKeys(data.password);

        new Select(driver.findElement(days)).selectByValue(data.day);
        new Select(driver.findElement(months)).selectByVisibleText(data.month);
        new Select(driver.findElement(years)).selectByValue(data.year);

        check(newsletter, data.newsletter);
        check(specialOffers, data.specialOffers);

        driver.findElement(firstName).sendKeys(data.firstName);
        driver.findElement(lastName).sendKeys(data.lastName);
        driver.findElement(company).sendKeys(data.company);
        driver.findElement(address).sendKeys(data.address);
        driver.findElement(address2).sendKeys(data.address2);
        new Select(driver.findElement(country)).selectByVisibleText(data.country);
        driver.findElement(state).sendKeys(data.state);
        driver.findElement(city).sendKeys(data.city);
        driver.findElement(zipcode).sendKeys(data.zipcode);
        driver.findElement(mobileNumber).sendKeys(data.mobileNumber);
        return this;
    }

    public AccountPage createAccount() {
        driver.findElement(createAccountButton).click();
        return new AccountPage(driver);
    }

    public String getMobileNumberValidationMessage() {
        return driver.findElement(mobileNumber).getDomProperty("validationMessage");
    }

    public boolean isMobileNumberMissing() {
        return (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validity.valueMissing;", driver.findElement(mobileNumber));
    }

    private void check(By checkbox, boolean marcado) {
        WebElement el = driver.findElement(checkbox);
        if (el.isSelected() != marcado) {
            el.click();
        }
    }
}
