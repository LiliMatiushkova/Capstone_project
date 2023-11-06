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
public class Track {
    ArrayList<String> uris;
    int position;
}
