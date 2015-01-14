package com.help.media.mediah3lp.models.artist.events;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alexey.bukin on 14.01.2015.
 */
public class Event {

    private String title;
    public String getTitle() {return title;}

    private String id;
    public String getId() {return id;}


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
