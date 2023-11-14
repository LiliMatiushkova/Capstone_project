package org.project.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    String name;
    String description;
    @JsonProperty("public")
    Boolean Public;
}
