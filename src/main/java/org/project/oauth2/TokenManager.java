package org.project.oauth2;

import io.restassured.response.Response;
import org.project.holder.PropertyHolder;

import java.time.Instant;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.project.specifications.BaseSpec.getAccountRequestSpec;
import static org.project.specifications.BaseSpec.getResponseSpec;

public class TokenManager {
    static PropertyHolder prop = new PropertyHolder();
    static String clientId = prop.readProperty("clientId");
    static String clientSecret = prop.readProperty("clientSecret");
    static String refreshToken = prop.readProperty("refreshToken");
    static String grantType = prop.readProperty("grantType");
    protected static final String parameterAPI = "/api";
    protected static final String parameterToken = "/token";
    private static String access_token;
    private static Instant expiry_time;

    public static String getToken(){
        try{
            if(access_token == null || Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing token ...");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 3600);
            } else{
                System.out.println("Token is good to use");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("ABORT!!! Failed to get token");
        }
        return access_token;
    }

    private static Response renewToken(){
        HashMap<String, String> formParams = new HashMap<String, String>();
        formParams.put("client_id", clientId);
        formParams.put("client_secret", clientSecret);
        formParams.put("refresh_token", refreshToken);
        formParams.put("grant_type", grantType);

        Response response = postAccount(formParams);

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT!!! Renew Token failed");
        }
        return response;
    }
    public static Response postAccount(HashMap<String, String> formParams){
        return given(getAccountRequestSpec())
                .formParams(formParams)
                .when()
                .post(parameterAPI + parameterToken)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }
}
