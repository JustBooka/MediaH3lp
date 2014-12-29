package com.help.media.mediah3lp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexey.bukin on 29.12.2014.
 */
public class ArtistResponse {

    @SerializedName("artist")
    private Artist mArtist = new Artist();

    public Artist getArtist() {
        return mArtist;
    }
}
