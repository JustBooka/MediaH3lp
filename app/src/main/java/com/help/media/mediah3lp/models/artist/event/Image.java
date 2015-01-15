package com.help.media.mediah3lp.models.artist.event;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexey.bukin on 15.01.2015.
 */
public class Image {

    @SerializedName("#text")
    private String text;

    public String getImgText() {return text;}

    private String size;

    public String getSize() {return size;}
}
