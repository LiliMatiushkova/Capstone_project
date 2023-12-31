package org.project;


import org.project.dto.Playlist;
import org.project.dto.PlaylistResponse;
import org.project.dto.TrackRequest;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpotifyAPITests extends BaseAPITest {
    String trackUri = "spotify:track:4yIfjMoivhXnY9lZkoVntq";
    String playlistName = "New Playlist";
    String playlistDescription = "New playlist description";
    String updatedPlaylistName = "Updated Playlist";
    String updatedPlaylistDescription = "Updated playlist description";
    Boolean Public = Boolean.FALSE;

    @Test
    public void createPlaylistTest() {
        Playlist expectedPlaylist = createPlaylist(playlistName, playlistDescription);
        var createResponse = given()
                .spec(playlistSpec.getPlaylistCreateSpec(expectedPlaylist))
                .when()
                .post()
                .then()
                .spec(playlistSpec.getResponseSpecCheckCreated())
                .extract().body().as(PlaylistResponse.class);

        Assert.assertEquals(createResponse.getName(), expectedPlaylist.getName());
        Assert.assertEquals(createResponse.getDescription(), expectedPlaylist.getDescription());
        Assert.assertEquals(createResponse.getPublic(), expectedPlaylist.getPublic());
    }

    @Test
    public void getPlaylistById() {
        String playlistId = createPlaylistAPI(playlistName, playlistDescription).getId();
        var getResponse = given()
                .spec(playlistSpec.getPlaylistGetSpec(playlistId))
                .when()
                .get()
                .then()
                .spec(playlistSpec.getResponseSpecCheckGetOk())
                .extract().body().as(PlaylistResponse.class);

        Assert.assertEquals(playlistName, getResponse.getName());
    }

    @Test
    public void editPlaylistDetails() {
        String playlistId = createPlaylistAPI(playlistName, playlistDescription).getId();
        Playlist editedNewPlaylist = updatePlaylist(updatedPlaylistName, updatedPlaylistDescription, Public);
        given()
                .spec(playlistSpec.getPlaylistUpdateSpec(playlistId, editedNewPlaylist))
                .when()
                .put()
                .then()
                .spec(playlistSpec.getResponseSpecCheckOk());
    }

    @Test
    public void addItemsToPlaylist() {
        String playlistId = createPlaylistAPI(playlistName, playlistDescription).getId();
        var addItemsResponse = given()
                .spec(playlistSpec.getAddItemsToPlaylistSpec(playlistId, createTrack(trackUri)))
                .when()
                .post()
                .then()
                .spec(playlistSpec.getResponseSpecCheckCreated()).log().body()
                .extract().body().as(TrackRequest.class);

        Assert.assertNotNull(addItemsResponse.getSnapshot_id());
    }

    @Test
    public void removeSongFromPlaylist() {
        String playlistId = createPlaylistAPI(playlistName, playlistDescription).getId();
        var addTrackResponse = addTrackToPlaylistAPI(playlistId, trackUri);
        String snapshotId = addTrackResponse.getSnapshot_id();
        Assert.assertNotNull(snapshotId);
        var deleteTrackFromPlaylistResponse = given()
                .spec(playlistSpec.removeTrackFromPlaylistSpec(playlistId, trackToDelete(snapshotId, trackUri)))
                .when()
                .delete()
                .then()
                .spec(playlistSpec.getResponseSpecCheckDeleted())
                .extract().body().as(TrackRequest.class);

        Assert.assertNotNull(deleteTrackFromPlaylistResponse.getSnapshot_id());
    }

    @AfterSuite
    public void getAllPlaylists() {
        var getAllPlaylistsResponse = given()
                .spec(playlistSpec.getAllPlaylistSpec())
                .when()
                .get()
                .then()
                .spec(playlistSpec.getResponseSpecCheckOk())
                .extract().jsonPath();

        List<String> playlistIds = new ArrayList<>();

        List<PlaylistResponse> playlists = getAllPlaylistsResponse.getList("items", PlaylistResponse.class);
        for ( PlaylistResponse playlist : playlists) {
            String id = playlist.getId();
            playlistIds.add(id);
        }

        for(String playlistId : playlistIds) {
            given()
                    .spec(playlistSpec.getRemovePlaylistSpec(playlistId))
                    .when()
                    .delete()
                    .then()
                    .spec(playlistSpec.getResponseSpecCheckOk());
        }
    }
}
