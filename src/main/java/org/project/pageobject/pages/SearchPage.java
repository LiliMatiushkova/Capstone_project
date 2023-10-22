package org.project.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.project.pageobject.BasePage;

import java.util.List;

public class SearchPage extends BasePage {

    @FindBy(xpath = "//div[@data-testid=\"tracklist-row\"]//div[contains(text(),'I Will Always Love You')]")
    private WebElement songToAdd;
    @FindBy(xpath = "//div[contains(@style,'context-menu-submenu')]//li/button/span[contains(text(),'My Playlist #1')]")
    private WebElement existingPlaylist;
    private String subMenuItems = "//div[contains(@style,'context-menu-submenu')]//li/button/span";
    @FindBy(xpath = "//div[@role=\"presentation\"]//button//span[contains(text(),'Songs')]")
    private WebElement songsFilter;

    public SearchPage(WebDriver driver) {
        super(driver);
    }
    public SearchPage filterForSongs() {
        waitForElements(songsFilter).click();
        waitForElements(songToAdd).isDisplayed();
        return this;
    }
    public HomePage addSongToPlaylist(String trackName, String playlistName) {
        Actions action = new Actions(driver);
        action.contextClick(getSongFromList(trackName)).perform();
        getContextMenuOptionOnTrack("Add to playlist").click();
        waitForElements(existingPlaylist);
        List<WebElement> subMenuList = driver.findElements(By.xpath(subMenuItems));
        waitForElements(subMenuList
                .stream()
                .filter(p -> p.getText().contains(playlistName))
                .findFirst().get())
                .click();
        return new HomePage(driver);
    }
}
