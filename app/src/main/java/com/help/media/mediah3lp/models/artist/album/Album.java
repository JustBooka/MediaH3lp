package com.help.media.mediah3lp.models.artist.album;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.albums.TopAlbums;
import com.help.media.mediah3lp.models.artist.events.Image;
import com.help.media.mediah3lp.models.artist.events.Venue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey.bukin on 16.01.2015.
 */
public class Album {

    @SerializedName("name")
    private String name;
    public String getName() {return name;}

    @SerializedName("artist")
    private String mArtist;
    public String getArtist() {return mArtist;}

    @SerializedName("releasedate")
    private String mReleasedate;
    public String getReleaseDate() {return mReleasedate;}

    @SerializedName("image")
    private List<Image> mImage = new ArrayList<>();
    public List<Image> getImage() {
        return mImage;
    }

    @SerializedName("wiki")
    private Wiki mWiki;
    public Wiki getWiki() {
        return mWiki;
    }

    @SerializedName("tracks")
    private Tracks mTracks;
    public Tracks getTracks() { return mTracks; }


}
