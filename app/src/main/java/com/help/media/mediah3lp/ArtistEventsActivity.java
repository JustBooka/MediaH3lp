package com.help.media.mediah3lp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 30.12.2014.
 */
public class ArtistEventsActivity extends Activity {

    public static final String API_KEY = "fd81bf1ff00cb86975d831785b3606a9";

    private URL link;
    private WebView mWebView;
    ProgressDialog pd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_events_layout);
        mWebView = (WebView) findViewById(R.id.artist_events);
        this.pd = ProgressDialog.show(this, "",
                getString(R.string.loading), true, false);
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
        new DownloadWebPageTask().execute();
//        Toast.makeText(getApplicationContext(), R.string.loading, Toast.LENGTH_LONG).show();
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[] {String.valueOf(link)});
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls){
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Pattern p = Pattern.compile("<table class=\"eventsMedium\">(.*?)</table>");
            Matcher m = p.matcher(result);

            if (m.find()) {

                mWebView.loadData(m.group(1), "text/html; charset=UTF-8", null);
                ArtistEventsActivity.this.pd.dismiss();
            }

        }
    }

}
