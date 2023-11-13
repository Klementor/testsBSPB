package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.LoginPage;
import pages.MainPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {

    private static String OUTER_TEXT_ATTRIBUTE;
    private static String TEXT_SECOND_AUTHENTICATION;
    private String correctUserName;
    private String incorrectRegisterUserName;
    private String correctPassword;
    private WebDriver driver;
    private LoginPage loginPage;

    private WebElement userNameInput;
    private WebElement passwordInput;

    @BeforeEach
    public void openingWebsite() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\BSPB\\IdeaProjects\\testsBSPB\\chromedriver\\chromedriver.exe");
        driver = WebDriverManager.chromedriver().create();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bspb.ru/");
        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        WebElement loginElement = mainPage.getLoginElement();
        loginElement.click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        loadProperties();
        userNameInput = loginPage.getUserNameInput();
        passwordInput = loginPage.getPasswordInput();
    }

    @AfterEach
    public void closeWebSite() {
        driver.close();
        driver.quit();
    }

    @Test
    public void openLoginPageTest() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(loginPage.getLogoText().getAttribute("alt"))
                .as("Логотип страницы авторизации")
                .isEqualTo("Интернет банк - Банк Санкт-Петербург");
        softly.assertThat(6).as("Неправильный тест написанный специально").isEqualTo(5);
        softly.assertThat(driver.getCurrentUrl())
                .as("url страницы авторизации")
                .startsWith(LoginPage.loginUrl);
        System.out.println("Эта строка выведется даже если тест до нее упадет");
        softly.assertAll();
    }

    @Test
    public void correctFillingOfFieldsTest() {
        SoftAssertions softly = new SoftAssertions();
        String userName = getRandomString(10, true, true);
        userNameInput.sendKeys(userName);
        WebElement passwordElement = loginPage.getPasswordInput();
        String password = getRandomString(10, true, true);
        passwordElement.sendKeys(password);
        softly.assertThat(userNameInput.getAttribute("value"))
                .as("userName из элемента страницы авторизации")
                .isEqualTo(userName);
        softly.assertThat(passwordElement.getAttribute("value"))
                .as("password из элемента страницы авторизации")
                .isEqualTo(password);
    }

    @Test
    public void registerTest() {
        userNameInput.sendKeys(correctUserName);
        passwordInput.sendKeys(correctPassword);
        loginPage.getLoginButton().click();
        assertThat(driver.findElement(By.xpath(LoginPage.CODE_TEXT)).getAttribute("outerText"))
                .as("Текст двойной аутентификации")
                .isEqualTo(TEXT_SECOND_AUTHENTICATION);
    }

    @ParameterizedTest
    @ValueSource(strings = {"incorrectRegisterPassword", "incorrectPassword"})
    public void incorrectRegisterPasswordTest(String password) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("C:\\Users\\BSPB\\IdeaProjects\\testsBSPB\\app.properties"));
        String incorrectPassword = prop.getProperty(password);
        userNameInput.sendKeys(correctUserName);
        passwordInput.sendKeys(incorrectPassword);
        loginPage.getLoginButton().click();
        assertThat(driver.findElement(By.xpath(LoginPage.alertsContainer)).getAttribute(OUTER_TEXT_ATTRIBUTE))
                .as("Текст ошибки авторизации")
                .isNotBlank();
    }

    @Test
    public void IncorrectRegisterUserNameTest() {
        userNameInput.sendKeys(incorrectRegisterUserName);
        passwordInput.sendKeys(correctPassword);
        loginPage.getLoginButton().click();
        assertThat(driver.findElement(By.xpath(LoginPage.CODE_TEXT)).getAttribute(OUTER_TEXT_ATTRIBUTE))
                .as("Текст двойной аутентификации")
                .isEqualTo(TEXT_SECOND_AUTHENTICATION);
    }

    private void loadProperties() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("C:\\Users\\BSPB\\IdeaProjects\\testsBSPB\\app.properties"));
        OUTER_TEXT_ATTRIBUTE = prop.getProperty("outerTextAttribute");
        TEXT_SECOND_AUTHENTICATION = prop.getProperty("textSecondAuthentication");
        correctUserName = prop.getProperty("correctUserName");
        incorrectRegisterUserName = prop.getProperty("incorrectRegisterUserName");
        correctPassword = prop.getProperty("correctPassword");
    }

    private String getRandomString(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
