package com.help.media.mediah3lp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Just on 12/26/2014.
 */
public class ArtistCardActivity extends Activity {

    public static final String API_KEY = "fd81bf1ff00cb86975d831785b3606a9";
    private static final String LOG_TAG = "my_log";

    private URL link;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_info_layout);
        mWebView = (WebView) findViewById(R.id.artist_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String s = extras.getString("value");
            TextView view = (TextView) findViewById(R.id.artist_name);
            view.setText(getString(R.string.info_about) + " " + s);

            try {
                link = new URL("http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + s + "&api_key=" + API_KEY + "&format=json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        new ParseTask().execute();
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = link;

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            Log.d(LOG_TAG, strJson);

            if (!TextUtils.isEmpty(strJson)) {
                ArtistResponse info = new Gson().fromJson(strJson, ArtistResponse.class);
                Toast.makeText(getApplicationContext(),R.string.loading, Toast.LENGTH_LONG).show();
                mWebView.loadUrl(info.getArtist().getBio().getLinks().getLink().getHref());
                mWebView.setWebViewClient(new WebViewClient(){
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return true;
                    }
                });

            }else {
                Toast.makeText(getApplicationContext(),R.string.loading_error, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }
    }
}
