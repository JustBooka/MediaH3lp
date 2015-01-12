package com.help.media.mediah3lp.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.help.media.mediah3lp.ArtistResponse;
import com.help.media.mediah3lp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 12.01.2015.
 */
public class ArtistInfoFragment extends Fragment {

    public static final String API_KEY = "fd81bf1ff00cb86975d831785b3606a9";
    private static final String LOG_TAG = "my_log";
    public String s;
    ProgressDialog pd = null;

    private URL link, link2;
//    public WebView mWebView =(WebView) getView().findViewById(R.id.artist_info) ;
    private WeakReference<ParseTask> asyncTaskWeakRef;
    private WeakReference<DownloadWebPageTask> asyncTaskWeakRef2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_artist_info, container, false);
        return view;

    }

    public void parseWebPage() {
        super.onResume();
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{String.valueOf(link2)});
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

//        Bundle args = getArguments();
//        if (args != null) {
//            s = args.getString("artist");
//        }
//        startDownloadWebPageTask();

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String s = extras.getString("value");
//            TextView view = (TextView) findViewById(R.id.artist_name);
//            view.setText(s);
        this.pd = ProgressDialog.show(this.getActivity(), "",
                getString(R.string.loading), true, false);

        s = getArguments().getString("artist");
        try {
            link = new URL("http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + s + "&api_key=" + API_KEY + "&format=json");
            link2 = new URL("http://www.lastfm.ru/music/" + s + "/+wiki");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        setRetainInstance(true);
        startNewAsyncTask();
     super.onCreate(savedInstanceState);
    }

//        new ParseTask().execute();
//        new DownloadWebPageTask().execute();

       private void startNewAsyncTask() {
        ParseTask asyncTask = new ParseTask(this);
        this.asyncTaskWeakRef = new WeakReference<ParseTask>(asyncTask);
        asyncTask.execute();
    }



//    private void startDownloadWebPageTask() {
//        DownloadWebPageTask asyncTask = new DownloadWebPageTask();
//        this.asyncTaskWeakRef2 = new WeakReference<DownloadWebPageTask>(asyncTask);
//        asyncTask.execute();
//    }


    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        private WeakReference<ArtistInfoFragment> fragmentWeakRef;

        public ParseTask(ArtistInfoFragment fragment) {
            this.fragmentWeakRef = new WeakReference<ArtistInfoFragment>(fragment);
        }


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

            if (!TextUtils.isEmpty(strJson)) {
                ArtistResponse info = new Gson().fromJson(strJson, ArtistResponse.class);
//                Toast.makeText(getActivity(), R.string.loading, Toast.LENGTH_LONG).show();
//                mWebView.loadUrl(info.getArtist().getBio().getLinks().getLink().getHref());
//                mWebView.loadUrl(String.valueOf(link2));
                WebView mWebView = (WebView) getView().findViewById(R.id.fr_artist_info);
                mWebView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return true;
                    }
                });

                parseWebPage();
            } else {
                pd.dismiss();
                Toast.makeText(getActivity(), R.string.loading_error, Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        }


    }

    public class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        private WeakReference<ArtistInfoFragment> fragmentWeakRef;

        private DownloadWebPageTask (ArtistInfoFragment fragment) {
            this.fragmentWeakRef = new WeakReference<ArtistInfoFragment>(fragment);
        }

        public DownloadWebPageTask() {

        }


        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s;
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
            Pattern p = Pattern.compile("<div id=\"wiki\">(.*?)</div>");
            Matcher m = p.matcher(result);

            if (m.find()) {
                WebView mWebView = (WebView) getView().findViewById(R.id.fr_artist_info);
                mWebView.loadData(m.group(1), "text/html; charset=UTF-8", null);
                pd.dismiss();
            }

        }
    }
}
