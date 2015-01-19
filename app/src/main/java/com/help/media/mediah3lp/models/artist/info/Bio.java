package com.help.media.mediah3lp.models.artist.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexey.bukin on 29.12.2014.
 */
public class Bio {

    @SerializedName("links")
    private Links mLinks = new Links();

    public Links getLinks() {
        return mLinks;
    }
}
