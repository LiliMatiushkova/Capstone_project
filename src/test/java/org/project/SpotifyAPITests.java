package org.project;


import org.project.dto.Playlist;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class SpotifyAPITests extends BaseAPITest {
    String trackUri = "spotify:track:4yIfjMoivhXnY9lZkoVntq";
    String playlistName = "New Playlist";
    String playlistDescription = "New playlist description";
    String updatedPlaylistName = "Updated Playlist";
    String updatedPlaylistDescription = "Updated playlist description";

    @Test
    public void createPlaylistTest() {
        Playlist expectedPlaylist = createPlaylist(playlistName, playlistDescription);
        var createResponse = given()
                .spec(playlistSpec.getPlaylistCreateSpec(expectedPlaylist))
                .when()
                .post()
                .then()
                .spec(playlistSpec.getResponseSpecCheckCreated())
                .extract().jsonPath();

        Assert.assertEquals(createResponse.get("name"), expectedPlaylist.getName());
        //Assert.assertEquals(createResponse.get("description"), expectedPlaylist.getDescription());
        Assert.assertEquals(createResponse.get("public"), expectedPlaylist.getPublic());
    }

    @Test
    public void editPlaylistDetails() {
        String playlistId = createPlaylistAPI(playlistName, playlistDescription).get("id");
        Playlist newPlaylist = updatePlaylist(updatedPlaylistName, updatedPlaylistDescription);
        given()
                .spec(playlistSpec.getPlaylistUpdateSpec(playlistId, newPlaylist))
                .when()
                .put()
                .then()
                .spec(playlistSpec.getResponseSpecCheckOk());
    }

    @Test
    public void addItemsToPlaylist() {
        String playlistId = createPlaylistAPI(playlistName, playlistDescription).get("id");
        var addItemsResponse = given()
                .spec(playlistSpec.getAddItemsToPlaylistSpec(playlistId, createTrack(trackUri))).log().body()
                .when()
                .post()
                .then()
                .spec(playlistSpec.getResponseSpecCheckCreated()).log().body()
                .extract().jsonPath();

        Assert.assertNotNull(addItemsResponse.get("snapshot_id"));
    }

    @Test
    public void removeSongFromPlaylist() {
        String playlistId = createPlaylistAPI(playlistName, playlistDescription).get("id");
        var addTrackResponse = addTrackToPlaylistAPI(playlistId, trackUri);
        String snapshotId = addTrackResponse.get("snapshot_id");
        Assert.assertNotNull(snapshotId);
        var deleteTrackFromPlaylistResponse = given()
                .spec(playlistSpec.removeTrackFromPlaylistSpec(playlistId, trackToDelete(snapshotId, trackUri))).log().body()
                .when()
                .delete()
                .then()
                .spec(playlistSpec.getResponseSpecCheckDeleted())
                .extract().jsonPath();

        Assert.assertNotNull(deleteTrackFromPlaylistResponse.get("snapshot_id"));
    }
}
