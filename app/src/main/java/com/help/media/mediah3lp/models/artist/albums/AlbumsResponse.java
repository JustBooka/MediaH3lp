package com.help.media.mediah3lp.models.artist.albums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 15.01.2015.
 */
public class AlbumsResponse {
    @SerializedName("topalbums")
    private TopAlbums mTopAlbums = new TopAlbums();

    public TopAlbums getTopAlbums() {
        return mTopAlbums;
    }
}
