package org.project.specifications;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.project.holder.PropertyHolder;

import java.util.HashMap;
import java.util.Map;

public class Authentication extends BaseSpec{
    static PropertyHolder prop = new PropertyHolder();
    static String token = prop.readProperty("token");

    private static String apiKeyName = "key";
    private static String key = System.getenv("APIkey");

    private static String apiTokenName = "token";
    private static String token1 = System.getenv("APItoken");

    private static Map<String, String> authQueryParams = new HashMap<>();

    public static Map<String, String> getAuthenticationParameters() {
        if (authQueryParams.isEmpty()) {
            //authQueryParams.put(apiKeyName, key);
            authQueryParams.put(apiTokenName, token);
        }
        return authQueryParams;
    }
}
