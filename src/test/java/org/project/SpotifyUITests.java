package org.project;

import org.openqa.selenium.WebElement;
import org.project.pageobject.pages.HomePage;
import org.project.pageobject.pages.LoginPage;
import org.project.pageobject.pages.PlaylistPage;
import org.project.pageobject.pages.StartPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class SpotifyUITests extends BaseUITest {
    @BeforeMethod
    public void setUpDriver() {
        setUp();
    }
    @AfterMethod
    public void closeDriver() {
        close();
    }

    @Test
    public void spotifyLoginWithEmptyCredentialsTest() {
        String expectedUserNameErrorMessage = "Please enter your Spotify username or email address.";
        String expectedPasswordErrorMessage = "Please enter your password.";
        StartPage startPage = new StartPage(driver);
        String userNameErrorMessage = startPage
                .openLoginPage()
                .typeCredentials("testName","testPassword")
                .getErrorForClearNameField();
        LoginPage loginPage = new LoginPage(driver);
        String passwordErrorMessage = loginPage
                .getErrorForClearPasswordField();
        Assert.assertEquals(userNameErrorMessage, expectedUserNameErrorMessage);
        Assert.assertEquals(passwordErrorMessage, expectedPasswordErrorMessage);
    }

    @Test(dataProvider = "wrongCredentials")
    public void spotifyLoginWithIncorrectCredentialsTest(String login, String password) {
        String expectedErrorMessage = "Incorrect username or password.";
        StartPage startPage = new StartPage(driver);
        String errorMessage = startPage
                .openLoginPage()
                .typeCredentials(login,password)
                .getErrorForIncorrectCredentials();
        Assert.assertEquals(errorMessage, expectedErrorMessage);
    }

    @Test
    public void spotifySuccessLoginTest() {
        String expectedUserInfoText = "Liliia";
        StartPage startPage = new StartPage(driver);
        String givenUserInfoText = startPage
                .openLoginPage()
                .typeCredentials(username, password)
                .successLogin()
                .openProfileModule("Profile")
                .getUserInfoText();
        Assert.assertEquals(givenUserInfoText, expectedUserInfoText);
    }

    @Test
    public void createPlaylistTest() {
        try {
            login();
            HomePage homePage = new HomePage(driver);
            String playlistNameFromList = homePage
                    .createPlaylist()
                    .getPlaylistNameFromList();
            String playlistNameFromMainPage = homePage
                    .getPlaylistNameFromMainPage();
            Assert.assertEquals(playlistNameFromList, playlistNameFromMainPage);
        } catch (Exception e) {
            System.out.println("Playlist exist");
        }
        finally {
            deletePlaylist();
        }
    }

    @Test
    public void editDetailsOfPlaylistTest() {
        login();
        HomePage homePage = new HomePage(driver);
        String editedPlaylistNameFromList = homePage
                .createNewPlaylist("Create a new playlist")
                .selectEditOptionFromContextMenu("Edit details")
                .editNameOfPlaylist("My favorite playlist")
                .getPlaylistNameFromList();
        String editedPlaylistNameFromMainPage = homePage
                .getPlaylistNameFromMainPage();
        Assert.assertEquals(editedPlaylistNameFromList, editedPlaylistNameFromMainPage);
    }

    @Test
    public void searchAndAddToPlaylistTest() {
        String trackName = "I Will Always Love You";
        login();
        HomePage homePage = new HomePage(driver);
        String nameOfAddedTrackInPlaylist = homePage
                .createNewPlaylist("Create a new playlist")
                .searchForSong("Whitney Elizabeth Houston")
                .filterForSongs()
                .addSongToPlaylist(trackName, "My Playlist")
                .openPlaylistWithAddedSong()
                .getNameOfAddedTrack(trackName);
        Assert.assertEquals(nameOfAddedTrackInPlaylist, trackName);
    }

    @Test
    public void removeSongFromPlaylist() {
        String trackName = "Greatest Love of All";
        login();
        createNewPlaylist();
        addSongToPlaylist("Whitney Houston", trackName, "My Playlist");
        PlaylistPage playlistPage = new PlaylistPage(driver);
        List<WebElement> listOfSongsAddedToPlaylist = playlistPage
                .removeTrackFromPlaylist(trackName)
                .getAllSongsFromPlaylist();
        Assert.assertFalse(listOfSongsAddedToPlaylist.contains(trackName));
    }

    @Test
    public void deletePlaylistTest() {
        String expectedSnackbarText = "Removed from Your Library.";
        login();
        createNewPlaylist();
        HomePage homePage = new HomePage(driver);
        String snackbarText = homePage
                .selectDeleteOptionFromContextMenu("Delete")
                .clickDeleteInConfirmationPopup()
                .getTextOfSnackbar();
        Assert.assertEquals(snackbarText, expectedSnackbarText);
    }

    @DataProvider(name = "wrongCredentials")
    public Object[][] wrongCredentials() {
        return new Object[][] {
                {"qwerty", "Test12345"},
                {"lilia_0801@ukr.net", "qwerty"},
                {"qwerty", "qwerty"}
        };
    }
}
