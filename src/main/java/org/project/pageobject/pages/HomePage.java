package org.project.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.project.pageobject.BasePage;
import org.project.pageobject.modules.EditDetailsModule;
import org.project.pageobject.modules.ProfileModule;
import java.util.List;

public class HomePage extends BasePage {
    @FindBy(xpath = "//button[@data-testid=\"user-widget-link\"]")
    private WebElement profileIcon;
    @FindBy(xpath = "//div[@data-testid=\"user-widget-menu\"]/ul[@role=\"menu\"]//span[contains(text(),'Profile')]")
    private WebElement profileOption;
    @FindBy(xpath = "//span[contains(text(),'Create playlist')]/parent::button")
    private WebElement createPlaylistButton;
    @FindBy(xpath = "//div[@data-encore-id=\"listRow\"]//div/p/span")
    private WebElement myPlaylistInList;
    @FindBy(xpath = "//section[@data-testid=\"playlist-page\"]//div//span/h1")
    private WebElement myPlaylistFromPlaylistPage;
    @FindBy(xpath = "//button[@aria-label=\"Create playlist or folder\"]")
    private WebElement addPlaylistOrFolderButton;
    private String addMenuOptions = "//div[@data-testid=\"context-menu\"]//li//span";
    @FindBy(xpath = "//div[@data-testid=\"context-menu\"]//li//span[contains(text(),'Edit details')]")
    private WebElement editDetailsOption;
    @FindBy(xpath = "//nav//a//span[contains(text(),'Search')]")
    private WebElement searchButton;
    @FindBy(xpath = "//input[@data-testid=\"search-input\"]")
    private WebElement searchInputField;
    @FindBy(xpath = "//div[@class=\"notistack-CollapseWrapper\"]//div/span[contains(text(),'Added to Your Library.')]")
    private WebElement addedToYourLibrarySnackbar;
    @FindBy(xpath = "//div//h2[contains(text(),'Delete from Your Library?')]")
    private WebElement deleteConfirmationPopup;
    @FindBy(xpath = "//span[contains(text(),'Delete')]/parent::button")
    private WebElement deleteButton;
    @FindBy(xpath = "//div[@class=\"notistack-CollapseWrapper\"]//div/span[contains(text(),'Removed from Your Library.')]")
    private WebElement removedFromYourLibrarySnackbar;
    @FindBy(xpath = "//span[contains(text(),'Home')]")
    private WebElement homeIcon;
    @FindBy(xpath = "//div[@data-testid=\"user-widget-menu\"]/ul[@role=\"menu\"]//span[contains(text(),'Log out')]")
    private WebElement logOutButton;
    private String listOfPlaylists = "//ul[@aria-label=\"Your Library\"]//li[@role=\"listitem\" and @draggable]";


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public ProfileModule openProfileModule(String name) {
        waitForElements(profileIcon).click();
        waitForElements(profileOption).click();
        return new ProfileModule(driver);
    }
    public HomePage createPlaylist() {
        waitForElements(createPlaylistButton).click();
        return this;
    }
    public String getPlaylistNameFromList() {
        waitForElements(homeIcon).isDisplayed();
        driver.navigate().refresh();
        return waitForElements(myPlaylistInList).getText();
    }
    public String getPlaylistNameFromMainPage() {
        return waitForElements(myPlaylistFromPlaylistPage).getText();
    }
    public HomePage createNewPlaylist(String name) {
        waitForElements(addPlaylistOrFolderButton).click();
        List<WebElement> addMenuList = driver.findElements(By.xpath(addMenuOptions));
        waitForElements(addMenuList
                .stream()
                .filter(p -> p.getText().equals(name))
                .findAny().get())
                .click();
        waitForElements(addedToYourLibrarySnackbar);
        return this;
    }
    public EditDetailsModule selectEditOptionFromContextMenu(String optionName) {
        Actions action = new Actions(driver);
        action.contextClick(getPlaylistFromList()).perform();
        waitForElements(editDetailsOption);
        getContextMenuOptionOnPlaylist(optionName).click();
        return new EditDetailsModule(driver);
    }
    public PlaylistPage openPlaylistWithAddedSong() {
        waitForElements(myPlaylistInList).isEnabled();
        getPlaylistFromList().click();
        return new PlaylistPage(driver);
    }
    public SearchPage searchForSong(String author) {
        waitForElements(searchButton).click();
        waitForElements(searchInputField);
        searchInputField.sendKeys(author);
        return new SearchPage(driver);
    }
    public HomePage selectDeleteOptionFromContextMenu(String optionName) {
        Actions action = new Actions(driver);
        action.contextClick(getPlaylistFromList()).perform();
        waitForElements(editDetailsOption);
        getContextMenuOptionOnPlaylist(optionName).click();
        return this;
    }
    public StartPage logOut() {
        waitForElements(profileIcon).click();
        waitForElements(logOutButton).click();
        return new StartPage(driver);
    }
    public HomePage clickDeleteInConfirmationPopup() {
        waitForElements(deleteConfirmationPopup);
        deleteButton.click();
        return this;
    }
    public String getTextOfSnackbar() {
        waitForElements(removedFromYourLibrarySnackbar);
        return removedFromYourLibrarySnackbar.getText();
    }
    public WebElement getPlaylistFromList() {
        List<WebElement> playlistsList = driver.findElements(By.xpath(listOfPlaylists));
        return waitForElements(playlistsList
                .stream()
                .findFirst().get());
    }
}
