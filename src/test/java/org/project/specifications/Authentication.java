package org.project.specifications;

import java.util.HashMap;
import java.util.Map;

public class Authentication {
    private static String apiKeyName = "key";
    private static String key = System.getenv("APIkey");

    private static String apiTokenName = "token";
    private static String token = System.getenv("APItoken");

    private static String apiClientIDName = "clientId";
    private static String clientId = "c9e017d2fd6e4e9f992e7430a3ddd38f";

    private static String apiClientSecretName = "clientSecret";
    private static String clientSecret = "3df6d1cff7f3412bb6c2fe9cf4f31fa4";

    private static String apiBearerTokenName = "bearerToken";
    private static String bearerToken = "BQCYSHyDu30DOhPlUSoYZNoy0hrXIDLbu7tcK0bvHLXQFjhbw_0ziGjKIWcW0hIak_Jln1Siya8FhUpuYHpdwag7bT-By3FCWVR6T8iESCnVwFfQbFTSAT4E6F9KOxFUk9hNWXcDUgP16RoDla7Zod7L5JTCqhkRq1XoKHjRyRPGCyQR5_laoM7EIYRfdaG0rH-6Ug";

    private static Map<String, String> authQueryParams = new HashMap<>();

    public static Map<String, String> getAuthenticationParameters() {
        if (authQueryParams.isEmpty()) {
           // authQueryParams.put(apiKeyName, key);
          //  authQueryParams.put(apiTokenName, token);
          //  authQueryParams.put(apiClientIDName, clientId);
          //  authQueryParams.put(apiClientSecretName, clientSecret);
            authQueryParams.put(apiBearerTokenName, bearerToken);
        }
        return authQueryParams;
    }
}
