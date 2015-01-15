package com.help.media.mediah3lp.models.topartist;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.events.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15.01.2015.
 */
public class Artist {

    @SerializedName("name")
    private String name;
    public String getName() {return name;}

    @SerializedName("image")
    private List<Image> mImage = new ArrayList<>();
    public List<Image> getImage() {
        return mImage;
    }
}
