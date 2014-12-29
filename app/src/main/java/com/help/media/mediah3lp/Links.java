package com.help.media.mediah3lp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexey.bukin on 29.12.2014.
 */
public class Links {

    @SerializedName("link")
    private Link mLink = new Link();

    public Link getLink() {
        return mLink;
    }
}
