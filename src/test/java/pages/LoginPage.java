package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

    public static final String loginUrl = "https://i.bspb.ru/auth";
    public static final String CODE_TEXT = "//*[@id='otp-code-text']";
    public static final String alertsContainer = "//*[@id='alerts-container']";

    @FindBy(xpath = "//*[@id='logo']")
    private WebElement logoText;

    @FindBy(xpath = "//input[@name='username']")
    private WebElement userNameInput;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@id='login-button']")
    private WebElement loginButton;

    public WebElement getLogoText() {
        return logoText;
    }

    public WebElement getUserNameInput() {
        return userNameInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }
}
