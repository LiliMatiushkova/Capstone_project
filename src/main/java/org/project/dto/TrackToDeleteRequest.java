package org.project.dto;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrackToDeleteRequest {
    ArrayList<URI> tracks;
    String snapshot_id;
    public class URI {
        String uri;
        public void setUri(String uri) {
            this.uri = uri;
        }
    }
}
