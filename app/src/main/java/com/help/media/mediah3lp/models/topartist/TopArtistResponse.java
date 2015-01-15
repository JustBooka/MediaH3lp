package com.help.media.mediah3lp.models.topartist;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.albums.TopAlbums;

/**
 * Created by User on 15.01.2015.
 */
public class TopArtistResponse {
    @SerializedName("topartists")
    private TopArtists mTopArtists = new TopArtists();

    public TopArtists getTopArtists() {
        return mTopArtists;
    }
}
