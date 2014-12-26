package com.help.media.mediah3lp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKError;

/**
 * Created by Just on 12/25/2014.
 */
public class WelcomeActivity extends Activity{

    private static final String VK_APP_ID = "4675654";

    //           currentRequest = new com.vk.sdk.api.methods.VKApiAudio().get(new VKParameters());
    private final VKSdkListener sdkListener = new VKSdkListener() {

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
            Log.d("MediaHLPR", "onAcceptUserToken " + token);
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            Log.d("MediaHLPR", "onReceiveNewToken " + newToken);
        }

        @Override
        public void onRenewAccessToken(VKAccessToken token) {
            Log.d("MediaHLPR", "onRenewAccessToken " + token);
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

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity_layout);

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
            Intent intent = new Intent(this, ArtistActivity.class);
            startActivity(intent);
        } else {
            loginButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (VKSdk.wakeUpSession()) {
            Intent intent = new Intent(this, ArtistActivity.class);
            startActivity(intent);
        }else {
            loginButton.setVisibility(View.VISIBLE);
        }
        VKUIHelper.onResume(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
    }
}
