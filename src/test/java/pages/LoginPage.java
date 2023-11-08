package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

    @FindBy(xpath = "//*[@id='logo']")
    private WebElement logoElement;

    @FindBy(xpath = "//input[@name='username']")
    private WebElement usernameElement;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordElement;

    @FindBy(xpath = "//button[@id='login-button']")
    private WebElement loginButton;

    public WebElement getLogoElement() {
        return logoElement;
    }

    public WebElement getUsernameElement() {
        return usernameElement;
    }

    public WebElement getPasswordElement() {
        return passwordElement;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }
}
