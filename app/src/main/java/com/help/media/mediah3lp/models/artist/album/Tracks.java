package com.help.media.mediah3lp.models.artist.album;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.albums.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey.bukin on 16.01.2015.
 */
public class Tracks extends com.help.media.mediah3lp.models.artist.albums.Album {


    @SerializedName("track")
    private List<Track> mTrack = new ArrayList<>();
    public List<Track> getTrack() {
        return mTrack;
    }
}
