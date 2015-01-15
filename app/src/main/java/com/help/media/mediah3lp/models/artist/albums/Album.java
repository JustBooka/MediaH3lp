package com.help.media.mediah3lp.models.artist.albums;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.events.Image;
import com.help.media.mediah3lp.models.artist.events.Venue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15.01.2015.
 */
public class Album {

    private String name;
    public String getName() {return name;}

    @SerializedName("artist")
    private Venue mArtist;
    public Venue getmArtist() {return mArtist;}

    @SerializedName("image")
    private List<Image> mImage = new ArrayList<>();
    public List<Image> getImage() {
        return mImage;
    }

}
