package org.project;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.hc.core5.http.HttpStatus;
import org.project.dto.*;
import org.project.holder.PropertyHolder;
import org.project.specifications.Authentication;
import org.project.specifications.BaseSpec;
import org.project.specifications.PlaylistSpec;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class BaseAPITest {
    PlaylistSpec playlistSpec = new PlaylistSpec();

    static PropertyHolder prop = new PropertyHolder();
    static String clientId = prop.readProperty("clientId");
    static String clientSecret = prop.readProperty("clientSecret");



    public static String getAccessToken() {
        Response response = RestAssured
                .given()
                .header("Content-type", "application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .post("https://accounts.spotify.com/api/token");
        String access_token = response.jsonPath().get("access_token");
        //System.out.println(access_token);
        return access_token;
    }

    public Playlist createPlaylist(String name, String description) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setPublic(Boolean.FALSE);
        return playlist;
    }
    public JsonPath createPlaylistAPI(String name, String description) {
        Playlist expectedPlaylist = createPlaylist(name, description);
        return given()
                .spec(playlistSpec.getPlaylistCreateSpec(expectedPlaylist))
                .when()
                .post()
                .then()
                .extract().jsonPath();
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
    public TrackToDelete trackToDelete(String snapshotId, String trackUri) {

        TrackToDelete trackToDelete = new TrackToDelete();
        TrackToDelete.URI uri = trackToDelete.new URI();
        uri.setUri(trackUri);
        ArrayList<TrackToDelete.URI> tracks = new ArrayList<>();
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
