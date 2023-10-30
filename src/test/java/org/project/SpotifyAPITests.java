package org.project;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.hc.core5.http.HttpStatus;
import org.project.dto.Playlist;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SpotifyAPITests extends BaseAPITest {
    protected static final String BASE_URL = "https://api.spotify.com/v1/";
    protected static final String userId = "31xqqvchtblksf3v2jevchgppzn4";
    protected static final String parameterPlaylists = "users/" + userId + "/playlists";
    private RequestSpecification requestSpecification;
    public void setCommonParams(RequestSpecification requestSpecification) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        requestSpecification.headers(headers);
    }

    @BeforeMethod
    public void authSetUp() {
        requestSpecification = RestAssured.given().auth().oauth2("BQBzYMpuMuDk4cBJrDwpvYxk0V1V2-OvwgCGNCyemqMyW3aDdVuQkeyFfx240kiY-mZJboR6SKFCIGB8z7ytcUZVnYqfYRS_GqPzVUTEelzWu20zfKNgbiI3ud-BOdDWcf1dBLQtZIJKV2tzGcob9aciBvMxtBZbupUwpTzka2gcwaSytIXMzqTB4tYEq5EkmvA0kgVXfG-h2PewvRMFnILfH1Vw-A3fPLT6rvZOVGh3SZkTK9vb28DnwIlyxssguKLRIq1pbX5LjQ");
        setCommonParams(requestSpecification);
    }

    @Test
    public void createPlaylistTest() {
        Playlist expectedPlaylist = createPlaylist();
        Response response = requestSpecification
                .body(expectedPlaylist)
                .expect()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post(BASE_URL + parameterPlaylists);
        JsonPath jsonPath = response.jsonPath();
        // Extract the "name" field
        String name = jsonPath.get("name");

        Assert.assertEquals(name, expectedPlaylist.getName());
    }
}
