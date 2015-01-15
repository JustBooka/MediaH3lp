package com.help.media.mediah3lp.models.artist.event;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.events.Events;

/**
 * Created by alexey.bukin on 15.01.2015.
 */
public class EventResponse {

    @SerializedName("event")
    private Event mEvent;
    public Event getEvent() {
        return mEvent;
    }
}
