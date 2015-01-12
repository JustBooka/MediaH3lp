package com.help.media.mediah3lp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.help.media.mediah3lp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 12.01.2015.
 */
public class ArtistEventsFragment extends Fragment {

    public String s;
    private URL link;
    private WeakReference<DownloadWebPageTask> asyncTaskWeakRef;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_events_layout, container, false);
        Bundle args = getArguments();
        if (args != null) {
            s = args.getString("value");
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        new DownloadWebPageTask();
        try {
            link = new URL("http://www.lastfm.ru/music/" + s + "/+events");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), R.string.loading, Toast.LENGTH_LONG).show();
//        mWebView.loadUrl(String.valueOf(link));
//        mWebView.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return true;
//            }
//        });
    }

    private void startNewAsyncTask() {
        DownloadWebPageTask asyncTask = new DownloadWebPageTask(this);
        this.asyncTaskWeakRef = new WeakReference<DownloadWebPageTask>(asyncTask);
        asyncTask.execute();

    }

    @Override
    public void onResume() {
        super.onResume();
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[] {String.valueOf(link)});
    }

    public class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        private WeakReference<ArtistEventsFragment> fragmentWeakRef;

        public DownloadWebPageTask(ArtistEventsFragment fragment) {
            this.fragmentWeakRef = new WeakReference<ArtistEventsFragment>(fragment);
        }

        public DownloadWebPageTask() {

        }

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

                WebView mWebView = (WebView) getView().findViewById(R.id.artist_info);
                mWebView.loadData(m.group(1), "text/html; charset=UTF-8", null);
            }

        }
    }
}