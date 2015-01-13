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
 * Created by alexey.bukin on 13.01.2015.
 */
public class CityFestivalsFragment extends Fragment {

    private URL link;
    ProgressDialog pd = null;
    private WeakReference<DownloadWebPageTask> asyncTaskWeakRef;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_city_events, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        s = getArguments().getString("artist");
        try {
            link = new URL("http://www.lastfm.ru/festivals/+place/Russian+Federation/Санкт-Петербург");
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

        private WeakReference<CityFestivalsFragment> fragmentWeakRef;

        public DownloadWebPageTask(CityFestivalsFragment fragment) {
            this.fragmentWeakRef = new WeakReference<CityFestivalsFragment>(fragment);
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

            Pattern p = Pattern.compile("<table id=\"festivalResults\" noattendance=\"1\">(.*?)</table>");
            Matcher m = p.matcher(result);

            if (m.find()) {

                WebView mWebView = (WebView) getView().findViewById(R.id.wv_city_festivals);
                mWebView.loadData(m.group(1), "text/html; charset=UTF-8", null);
                pd.dismiss();
            }

        }
    }
}