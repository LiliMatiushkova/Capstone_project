package org.project;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SpotifyEndToEndTests extends BaseAPITest{
    private static final Logger logger = LoggerFactory.getLogger(SpotifyEndToEndTests.class);

    @BeforeMethod
    public void setUpDriver() {
        setUpDriverAndBrowser();
    }

    @AfterMethod
    public void closeDriver() {
        closeDriverAndBrowser();
    }

    @Test
    public void addSongToPlaylistTest() {
        // arrange
        final String trackName = "I Will Always Love You";
        var playListName = RandomStringUtils.random(10, true, false);
        var description = RandomStringUtils.random(30, true, false);
        createPlaylistAPI(playListName, description);

        // act
        var homePage = loginWithValidCredentials();
        var nameOfAddedTrackInPlaylist = homePage
                .searchForSong("Whitney Elizabeth Houston")
                .filterForSongs()
                .addSongToPlaylist(trackName, playListName)
                .openPlaylistWithAddedSong()
                .getNameOfAddedTrack(trackName);

        // assert
        assertEquals(nameOfAddedTrackInPlaylist, trackName);
    }

    @Test
    public void editDetailsOfPlaylistTest() {
        // arrange
        var playListName = RandomStringUtils.random(200, true, false);
        var description = RandomStringUtils.random(30, true, false);
        var updatedName = RandomStringUtils.random(10, true, false);
        var updatedDescription = RandomStringUtils.random(30, true, false);
        var playlistId = createPlaylistAPI(playListName, description).getId();

        // act
        updatePlaylistRest(updatedName, updatedDescription, false, playlistId);
        logger.info(getPlaylistRest(playlistId).getName()); //to check if playlist details are updated
        var homePage = loginWithValidCredentials();

        // assert
        var playlistNameFromList = homePage.getPlaylistNameFromList();
        assertEquals(playlistNameFromList, updatedName);
    }
}
