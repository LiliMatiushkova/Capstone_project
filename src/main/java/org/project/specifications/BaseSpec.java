package org.project.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.project.holder.PropertyHolder;

import java.net.HttpURLConnection;

public class BaseSpec {
    static PropertyHolder prop = new PropertyHolder();
    String token = prop.readProperty("token");
    static String userId = prop.readProperty("userId");
    protected static final String BASE_URL = "https://api.spotify.com/v1";

    protected RequestSpecBuilder baseRequestBuilder = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .addQueryParam("access_token", token);

    public ResponseSpecification getResponseSpecCheckCreated() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpURLConnection.HTTP_CREATED)
                .expectContentType(ContentType.JSON)
                .build();
    }
    public ResponseSpecification getResponseSpecCheckOk() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpURLConnection.HTTP_OK)
                //.expectContentType(ContentType.JSON)
                .build();
    }
    public ResponseSpecification getResponseSpecCheckDeleted() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpURLConnection.HTTP_OK)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
