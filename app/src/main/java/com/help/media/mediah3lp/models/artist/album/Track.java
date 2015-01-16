package com.help.media.mediah3lp.models.artist.album;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey.bukin on 16.01.2015.
 */
public class Track {

    @SerializedName("name")
    private String name;
    public String getName() {return name;}

    @SerializedName("@attr")
    private Attr mAttr;
    public Attr getImage() {
        return mAttr;
    }

}
