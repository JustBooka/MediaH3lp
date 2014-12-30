package com.help.media.mediah3lp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 30.12.2014.
 */
public class ArtistAlbumsActivity extends Activity {

    private URL link;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_albums_layout);
        mWebView = (WebView) findViewById(R.id.artist_albums);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String s = extras.getString("value");

            try {
                link = new URL("http://www.lastfm.ru/music/" + s + "/+albums?order=date");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        mWebView.loadUrl(String.valueOf(link));
        Toast.makeText(getApplicationContext(), R.string.loading, Toast.LENGTH_LONG).show();
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
    }
}
