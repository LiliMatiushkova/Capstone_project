package org.project.pageobject.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.project.pageobject.BasePage;
import org.project.pageobject.pages.HomePage;

public class EditDetailsModule extends BasePage {
    @FindBy(xpath = "//input[@data-testid=\"playlist-edit-details-name-input\"]")
    private WebElement nameInput;
    @FindBy(xpath = "//button[@data-testid=\"playlist-edit-details-save-button\"]")
    private WebElement saveButton;
    public EditDetailsModule(WebDriver driver) {
        super(driver);
    }
    public HomePage editNameOfPlaylist(String newName) {
        waitForElements(nameInput).clear();
        nameInput.sendKeys(newName);
        saveButton.click();
        return new HomePage(driver);
    }
}
