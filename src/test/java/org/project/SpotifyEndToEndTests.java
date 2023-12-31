package org.project;

import org.project.dto.PlaylistResponse;
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
    Boolean updatedPublic = Boolean.FALSE;
    private BaseAPITest apiTest = new BaseAPITest();
    private BaseUITest uiTest = new BaseUITest();

    @BeforeMethod
    public void setUpDriver() {
        uiTest.setUp();
    }

    @AfterMethod
    public void closeDriver() {
        uiTest.close();
    }

    @Test
    public void addSongToPlaylistTest() {
        String trackName = "I Will Always Love You - Film Version";

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
        PlaylistResponse createdPlaylist = apiTest.createPlaylistAPI(playlistName, playlistDescription);
        String playlistId = createdPlaylist.getId();
        apiTest.updatePlaylistAPI(updatedPlaylistName, updatedPlaylistDescription, updatedPublic, playlistId);
        String updatedPlaylistName = apiTest.getPlaylistAPI(playlistId).getName(); //to check if playlist details are updated
        System.out.println(updatedPlaylistName);
        uiTest.login();
        HomePage homePage = new HomePage(uiTest.driver);
        homePage
                .logOut()
                .openLoginPage()
                .typePasswordForSecondLogin(uiTest.password)
                .successLogin();
        String playlistNameFromList = homePage
                .getPlaylistNameFromList();

        Assert.assertEquals(playlistNameFromList, updatedPlaylistName);
    }
}
