package org.project.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.project.pageobject.BasePage;


public class ProfileModule extends BasePage {

    @FindBy(xpath = "//h1[@dir=\"auto\"]")
    private WebElement userInfo;
    public ProfileModule(WebDriver driver) {
        super(driver);
    }
    public String getUserInfoText() {
        return waitForElements(userInfo).getText();
    }
}
