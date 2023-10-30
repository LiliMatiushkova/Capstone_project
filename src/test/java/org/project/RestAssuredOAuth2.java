package org.project;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import java.util.Base64;

public class RestAssuredOAuth2 {

    public static String clientId = "c9e017d2fd6e4e9f992e7430a3ddd38f";
    public static String redirectUri = "htpps://oauth.pstmn.io/v1/browser-callback";
    public static String scope = "user-read-private user-read-email";
    public static String username = "lilia_0801@ukr.net";
    public static String password = "Test12345";

    public static String encode(String str1, String str2) {
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }

    public static Response getCode() {
        String authorization = encode(username, password);

        return
                given()
                        .header("authorization", authorization)
                        .contentType(ContentType.URLENC)
                        .formParam("response_type", "code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("scope", scope)
                        .get("/oauth2/authorize")
                        .then()
                       // .statusCode(200)
                        .extract()
                        .response();
    }

    public static String parseForOAuth2Code(Response response) {
        return response.jsonPath().getString("code");
    }


    @BeforeMethod
    public static void setup() {
        RestAssured.baseURI = "https://accounts.spotify.com/authorize?";
    }

    @Test
    public void iShouldGetCode() {
        Response response = getCode();
        System.out.println(response.asString());
       // String code = parseForOAuth2Code(response);

      //  Assert.assertNotNull(code);
    }
}