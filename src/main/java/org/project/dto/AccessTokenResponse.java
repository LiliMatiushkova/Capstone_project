package org.project.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponse {
    String access_token;
    String token_type;
    String scope;
    int expires_in;
    String refresh_token;
}
