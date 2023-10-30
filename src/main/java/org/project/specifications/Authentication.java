package org.project.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class Authentication extends BaseSpec{

    private static String apiClientIDName = "client_id";
    private static String clientId = "c9e017d2fd6e4e9f992e7430a3ddd38f";

    private static String apiClientSecretName = "clientSecret";
    private static String clientSecret = "3df6d1cff7f3412bb6c2fe9cf4f31fa4";

    private static String apiResponseTypeName = "response_type";
    private static String responseType = "code";

    private static String apiRedirectURIName = "redirect_uri";
    private static String redirectURI = "htpps://oauth.pstmn.io/v1/browser-callback";

    private static String apiAuthorizationName = "Authorization";
    private static String authorization = "Authorization: Basic " + clientId + ":" + clientSecret;

    private static String apiContentTypeName = "content-type";
    private static String contentType = "application/x-www-form-urlencoded";

    private static String apiGrantTypeName = "grant_type";
    private static String grantType = "authorization_code";

    private static String apiCodeName = "code";

    private static String authoriseURL = "https://accounts.spotify.com/authorize?";
    private static String tokenURL = "https://accounts.spotify.com/api/token";


    private static Map<String, String> authQueryParams = new HashMap<>();
    private static Map<String, String> headersParams = new HashMap<>();
    private static Map<String, String> bodyParams = new HashMap<>();

    public static Map<String, String> getAuthorizationParameters() {
        if (authQueryParams.isEmpty()) {
            authQueryParams.put(apiClientIDName, clientId);
            authQueryParams.put(apiResponseTypeName, responseType);
            authQueryParams.put(apiRedirectURIName, redirectURI);
        }
        return authQueryParams;
    }
    protected RequestSpecBuilder authorizationRequestBuilder = new RequestSpecBuilder()
            .setBaseUri(authoriseURL)
            .setContentType(ContentType.URLENC)
            .addQueryParams(getAuthorizationParameters());
    public static Map<String, String> getHeadersForTokenParameters() {
        if (headersParams.isEmpty()) {
            headersParams.put(apiContentTypeName, contentType);
            headersParams.put(apiAuthorizationName, authorization);
        }
        return headersParams;
    }
    public static Map<String, String> getBodyForTokenParameters(String code) {
        if (bodyParams.isEmpty()) {
            bodyParams.put(apiGrantTypeName, grantType);
            bodyParams.put(apiCodeName, code);
            bodyParams.put(apiRedirectURIName, redirectURI);
        }
        return bodyParams;
    }

    protected RequestSpecBuilder tokenRequestBuilder = new RequestSpecBuilder()
            .addHeaders(getHeadersForTokenParameters())
            .setBaseUri(tokenURL);

    public RequestSpecification getAccessTokenSpec(String code) {
        return tokenRequestBuilder
                .setBody(getBodyForTokenParameters(code))
                .build();

    }

    public RequestSpecification getAuthorizationCodeSpec() {
        return authorizationRequestBuilder
                .build();
    }
}
