package com.help.media.mediah3lp.models.artist.event;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.events.Location;

/**
 * Created by alexey.bukin on 15.01.2015.
 */
public class Venue {

    @SerializedName("location")
    private Location mLocation;
    public Location getLocation() {return mLocation;}

    private String name;
    public String getName() {return name;}

    @SerializedName("phonenumber")
    private String phonenumber;
    public String getPhonenumber() {return phonenumber;}

    @SerializedName("website")
    private String website;
    public String getWebsite() {return website;}
}
