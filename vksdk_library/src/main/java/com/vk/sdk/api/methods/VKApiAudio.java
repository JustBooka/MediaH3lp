package com.vk.sdk.api.methods;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKAudioArray;

/**
 * Created by alexey.bukin on 17.12.2014.
 */
public class VKApiAudio extends VKApiBase {

    public VKRequest get(VKParameters params) {
        return prepareRequest("get", params, VKRequest.HttpMethod.GET, VKAudioArray.class);
    }
}
