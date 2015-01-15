package com.help.media.mediah3lp.models.artist.events;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexey.bukin on 14.01.2015.
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
