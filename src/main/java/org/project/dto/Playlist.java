package org.project.dto;

import lombok.*;

@Builder
@Setter
@Getter
public class Playlist {
    String name;
    String description;
    Boolean public_;
}
