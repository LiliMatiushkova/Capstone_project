package org.project.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.project.pageobject.BasePage;
import java.util.List;

public class PlaylistPage extends BasePage {
    private String listOfSongs = "//div[@data-testid=\"tracklist-row\"]//div[@dir=\"auto\"]";
    @FindBy(xpath = "//div[@role=\"presentation\"]//div//span[contains(text(),'Title')]")
    private WebElement titleColumn;
    public PlaylistPage(WebDriver driver) {
        super(driver);
    }
    public String getNameOfAddedTrack(String trackName) {
        waitForElements(titleColumn).isDisplayed();
        return getSongFromPlaylist(trackName).getText();
    }
    public PlaylistPage removeTrackFromPlaylist(String trackName) {
        waitForElements(titleColumn).isDisplayed();
        Actions action = new Actions(driver);
        action.contextClick(getSongFromPlaylist(trackName)).perform();
        getContextMenuOptionOnTrack("Remove from this playlist").click();
        return this;
    }
    public List<WebElement> getAllSongsFromPlaylist() {
        List<WebElement> songsList = driver.findElements(By.xpath(listOfSongs));
        return songsList;
    }
    public WebElement getSongFromPlaylist(String trackName) {
        waitForElements(titleColumn).isDisplayed();
        List<WebElement> songsList = driver.findElements(By.xpath(listOfSongs));
        return waitForElements(songsList
                .stream()
                .filter(p -> p.getText().contains(trackName))
                .findFirst().get());
    }
}
