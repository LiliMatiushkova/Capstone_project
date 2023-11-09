package org.project;

import io.restassured.path.json.JsonPath;
import org.project.dto.*;
import org.project.specifications.PlaylistSpec;
import java.util.*;

import static io.restassured.RestAssured.given;

public class BaseAPITest {
    PlaylistSpec playlistSpec = new PlaylistSpec();


    public Playlist createPlaylist(String name, String description) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setPublic(Boolean.FALSE);
        return playlist;
    }
    public PlaylistResponse createPlaylistAPI(String name, String description) {
        Playlist expectedPlaylist = createPlaylist(name, description);
        return given()
                .spec(playlistSpec.getPlaylistCreateSpec(expectedPlaylist))
                .when()
                .post()
                .then()
                .extract().body().as(PlaylistResponse.class);
    }
    public Playlist updatePlaylist(String updatedName, String updatedDescription) {
        Playlist playlist = new Playlist();
        playlist.setName(updatedName);
        playlist.setDescription(updatedDescription);
        playlist.setPublic(Boolean.FALSE);
        return playlist;
    }
    public JsonPath updatePlaylistAPI(String updatedPlaylistName, String updatedPlaylistDescription, String playlistId) {
        Playlist newPlaylist = updatePlaylist(updatedPlaylistName, updatedPlaylistDescription);
        return given()
                .spec(playlistSpec.getPlaylistUpdateSpec(playlistId, newPlaylist))
                .when()
                .put()
                .then()
                .extract().jsonPath();
    }
    public Track createTrack(String trackUri) {
        Track track = new Track();
        ArrayList<String> uris = new ArrayList<>();
        uris.add(trackUri);
        track.setUris(uris);
        track.setPosition(0);
        return track;
    }
    public TrackToDeleteRequest trackToDelete(String snapshotId, String trackUri) {

        TrackToDeleteRequest trackToDelete = new TrackToDeleteRequest();
        TrackToDeleteRequest.URI uri = trackToDelete.new URI();
        uri.setUri(trackUri);
        ArrayList<TrackToDeleteRequest.URI> tracks = new ArrayList<>();
        tracks.add(uri);
        trackToDelete.setTracks(tracks);
        trackToDelete.setSnapshot_id(snapshotId);
        return trackToDelete;
    }
    public JsonPath addTrackToPlaylistAPI(String playlistId, String trackUri) {
        return given()
                .spec(playlistSpec.getAddItemsToPlaylistSpec(playlistId, createTrack(trackUri)))
                .when()
                .post()
                .then()
                .spec(playlistSpec.getResponseSpecCheckCreated()).log().body()
                .extract().jsonPath();
    }
}
