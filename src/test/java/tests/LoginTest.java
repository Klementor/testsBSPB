package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {

    // ToDo:
    //  Посмотреть как в одном месте прописать таймауты 60-90 минут 13.11
    //  Добавить систему которая строит отчеты по тестам Allure 120-180 минут 13.11
    //  Перенести логины, пароли, url и др. в файл app.properties 30-60 минут 09.11 done

    private static String OUTER_TEXT_ATTRIBUTE;
    private static String TEXT_SECOND_AUTHENTICATION;
    private String correctUserName;
    private String incorrectRegisterUserName;
    private String correctPassword;
    private String incorrectRegisterPassword;
    private String incorrectPassword;
    private WebDriver driver;
    private LoginPage loginPage;

    private WebElement userNameInput;
    private WebElement passwordInput;

    @BeforeEach
    public void openingWebsite() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mihai\\IdeaProjects\\testsBSPB\\chromedriver\\chromedriver.exe");
        driver = WebDriverManager.chromedriver().create();
        driver.manage().window().maximize();
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
        softly.assertThat(loginPage.getLogoText().getAttribute("alt")) //TODO: перевести на softAssertions 45-75 минут 09.11
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
        String userName = getRandomString(10, true, true); //ToDo: реализовать метод который генерирует необходимые значения для пароля и логина 45-75 минут 09.11
        userNameInput.sendKeys(userName);
        WebElement passwordElement = loginPage.getPasswordInput();
        String password = getRandomString(10, true, true);
        passwordElement.sendKeys(password);
        softly.assertThat(userNameInput.getAttribute("value")) //TODO: перевести на softAssertions 45-75 минут 09.11
                .as("userName из элемента страницы авторизации")
                .isEqualTo(userName);
        softly.assertThat(passwordElement.getAttribute("value"))
                .as("password из элемента страницы авторизации")
                .isEqualTo(password);
    }

    @Test
    public void registerTest() { //TODO: разделить на разные тесты 15-30 минут 09.11 done
        userNameInput.sendKeys(correctUserName);
        passwordInput.sendKeys(correctPassword);
        loginPage.getLoginButton().click();
        assertThat(getElementWithWaiting(LoginPage.CODE_TEXT).getAttribute(OUTER_TEXT_ATTRIBUTE))
                .as("Текст двойной аутентификации")
                .isEqualTo(TEXT_SECOND_AUTHENTICATION);
    }

    @Test
    public void incorrectRegisterPasswordTest() {
        userNameInput.sendKeys(correctUserName);
        passwordInput.sendKeys(incorrectRegisterPassword);
        loginPage.getLoginButton().click();
        assertThat(getElementWithWaiting(LoginPage.alertsContainer).getAttribute(OUTER_TEXT_ATTRIBUTE))
                .as("Текст ошибки авторизации")
                .isNotBlank();
    }

    @Test
    public void IncorrectRegisterUserNameTest() {
        userNameInput.sendKeys(incorrectRegisterUserName);
        passwordInput.sendKeys(correctPassword);
        loginPage.getLoginButton().click();
        assertThat(getElementWithWaiting(LoginPage.CODE_TEXT).getAttribute(OUTER_TEXT_ATTRIBUTE))
                .as("Текст двойной аутентификации")
                .isEqualTo(TEXT_SECOND_AUTHENTICATION);
    }

    @Test
    public void incorrectPasswordTest() {
        userNameInput.sendKeys(correctUserName); //TODO: объединить с 98 строкой и сделать параметризованный тест selenium framework 120-180 минут 13.11
        passwordInput.sendKeys(incorrectPassword);
        loginPage.getLoginButton().click();
        assertThat(getElementWithWaiting(LoginPage.alertsContainer).getAttribute(OUTER_TEXT_ATTRIBUTE))
                .as("Текст ошибки авторизации")
                .isNotBlank();
    }

    private void loadProperties() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("C:\\Users\\mihai\\IdeaProjects\\testsBSPB\\app.properties"));
        OUTER_TEXT_ATTRIBUTE = prop.getProperty("outerTextAttribute");
        TEXT_SECOND_AUTHENTICATION = prop.getProperty("textSecondAuthentication");
        correctUserName = prop.getProperty("correctUserName");
        incorrectRegisterUserName = prop.getProperty("incorrectRegisterUserName");
        correctPassword = prop.getProperty("correctPassword");
        incorrectRegisterPassword = prop.getProperty("incorrectRegisterPassword");
        incorrectPassword = prop.getProperty("incorrectPassword");
    }

    private String getRandomString(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    private WebElement getElementWithWaiting(String xpathExpression) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
    }
}
