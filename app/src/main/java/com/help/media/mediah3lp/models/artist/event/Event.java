package com.help.media.mediah3lp.models.artist.event;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.events.Image;
import com.help.media.mediah3lp.models.artist.events.Venue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey.bukin on 15.01.2015.
 */
public class Event {

    @SerializedName("title")
    private String title;
    public String getTitle() {return title;}

    @SerializedName("venue")
    private Venue mVenue;
    public Venue getVenue() {return mVenue;}

    private String startDate;
    public String getStartDate() {return startDate;}

    @SerializedName("image")
    private List<Image> mImage = new ArrayList<>();
    public List<Image> getImage() {
        return mImage;
    }

    private String website;
    public String getWebSite() {return website;}

}
