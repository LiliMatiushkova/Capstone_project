package org.project.specifications;

import io.restassured.specification.RequestSpecification;
import org.project.dto.Playlist;

public class PlaylistSpec extends BaseSpec {
    protected static final String userId = "31xqqvchtblksf3v2jevchgppzn4";
    protected static final String parameterPlaylists = "users/" + userId + "/playlists";
    public RequestSpecification getPlaylistCreateSpec(Playlist playlist) {
        return baseRequestBuilder
                .setBasePath(parameterPlaylists)
                .setBody(playlist)
                .build();
    }
}
