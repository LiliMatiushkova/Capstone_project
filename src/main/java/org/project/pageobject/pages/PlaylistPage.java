package org.project.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.project.pageobject.BasePage;

import java.util.List;


public class PlaylistPage extends BasePage {
    @FindBy(xpath = "//div[@data-testid=\"tracklist-row\"]//div//a/div[contains(text(),'I Will Always Love You')]")
    private WebElement addedTrack;
    @FindBy(xpath = "//div[@data-testid=\"tracklist-row\"]//div//a/div[contains(text(),'Greatest Love of All')]")
    private WebElement addedTrackToDelete;
    @FindBy(xpath = "//section[@data-testid=\"playlist-page\"]//div//span/h1")
    private WebElement myPlaylist;
    private String listOfSongs = "//div[@data-testid=\"tracklist-row\"]//div[@dir=\"auto\"]";
    public PlaylistPage(WebDriver driver) {
        super(driver);
    }
    public String getNameOfAddedTrack() {
        waitForElements(addedTrack).isDisplayed();
        return addedTrack.getText();
    }
    public PlaylistPage removeTrackFromPlaylist(String trackName) {
        waitForElements(addedTrackToDelete).isDisplayed();
        Actions action = new Actions(driver);
        action.contextClick(addedTrackToDelete).perform();
        getContextMenuOptionOnTrack("Remove from this playlist").click();
        return this;
    }
    public List<WebElement> getListOfSongs() {
        List<WebElement> songsList = driver.findElements(By.xpath(listOfSongs));
        return songsList;
    }
}
