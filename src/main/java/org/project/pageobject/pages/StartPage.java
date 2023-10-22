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
        driver.get("https://open.spotify.com/");
        loginButton.click();
        return new LoginPage(driver);
    }
}
