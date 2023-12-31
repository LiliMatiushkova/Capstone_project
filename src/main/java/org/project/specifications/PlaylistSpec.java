package org.project.specifications;

import io.restassured.specification.RequestSpecification;
import org.project.dto.Playlist;
import org.project.dto.Track;
import org.project.dto.TrackRequest;

public class PlaylistSpec extends BaseSpec {
    protected static final String parameterUsers = "/users/" + userId;
    protected static final String parameterPlaylists = "/playlists/";
    protected static final String parameterTracks = "/tracks";
    protected static final String parameterMe = "/me";
    protected static final String parameterFollowers = "/followers";
    public RequestSpecification getPlaylistCreateSpec(Playlist playlist) {
        return baseRequestBuilder
                .setBasePath(parameterUsers + parameterPlaylists)
                .setBody(playlist)
                .build();
    }
    public RequestSpecification getPlaylistUpdateSpec(String playlistId, Playlist updatedPlaylist) {
        return baseRequestBuilder
                .setBasePath(parameterPlaylists + playlistId)
                .setBody(updatedPlaylist)
                .build();
    }
    public RequestSpecification getPlaylistGetSpec(String playlistId) {
        return baseRequestBuilderWithoutContentType
                .setBasePath(parameterPlaylists + playlistId)
                .build();
    }
    public RequestSpecification getAddItemsToPlaylistSpec(String playlistId, Track track) {
        return baseRequestBuilder
                .setBasePath(parameterPlaylists + playlistId + parameterTracks)
                .setBody(track)
                .build();
    }
    public RequestSpecification removeTrackFromPlaylistSpec(String playlistId, TrackRequest track) {
        return baseRequestBuilder
                .setBasePath(parameterPlaylists + playlistId + parameterTracks)
                .setBody(track)
                .build();
    }
    public RequestSpecification getAllPlaylistSpec() {
        return baseRequestBuilderWithoutContentType
                .setBasePath(parameterMe + parameterPlaylists)
                //.addPathParams("limit", 100)
                //.addPathParams("offset", 0)
                .build();
    }
    public RequestSpecification getRemovePlaylistSpec(String playlistId) {
        return baseRequestBuilderWithoutContentType
                .setBasePath(parameterPlaylists + playlistId + parameterFollowers)
                .build();
    }

}
