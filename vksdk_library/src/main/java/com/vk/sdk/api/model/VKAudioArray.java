package com.vk.sdk.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexey.bukin on 17.12.2014.
 */
public class VKAudioArray extends VKList<VKApiAudio> {

    @Override
    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiAudio.class);
        return this;
    }
}
