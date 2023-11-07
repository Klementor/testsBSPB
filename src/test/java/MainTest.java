import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public class MainTest {
    private WebDriver driver;

    @BeforeEach
    public void openingWebsite() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mihai\\IdeaProjects\\testsBSPB\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.bspb.ru/");
    }

    @AfterEach
    public void closeWebSite() {
        driver.close();
        driver.quit();
    }

    @Test
    public void testNawbar() {
        WebElement element = driver.findElement(By.xpath("//button[.//*[text()='Карты']]"));
        String ariaExpended = element.getAttribute("aria-expanded");
        Assertions.assertEquals(ariaExpended, "false");
        new Actions(driver)
                .moveToElement(element)
                .perform();
        String ariaExpendedUpd = element.getAttribute("aria-expanded");
        Assertions.assertEquals(ariaExpendedUpd, "true");
    }

    @Test
    public void openLoginPageTest() {
        WebElement element = driver.findElement(By.xpath("//a[.//*[text()='Войти ']]"));
        element.click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        WebElement elementLogo = (new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='logo']"))));
        Assertions.assertEquals(elementLogo.getAttribute("alt"), "Интернет банк - Банк Санкт-Петербург");
        Assertions.assertEquals(driver.getCurrentUrl(), "https://i.bspb.ru/auth?response_type=code&client_id=1&red" +
                "irect_uri=https%3A%2F%2Fi.bspb.ru%2Flogin%2Fsuccess&prefetch_uri=https%3A%2F%2Fi.bspb.ru%2Flogin%2Fpref" +
                "etch&force_new_session=true");
    }

    @Test
    public void openCommunicationWithTheBankTest() {
        WebElement element = driver.findElement(By.xpath("//button[.//*[text()='Связь с банком']]"));
        element.click();
        WebElement elementPrivateClients = (new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Частным клиентам']"))));
        String string = elementPrivateClients.getAttribute("textContent");
        Assertions.assertEquals(string, "Частным клиентам");
        Assertions.assertEquals(driver.getCurrentUrl(), "https://www.bspb.ru/feedback");
    }
}
