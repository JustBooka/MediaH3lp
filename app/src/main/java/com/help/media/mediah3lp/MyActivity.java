package com.help.media.mediah3lp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKAudioArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;


public class MyActivity extends ListActivity {

    private static final String VK_APP_ID = "4675654";

    //           currentRequest = new com.vk.sdk.api.methods.VKApiAudio().get(new VKParameters());
    private final VKSdkListener sdkListener = new VKSdkListener() {

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
            Log.d("MediaHLPR", "onAcceptUserToken " + token);
            startLoading();
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            Log.d("MediaHLPR", "onReceiveNewToken " + newToken);
            startLoading();
        }

        @Override
        public void onRenewAccessToken(VKAccessToken token) {
            Log.d("MediaHLPR", "onRenewAccessToken " + token);
            startLoading();
        }

        @Override
        public void onCaptchaError(VKError captchaError) {
            Log.d("MediaHLPR", "onCaptchaError " + captchaError);
        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {
            Log.d("MediaHLPR", "onTokenExpired " + expiredToken);
        }

        @Override
        public void onAccessDenied(VKError authorizationError) {
            Log.d("MediaHLPR", "onAccessDenied " + authorizationError);
        }

    };

    private VKRequest currentRequest;
    private Button loginButton;

    private final List<Audio> users = new ArrayList<Audio>();
    private ArrayAdapter<Audio> listAdapter;
    private final HashSet ts = new HashSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        listAdapter = new ArrayAdapter<Audio>(this, android.R.layout.simple_list_item_2, android.R.id.text1, users) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                final Audio audio = getItem(position);

                ((TextView) view.findViewById(android.R.id.text1)).setText(audio.getArtist());

//                ((TextView) view.findViewById(android.R.id.text2)).setText(audio.getGenre());
                return view;

            }
        };
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(listAdapter);

        VKSdk.initialize(sdkListener, VK_APP_ID);

        VKUIHelper.onCreate(this);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.authorize(VKScope.AUDIO);
            }
        });

        if (VKSdk.wakeUpSession()) {
            startLoading();
        } else {
            loginButton.setVisibility(View.VISIBLE);
        }
    }
                @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
        if (currentRequest != null) {
            currentRequest.cancel();
        }
    }

    private void startLoading() {
        loginButton.setVisibility(View.GONE);
        if (currentRequest != null) {
            currentRequest.cancel();
        }
//        currentRequest = new com.vk.sdk.api.methods.VKApiAudio().get(VKParameters.from(VKApiConst.FIELDS, "artist,genre_id"));
        currentRequest  = new VKRequest("audio.get", VKParameters.from(VKApiConst.FIELDS, "id,artist,genre_id"), VKRequest.HttpMethod.GET, VKAudioArray.class);

      //  currentRequest = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "id,first_name,last_name,bdate"));
        currentRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                Log.d("MediaHLPR", "onComplete " + response);

                VKAudioArray usersArray = (VKAudioArray) response.parsedModel;
                users.clear();
                final String[] artists = new String[]{};

                for (VKApiAudio userFull : usersArray) {
                    if (!TextUtils.isEmpty(userFull.artist)) {
                        for (int i = 0; i < artists.length; i++) {
                        }

                    }
                    listAdapter.add(new Audio(userFull.artist));

                }

//                ts.addAll(users);
//                users.clear();
//                users.addAll(ts);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                Log.d("MediaHLPR", "attemptFailed " + request + " " + attemptNumber + " " + totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d("MediaHLPR", "onError: " + error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
                Log.d("MediaHLPR", "onProgress " + progressType + " " + bytesLoaded + " " + bytesTotal);
            }
        });
    }
}