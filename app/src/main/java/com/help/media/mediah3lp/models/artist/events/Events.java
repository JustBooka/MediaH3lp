package com.help.media.mediah3lp.models.artist.events;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.Bio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey.bukin on 14.01.2015.
 */
public class Events {

    @SerializedName("event")
    private List<Event> mEvent = new ArrayList<>();

    public List<Event> getEvent() {
        return mEvent;
    }
}
