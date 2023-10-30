package org.project.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationResponse {
    String code;
    String state;
}
