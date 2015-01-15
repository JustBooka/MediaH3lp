package com.help.media.mediah3lp.models.artist.events;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexey.bukin on 14.01.2015.
 */
public class Location {

    @SerializedName("city")
    private String city;
    public String getCity() {return city;}

    @SerializedName("country")
    private String country;
    public String getCountry() {return country;}

    @SerializedName("street")
    private String street;
    public String getStreet() {return street;}

}
