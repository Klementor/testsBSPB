package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.lang.model.element.Element;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mihai\\IdeaProjects\\testsBSPB\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.bspb.ru/");

        WebElement element = driver.findElement(By.xpath("//a[@class = \"chakra-link css-1hxq3ev\"]"));

        String hrefToLogIn = element.getAttribute("href");

        System.out.println(hrefToLogIn);

        driver.get(hrefToLogIn);

        //Через element.click(); почему-то не работает, пока что не разобрался
    }
}