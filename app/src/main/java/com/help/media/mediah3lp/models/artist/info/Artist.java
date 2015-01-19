package com.help.media.mediah3lp.models.artist.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexey.bukin on 29.12.2014.
 */
public class Artist {

    private String name;

    @SerializedName("bio")
    private Bio mBio = new Bio();

    public Bio getBio() {
        return mBio;
    }
}
