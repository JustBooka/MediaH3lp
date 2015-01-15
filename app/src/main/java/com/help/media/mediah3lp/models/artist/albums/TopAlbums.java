package com.help.media.mediah3lp.models.artist.albums;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.events.Events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15.01.2015.
 */
public class TopAlbums extends Events {
    @SerializedName("album")
    private List<Album> mAlbum = new ArrayList<>();

    public List<Album> getAlbum() {
        return mAlbum;
    }
}
