package org.project;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.hc.core5.http.HttpStatus;
import org.project.dto.*;
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

    private RequestSpecification requestSpecification;

    String clientId = "c9e017d2fd6e4e9f992e7430a3ddd38f";
    String redirectUri = "http://localhost:8888/callback";
    String scopes = "playlist-modify-public playlist-read-private playlist-modify-private";
    String authUrl = "https://accounts.spotify.com/authorize" +
            "?client_id=" + clientId +
            "&response_type=code" +
            "&scope=" + scopes +
            "&redirect_uri=" + redirectUri;

    @Test
    public void getAuthorizationCode() {
        Response response = requestSpecification
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .get(authUrl);
        JsonPath jsonPath = response.jsonPath();
        System.out.println(response.asPrettyString());
        // Extract the "name" field
        String code = jsonPath.get("code");
        System.out.println(code);
    }


   /*


    public String getAuthorizationCode() {
        var getAuthorizationCodeResponse = given()
                .spec(authentication.getAuthorizationCodeSpec())
                .when()
                .get()
                .then()
                .spec(authentication.getResponseSpecCheckOk())
                .extract().body().as(AuthorizationResponse.class);
        return getAuthorizationCodeResponse.getCode();
    }
    public String getAccessToken(String code) {
        var getAccessTokenResponse = given()
                .spec(authentication.getAccessTokenSpec(code))
                .when()
                .post()
                .then()
                .spec(authentication.getResponseSpecCheckOk())
                .extract().body().as(AccessTokenResponse.class);
        return getAccessTokenResponse.getAccess_token();
    }

    String bearerToken = "Bearer " + getAccessToken(getAuthorizationCode());

    private static Map<String, String> headerAuthorizationParams = new HashMap<>();
    public Map<String, String> getHeaderAuthorizationParams() {
        if (headerAuthorizationParams.isEmpty()) {
            headerAuthorizationParams.put("Authorization", bearerToken);
        }
        return headerAuthorizationParams;
    }

    public void setCommonParams(RequestSpecification requestSpecification) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        requestSpecification.headers(headers);
    } */

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
