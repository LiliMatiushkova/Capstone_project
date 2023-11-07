package org.project.specifications;

import org.project.oauth2.TokenManager;
import java.util.HashMap;
import java.util.Map;

public class Authentication extends BaseSpec{

    private static String apiTokenName = "access_token";
    private static Map<String, String> authQueryParams = new HashMap<>();


    public static Map<String, String> getAuthenticationParameters() {
        if (authQueryParams.isEmpty()) {
            authQueryParams.put(apiTokenName, TokenManager.getToken());
        }
        return authQueryParams;
    }
}
