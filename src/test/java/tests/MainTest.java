package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;

import java.time.Duration;

public class MainTest {

    private static final String feedbackUrl = "https://www.bspb.ru/feedback";

    private WebDriver driver;

    private MainPage mainPage;

    @BeforeEach
    public void openingWebsite() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mihai\\IdeaProjects\\testsBSPB\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.bspb.ru/");
        mainPage = PageFactory.initElements(driver, MainPage.class);
    }

    @AfterEach
    public void closeWebSite() {
        driver.close();
        driver.quit();
    }

    @Test
    public void testNawbar() {
        WebElement cardElement = mainPage.getCardElement();
        String ariaExpended = cardElement.getAttribute("aria-expanded");
        Assertions.assertEquals(ariaExpended, "false");
        new Actions(driver)
                .moveToElement(cardElement)
                .perform();
        String ariaExpendedUpd = cardElement.getAttribute("aria-expanded");
        Assertions.assertEquals(ariaExpendedUpd, "true");
    }

    @Test
    public void openCommunicationWithTheBankTest() {
        WebElement communicationElement = mainPage.getCommunicationElement();
        communicationElement.click();
        WebElement elementPrivateClients = getElementWithWaiting("//h2[text()='Частным клиентам']");
        String string = elementPrivateClients.getAttribute("textContent");
        Assertions.assertEquals(string, "Частным клиентам");
        Assertions.assertEquals(driver.getCurrentUrl(), feedbackUrl);
    }

    private WebElement getElementWithWaiting(String xpathExpression) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
    }
}
