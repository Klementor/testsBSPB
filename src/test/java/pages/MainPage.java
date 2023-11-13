package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage {
    public static final By communicationBtn = By.xpath("//button[.//*[text()='Связь с банком']]");

    @FindBy(xpath = "//button[.//*[text()='Карты']]")
    private WebElement cardElement;

    @FindBy(xpath = "//a[.//*[text()='Войти ']]")
    private WebElement loginElement;

    @FindBy(xpath = "//button[.//*[text()='Связь с банком']]")
    private WebElement communicationElement;

    public WebElement getCardElement() {
        return cardElement;
    }

    public WebElement getLoginElement() {
        return loginElement;
    }

    public WebElement getCommunicationElement() {
        return communicationElement;
    }
}
