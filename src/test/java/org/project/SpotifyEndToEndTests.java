package org.project;

import org.project.pageobject.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SpotifyEndToEndTests {
    String playlistName = "New Playlist";
    String playlistDescription = "New playlist description";
    String updatedPlaylistName = "Updated Playlist";
    String updatedPlaylistDescription = "Updated playlist description";
    private BaseAPITest apiTest = new BaseAPITest();
    private BaseUITest uiTest = new BaseUITest();

    @BeforeMethod
    public void setUpDriver() {
        uiTest.setUp();
    }

    /*@AfterMethod
    public void closeDriver() {
        uiTest.close();
    }*/

    @Test
    public void addSongToPlaylistTest() {
        String trackName = "I Will Always Love You";

        apiTest.createPlaylistAPI(playlistName, playlistDescription);
        uiTest.login();
        HomePage homePage = new HomePage(uiTest.driver);
        String nameOfAddedTrackInPlaylist = homePage
                .searchForSong("Whitney Elizabeth Houston")
                .filterForSongs()
                .addSongToPlaylist(trackName, playlistName)
                .openPlaylistWithAddedSong()
                .getNameOfAddedTrack(trackName);

        Assert.assertEquals(nameOfAddedTrackInPlaylist, trackName);
    }
    @Test
    public void editDetailsOfPlaylistTest() {
        String playlistId = apiTest.createPlaylistAPI(playlistName, playlistDescription).get("id");
        apiTest.updatePlaylistAPI(updatedPlaylistName, updatedPlaylistDescription, playlistId);
        uiTest.login();
        HomePage homePage = new HomePage(uiTest.driver);
        homePage
                .logOut()
                .openLoginPage()
                .typeCredentials(uiTest.username, uiTest.password)
                .successLogin();
        String playlistNameFromList = homePage
                .getPlaylistNameFromList();

        Assert.assertEquals(playlistNameFromList, updatedPlaylistName);
    }
}
