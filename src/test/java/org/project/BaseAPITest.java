package org.project;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.hc.core5.http.HttpStatus;
import org.project.dto.AccessTokenResponse;
import org.project.dto.AuthorizationResponse;
import org.project.dto.Playlist;
import org.project.specifications.Authentication;
import org.project.specifications.PlaylistSpec;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class BaseAPITest {
    private RequestSpecification requestSpecification;
    public void setCommonParams(RequestSpecification requestSpecification) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        requestSpecification.headers(headers);
    }
    @BeforeMethod
    public void authSetUp() {
        requestSpecification = RestAssured.given();
        setCommonParams(requestSpecification);
    }

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
        // Extract the "name" field
        String code = jsonPath.get("code");
        System.out.println(code);
    }


    /*Authentication authentication = new Authentication();
    PlaylistSpec playlistSpec = new PlaylistSpec();


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

    public Playlist createPlaylist() {
        Random random = new Random();
        Playlist playlist = new Playlist();
        playlist.setName("Playlist" + random.nextInt());
        playlist.setDescription("Description" + random.nextInt());
        playlist.setPublic(Boolean.TRUE);
        return playlist;
    }

   /* public Response createPlaylistAPI() {

    } */
}
