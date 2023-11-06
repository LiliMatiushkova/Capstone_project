package org.project.pageobject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.project.pageobject.BasePage;

public class StartPage extends BasePage {

    @FindBy(xpath = "//button[@data-testId=\"login-button\"]")
    private WebElement loginButton;
    public StartPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage openLoginPage() {
        waitForElements(loginButton).click();
        return new LoginPage(driver);
    }
    public StartPage openStartPage() {
        driver.get("https://open.spotify.com/");
        return this;
    }
}
