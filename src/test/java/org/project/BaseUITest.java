package org.project;

import org.project.pageobject.pages.HomePage;

public class BaseUITest extends BaseTest{


    protected void createNewPlaylist() {
        HomePage homePage = new HomePage(driver);
        homePage
                .createNewPlaylist("Create a new playlist");
    }
    protected void addSongToPlaylist(String author, String trackName, String playlistName) {
        HomePage homePage = new HomePage(driver);
        homePage
                .searchForSong(author)
                .filterForSongs()
                .addSongToPlaylist(trackName, playlistName)
                .openPlaylistWithAddedSong();
    }
    public void deletePlaylist() {
        HomePage homePage = new HomePage(driver);
        homePage
                .selectDeleteOptionFromContextMenu("Delete")
                .clickDeleteInConfirmationPopup();
    }
}
