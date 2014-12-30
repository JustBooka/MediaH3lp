package com.help.media.mediah3lp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Just on 12/26/2014.
 */
public class ArtistCardActivity extends Activity implements View.OnClickListener {

    public static final String API_KEY = "fd81bf1ff00cb86975d831785b3606a9";
    private static final String LOG_TAG = "my_log";

    private URL link, link2;
    private WebView mWebView;
    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_info_layout);
        mWebView = (WebView) findViewById(R.id.artist_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String s = extras.getString("value");
            TextView view = (TextView) findViewById(R.id.artist_name);
            view.setText(s);

            try {
                link = new URL("http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + s + "&api_key=" + API_KEY + "&format=json");
                link2 = new URL("http://www.lastfm.ru/music/" + s + "/+wiki");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        Button btn_albums = (Button) findViewById(R.id.btn_albums);
        Button btn_events = (Button) findViewById(R.id.btn_events);


        btn_albums.setOnClickListener(this);
        btn_events.setOnClickListener(this);

        new ParseTask().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_events:
                Intent intent = new Intent(this, ArtistEventsActivity.class);
                TextView tv = (TextView) findViewById(R.id.artist_name);
                String s = tv.getText().toString();
                intent.putExtra("value", String.valueOf(s));
                startActivity(intent);
                break;
            case R.id.btn_albums:
                intent = new Intent(this, ArtistAlbumsActivity.class);
                tv = (TextView) findViewById(R.id.artist_name);
                s = tv.getText().toString();
                intent.putExtra("value", String.valueOf(s));
                startActivity(intent);
                break;
        }

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
                Toast.makeText(getApplicationContext(), R.string.loading, Toast.LENGTH_LONG).show();
//                mWebView.loadUrl(info.getArtist().getBio().getLinks().getLink().getHref());
                mWebView.loadUrl(String.valueOf(link2));
                mWebView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return true;
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), R.string.loading_error, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }
    }
}
