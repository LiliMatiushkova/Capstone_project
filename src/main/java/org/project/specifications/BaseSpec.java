package org.project.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.project.holder.PropertyHolder;

import java.net.HttpURLConnection;

public class BaseSpec {
    static PropertyHolder prop = new PropertyHolder();
    static String userId = prop.readProperty("userId");
    protected static final String BASE_URL = "https://api.spotify.com/v1";
    protected static final String BASE_ACCOUNT_URL = "https://accounts.spotify.com";

    protected RequestSpecBuilder baseRequestBuilder = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .addQueryParams(Authentication.getAuthenticationParameters());

    public ResponseSpecification getResponseSpecCheckCreated() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpURLConnection.HTTP_CREATED)
                .expectContentType(ContentType.JSON)
                .build();
    }
    public ResponseSpecification getResponseSpecCheckOk() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpURLConnection.HTTP_OK)
                .build();
    }
    public ResponseSpecification getResponseSpecCheckGetOk() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpURLConnection.HTTP_OK)
                .expectContentType(ContentType.JSON)
                .build();
    }
    public ResponseSpecification getResponseSpecCheckDeleted() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpURLConnection.HTTP_OK)
                .expectContentType(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
    public static RequestSpecification getAccountRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_ACCOUNT_URL)
                .setContentType(ContentType.URLENC)
                .build();
    }
}
