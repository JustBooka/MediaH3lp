package com.help.media.mediah3lp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 30.12.2014.
 */
public class ArtistEventsActivity extends Activity {

    public static final String API_KEY = "fd81bf1ff00cb86975d831785b3606a9";

    private URL link;
    private WebView mWebView;
    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_events_layout);
        mWebView = (WebView) findViewById(R.id.artist_events);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String s = extras.getString("value");
            TextView view = (TextView) findViewById(R.id.artist_name);
            view.setText(s);

            try {
                link = new URL("http://www.lastfm.ru/music/" + s + "/+events");
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
