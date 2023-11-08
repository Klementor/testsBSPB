package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;

import java.time.Duration;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {

    private static final String loginUrl = "https://i.bspb.ru/auth?response_type=code&client_id=1&red" +
            "irect_uri=https%3A%2F%2Fi.bspb.ru%2Flogin%2Fsuccess&prefetch_uri=https%3A%2F%2Fi.bspb.ru%2Flogin%2Fpref" +
            "etch&force_new_session=true";
    private WebDriver driver;

    private MainPage mainPage;

    private LoginPage loginPage;

    @BeforeEach
    public void openingWebsite() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mihai\\IdeaProjects\\testsBSPB\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.bspb.ru/");
        mainPage = PageFactory.initElements(driver, MainPage.class);
        WebElement loginElement = mainPage.getLoginElement();
        loginElement.click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @AfterEach
    public void closeWebSite() {
        driver.close();
        driver.quit();
    }

    @Test
    public void openLoginPageTest() {
        Assertions.assertEquals(loginPage.getLogoElement().getAttribute("alt"), "Интернет банк - Банк Санкт-Петербург");
        Assertions.assertEquals(driver.getCurrentUrl(), loginUrl);
    }

    @Test
    public void correctFillingOfFieldsTest() {
        WebElement usernameElement = loginPage.getUsernameElement();
        usernameElement.sendKeys("fdsfdjkhhsf213123");
        WebElement passwordElement = loginPage.getPasswordElement();
        passwordElement.sendKeys("DSFsdfssd321");
        assertThat(usernameElement.getAttribute("value")).isEqualTo("fdsfdjkhhsf213123");
        assertThat(passwordElement.getAttribute("value")).isEqualTo("DSFsdfssd321");
    }

    @Test
    public void registerTest() {
        WebElement usernameElement = loginPage.getUsernameElement();
        WebElement passwordElement = loginPage.getPasswordElement();
        String url = driver.getCurrentUrl();

        usernameElement.sendKeys("klementor373");
        passwordElement.sendKeys("Arhangel373");
        loginPage.getLoginButton().click();
        assertThat(getElementWithWaiting("//*[@id=\"otp-code-text\"]").getAttribute("outerText"))
                .isEqualTo("На ваше устройство отправлен код подтверждения в виде push/SMS *9346, введите его для входа");
        driver.get(url);

        usernameElement.sendKeys("klementor373");
        passwordElement.sendKeys("ArHaNgEl373");
        loginPage.getLoginButton().click();
        assertThat(getElementWithWaiting("//*[@id=\"alerts-container\"]").getAttribute("outerText")).isNotBlank();

        usernameElement.clear();
        usernameElement.sendKeys("KlEmEnTOr373");
        passwordElement.sendKeys("Arhangel373");
        loginPage.getLoginButton().click();
        assertThat(getElementWithWaiting("//*[@id=\"otp-code-text\"]").getAttribute("outerText"))
                .isEqualTo("На ваше устройство отправлен код подтверждения в виде push/SMS *9346, введите его для входа");
    }

    @Test
    public void incorrectPasswordTest() {
        WebElement usernameElement = loginPage.getUsernameElement();
        WebElement passwordElement = loginPage.getPasswordElement();

        usernameElement.sendKeys("klementor373");
        passwordElement.sendKeys("53453932455");
        loginPage.getLoginButton().click();
        assertThat(getElementWithWaiting("//*[@id=\"alerts-container\"]").getAttribute("outerText")).isNotBlank();

        usernameElement.clear();
        usernameElement.sendKeys("klementor373");
        passwordElement.sendKeys("Arhangel373");
        loginPage.getLoginButton().click();
        assertThat(getElementWithWaiting("//*[@id=\"otp-code-text\"]").getAttribute("outerText"))
                .isEqualTo("На ваше устройство отправлен код подтверждения в виде push/SMS *9346, введите его для входа");
    }

    private WebElement getElementWithWaiting(String xpathExpression) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
    }
}
