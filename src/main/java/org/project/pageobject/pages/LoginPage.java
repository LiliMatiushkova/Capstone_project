package org.project.pageobject.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.project.pageobject.BasePage;

public class LoginPage extends BasePage {
    @FindBy(id = "login-username")
    private WebElement userNameField;
    @FindBy(id = "login-password")
    private WebElement passwordField;
    @FindBy(id = "login-button")
    private WebElement logInButton;
    @FindBy(xpath = "//div[@id=\"username-error\"]//span/p")
    private WebElement userNameError;
    @FindBy(xpath = "//div[@id=\"password-error\"]//span")
    private WebElement passwordError;
    @FindBy(xpath = "//div[@data-encore-id=\"banner\"]//span")
    private WebElement incorrectUserNameOrPasswordError;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public HomePage successLogin() {
        waitForElements(logInButton).click();
        return new HomePage(driver);
    }
    public LoginPage typeCredentials(String userName, String password) {
        waitForElements(userNameField);
        this.userNameField.sendKeys(userName);
        this.passwordField.sendKeys(password);
        return this;
    }
    public String getErrorForClearNameField() {
        userNameField.sendKeys(Keys.BACK_SPACE);
        return waitForElements(userNameError).getText();
    }
    public String getErrorForClearPasswordField() {
        passwordField.sendKeys(Keys.BACK_SPACE);
        return waitForElements(passwordError).getText();
    }
    public String getErrorForIncorrectCredentials() {
        logInButton.click();
        return waitForElements(incorrectUserNameOrPasswordError).getText();
    }
}
