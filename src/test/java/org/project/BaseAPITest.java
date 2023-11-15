package org.project;

import io.restassured.path.json.JsonPath;
import org.project.dto.*;
import org.project.specifications.PlaylistSpec;

import java.util.*;

import static io.restassured.RestAssured.given;

public class BaseAPITest extends BaseTest{
    PlaylistSpec playlistSpec = new PlaylistSpec();

    public PlaylistResponse createPlaylistAPI(String name, String description) {
        var expectedPlaylist = Playlist.builder()
                .name(name)
                .description(description)
                .build();
        return given()
                .spec(playlistSpec.getPlaylistCreateSpec(expectedPlaylist))
                .when()
                .post()
                .then()
                .extract().body().as(PlaylistResponse.class);
    }

    public JsonPath updatePlaylistRest(String updatedPlaylistName, String updatedPlaylistDescription, Boolean isPublic, String playlistId) {
        var newPlaylist = new Playlist(updatedPlaylistName, updatedPlaylistDescription, isPublic);
        return given()
                .spec(playlistSpec.getPlaylistUpdateSpec(playlistId, newPlaylist))
                .when()
                .put()
                .then()
                .extract().jsonPath();
    }

    public PlaylistResponse getPlaylistRest(String playlistId) {
        return given()
                .spec(playlistSpec.getPlaylistGetSpec(playlistId))
                .when()
                .get()
                .then()
                .spec(playlistSpec.getResponseSpecCheckGetOk())
                .extract().body().as(PlaylistResponse.class);
    }
    public Track createTrack(String trackUri) {
        Track track = new Track();
        ArrayList<String> uris = new ArrayList<>();
        uris.add(trackUri);
        track.setUris(uris);
        track.setPosition(0);
        return track;
    }
    public TrackRequest trackToDelete(String snapshotId, String trackUri) {

        TrackRequest trackToDelete = new TrackRequest();
        TrackRequest.URI uri = trackToDelete.new URI();
        uri.setUri(trackUri);
        ArrayList<TrackRequest.URI> tracks = new ArrayList<>();
        tracks.add(uri);
        trackToDelete.setTracks(tracks);
        trackToDelete.setSnapshot_id(snapshotId);
        return trackToDelete;
    }
    public TrackRequest addTrackToPlaylistAPI(String playlistId, String trackUri) {
        return given()
                .spec(playlistSpec.getAddItemsToPlaylistSpec(playlistId, createTrack(trackUri)))
                .when()
                .post()
                .then()
                .spec(playlistSpec.getResponseSpecCheckCreated()).log().body()
                .extract().body().as(TrackRequest.class);
    }
}
