import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.sql.Driver;

public class MainTest {


//    @BeforeEach
//    public void openingWebsite() {
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mihai\\IdeaProjects\\testsBSPB\\chromedriver\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("https://www.bspb.ru/");
//    }

    @Test
    public void testNawbar() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mihai\\IdeaProjects\\testsBSPB\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.bspb.ru/");

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
    public void openLoginPageTest () {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mihai\\IdeaProjects\\testsBSPB\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.bspb.ru/");

        WebElement element = driver.findElement(By.xpath("//a[.//*[text()='Войти ']]"));

        element.click();
    }

}
