package exselenium;

import exselenium.pages.AccountPage;
import exselenium.pages.SignupData;
import exselenium.pages.SignupPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestaAutomationExercises {

	protected WebDriver driver;
	
    @BeforeEach
    public void createDriver() {
		driver = new ChromeDriver();
    }

	@Test
	public void testFailedLogin() {
        //Página main
        driver.get("https://automationexercise.com/");
        String featureItems = driver.findElement(By.xpath("html/body/section[2]/div/div/div[2]/div[1]/h2")).getText();
        assertEquals("FEATURES ITEMS", featureItems);
        driver.findElement(By.xpath("/html/body/header/div/div/div/div[2]/div/ul/li[4]/a")).click();

        //Login Screen
        String loginText = driver.findElement(By.xpath("/html/body/section/div/div/div[1]/div/h2")).getText();
        assertEquals("Login to your account", loginText);

        //Type email
        driver.findElement(By.name("email")).sendKeys("teste@teste.com");

        //Type pass
        driver.findElement(By.name("password")).sendKeys("teste@teste.com");

        driver.findElement(By.xpath("/html/body/section/div/div/div[1]/div/form/button")).click();

        String errorMessage = driver.findElement(By.xpath("/html/body/section/div/div/div[1]/div/form/p")).getText();
        assertEquals("Your email or password is incorrect!", errorMessage);
        driver.quit();
    }

    @Test
    public void registerUserDefault() {
        this.goToSignUp();

        SignupData data = new SignupData();

        AccountPage account = new SignupPage(driver)
                .fillForm(data)
                .createAccount();

        assertEquals("ACCOUNT CREATED!", account.getAccountCreatedText());
        account.continueClick();

        assertEquals("Logged in as Alysson Rocha", account.getLoggedInAsText());
        account.deleteAccount();

        assertEquals("ACCOUNT DELETED!", account.getAccountDeletedText());
        account.continueClick();

        driver.quit();
    }

    @Test
    public void registerUserWithBlankNumber() {
        this.goToSignUp();

        SignupData data = new SignupData();
        data.mobileNumber = "";

        SignupPage signup = new SignupPage(driver).fillForm(data);
        signup.createAccount();

        assertTrue(signup.isMobileNumberMissing());
        assertEquals("Please fill out this field.", signup.getMobileNumberValidationMessage());
        assertTrue(driver.getCurrentUrl().contains("/signup"));

        driver.quit();
    }

    @Test
    public void registerUserWithBigFirstName() {
        this.goToSignUp();

        SignupData data = new SignupData();
        data.firstName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";

        SignupPage signup = new SignupPage(driver).fillForm(data);
        AccountPage account = signup.createAccount();

        assertEquals("ACCOUNT CREATED!", account.getAccountCreatedText());
        account.continueClick();

        assertEquals("Logged in as Alysson Rocha", account.getLoggedInAsText());
        account.deleteAccount();

        assertEquals("ACCOUNT DELETED!", account.getAccountDeletedText());
        account.continueClick();

        driver.quit();
    }

    @Test
    public void registerUserWithLettersZipCode() {
        this.goToSignUp();

        SignupData data = new SignupData();
        data.zipcode = "aaaaaaaaaaaaaaaaa";

        SignupPage signup = new SignupPage(driver).fillForm(data);
        AccountPage account = signup.createAccount();

        assertEquals("ACCOUNT CREATED!", account.getAccountCreatedText());
        account.continueClick();

        assertEquals("Logged in as Alysson Rocha", account.getLoggedInAsText());
        account.deleteAccount();

        assertEquals("ACCOUNT DELETED!", account.getAccountDeletedText());
        account.continueClick();

        driver.quit();
    }

    @Test
    public void registerUserWithShortZipCode() {
        this.goToSignUp();

        SignupData data = new SignupData();
        data.zipcode = "1234";

        SignupPage signup = new SignupPage(driver).fillForm(data);
        AccountPage account = signup.createAccount();

        assertEquals("ACCOUNT CREATED!", account.getAccountCreatedText());
        account.continueClick();

        assertEquals("Logged in as Alysson Rocha", account.getLoggedInAsText());
        account.deleteAccount();

        assertEquals("ACCOUNT DELETED!", account.getAccountDeletedText());
        account.continueClick();

        driver.quit();
    }

    @Test
    public void registerUserWithOneCharPass() {
        this.goToSignUp();

        SignupData data = new SignupData();
        data.password = "1";

        SignupPage signup = new SignupPage(driver).fillForm(data);
        AccountPage account = signup.createAccount();

        assertEquals("ACCOUNT CREATED!", account.getAccountCreatedText());
        account.continueClick();

        assertEquals("Logged in as Alysson Rocha", account.getLoggedInAsText());
        account.deleteAccount();

        assertEquals("ACCOUNT DELETED!", account.getAccountDeletedText());
        account.continueClick();

        driver.quit();
    }


    @Test
    public void registerUserWithHugePass() {
        this.goToSignUp();

        SignupData data = new SignupData();
        data.password = "dfasdasdasdasdnaidhasdiasubdauishdiuashdiuashuidahduiashdiuashduiahuidhauidhasuihdiaushduiahduiashdiuashduiahduiahudaiudhauhuidhauisdhaiud";

        SignupPage signup = new SignupPage(driver).fillForm(data);
        AccountPage account = signup.createAccount();

        assertEquals("ACCOUNT CREATED!", account.getAccountCreatedText());
        account.continueClick();

        assertEquals("Logged in as Alysson Rocha", account.getLoggedInAsText());
        account.deleteAccount();

        assertEquals("ACCOUNT DELETED!", account.getAccountDeletedText());
        account.continueClick();

        driver.quit();
    }

    @Test
    public void registerUserWithUnlistedCountry() {
        this.goToSignUp();

        SignupData data = new SignupData();
        data.country = "Brazil";

        // o select só tem opções fixas, então não deve ser possível escolher Brazil
        assertThrows(NoSuchElementException.class, () -> new SignupPage(driver).fillForm(data));
        assertTrue(driver.getCurrentUrl().contains("/signup"));

        driver.quit();
    }


    private void goToSignUp() {
        //Página main
        driver.get("https://automationexercise.com/");
        String featureItems = driver.findElement(By.xpath("html/body/section[2]/div/div/div[2]/div[1]/h2")).getText();
        assertEquals("FEATURES ITEMS", featureItems);
        driver.findElement(By.xpath("/html/body/header/div/div/div/div[2]/div/ul/li[4]/a")).click();

        //Login Screen
        String signUpText = driver.findElement(By.xpath("/html/body/section/div/div/div[3]/div/h2")).getText();
        assertEquals("New User Signup!", signUpText);

        //Type Name
        driver.findElement(By.name("name")).sendKeys("Alysson Rocha");

        //Type email
        driver.findElement(By.xpath("/html/body/section/div/div/div[3]/div/form/input[3]")).sendKeys("alysson.rocha1234@teste.com");

        driver.findElement(By.xpath("/html/body/section/div/div/div[3]/div/form/button")).click();

        String enterInfo = driver.findElement(By.xpath("/html/body/section/div/div/div/div[1]/h2/b")).getText();
        assertEquals("ENTER ACCOUNT INFORMATION", enterInfo);
    }
	
    @AfterAll
    public static void quitDriver() {
//       driver.quit();
    }
}
