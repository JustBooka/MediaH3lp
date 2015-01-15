package com.help.media.mediah3lp.models.topartist;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.events.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15.01.2015.
 */
public class TopArtists {
    @SerializedName("artist")
    private List<Artist> mArtist = new ArrayList<>();

    public List<Artist> getArtist() {
        return mArtist;
    }
}
