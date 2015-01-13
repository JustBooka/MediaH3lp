package com.help.media.mediah3lp.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
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
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 12.01.2015.
 */
public class ArtistEventsFragment extends Fragment {

public String s;
private URL link;
        ProgressDialog pd = null;
private WeakReference<DownloadWebPageTask> asyncTaskWeakRef;

public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_artist_events, container, false);
        return view;
        }

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s = getArguments().getString("artist");
        try {
        try {
        link = new URL("http://www.lastfm.ru/music/" + URLEncoder.encode(s,"UTF-8") + "/+events");
        } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        }
        } catch (MalformedURLException e) {
        e.printStackTrace();
        }
        setRetainInstance(true);
        startNewAsyncTask();
        }

@Override
public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.pd = ProgressDialog.show(this.getActivity(), "",
        getString(R.string.loading), true, false);
        }

private void startNewAsyncTask() {
        DownloadWebPageTask asyncTask = new DownloadWebPageTask(this);
        this.asyncTaskWeakRef = new WeakReference<DownloadWebPageTask>(asyncTask);
        asyncTask.execute(new String[] {String.valueOf(link)});

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
        if(isDetached()){
            return;
        }

        Pattern p = Pattern.compile("<table class=\"eventsMedium\">(.*?)</table>");
        Matcher m = p.matcher(result);

        if (m.find()) {

            WebView mWebView = (WebView) getView().findViewById(R.id.fr_artist_events);
            mWebView.loadData(m.group(1), "text/html; charset=UTF-8", null);
            pd.dismiss();
        }

    }
}
}