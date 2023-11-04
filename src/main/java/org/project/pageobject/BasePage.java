package org.project.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BasePage {

    private String contextMenuOptionsOnTrack = "//div[@id=\"context-menu\"]//button//span";
    private String contextMenuOptionsOnPlaylist = "//div[@id=\"context-menu\"]//li//button//span";

    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement waitForElements(WebElement element){
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    public WebElement getContextMenuOptionOnPlaylist(String option) {
        List<WebElement> contextMenuList = driver.findElements(By.xpath(contextMenuOptionsOnPlaylist));
        return waitForElements(contextMenuList
                .stream()
                .filter(p -> p.getText().equals(option))
                .findFirst().get());
    }
    public WebElement getContextMenuOptionOnTrack(String optionName){
        List<WebElement> contextMenuList = driver.findElements(By.xpath(contextMenuOptionsOnTrack));
        return waitForElements(contextMenuList
                .stream()
                .filter(p -> p.getText().equals(optionName))
                .findFirst().get());
    }
}