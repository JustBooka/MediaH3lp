package com.help.media.mediah3lp.models.artist.albums;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15.01.2015.
 */
public class Albums {

    @SerializedName("album")
    private List<Album> mAlbum = new ArrayList<>();

    public List<Album> getAlbum() {
        return mAlbum;
    }
}
