package org.project.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    private String listOfPlaylists = "//ul[@aria-label=\"Your Library\"]//li[@role=\"listitem\" and @draggable]";
    private String listOfSongs = "//div[@data-testid=\"tracklist-row\"]//div[@dir=\"auto\"]";
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
    public WebElement getPlaylistFromList() {
        List<WebElement> playlistsList = driver.findElements(By.xpath(listOfPlaylists));
        return waitForElements(playlistsList
                .stream()
                .findFirst().get());
    }
    public WebElement getSongFromList(String trackName) {
        List<WebElement> songsList = driver.findElements(By.xpath(listOfSongs));
        return waitForElements(songsList
                .stream()
                .filter(p -> p.getText().equals(trackName))
                .findFirst().get());
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