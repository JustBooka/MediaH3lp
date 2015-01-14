package com.help.media.mediah3lp.models.artist.events;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.Artist;

/**
 * Created by alexey.bukin on 14.01.2015.
 */
public class EventsResponse {

    @SerializedName("events")
    private Events mEvents = new Events();

    public Events getEvents() {
        return mEvents;
    }
}
