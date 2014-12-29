package com.help.media.mediah3lp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
