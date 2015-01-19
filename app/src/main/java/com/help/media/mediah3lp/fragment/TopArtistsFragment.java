package com.help.media.mediah3lp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.help.media.mediah3lp.activity.MenuActivity;
import com.help.media.mediah3lp.R;
import com.help.media.mediah3lp.models.topartist.Artist;
import com.help.media.mediah3lp.models.topartist.TopArtistResponse;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static android.graphics.BitmapFactory.decodeFile;

/**
 * Created by alexey.bukin on 13.01.2015.
 */
public class TopArtistsFragment extends Fragment {

    public ListView mListView;
    public String line;
    private URL link;
    private WeakReference<ParseTask> asyncTaskWeakRef;
    Object object;
    ProgressDialog pd = null;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_top_artists, container, false);

        mListView = (ListView) view.findViewById(R.id.lv_top_artist);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pd = ProgressDialog.show(this.getActivity(), "",
                getString(R.string.loading), true, false);

        try {
            link = new URL("http://ws.audioscrobbler.com/2.0/?method=geo.getmetroartistchart&country=russia&metro=saint%20petersburg&api_key=fd81bf1ff00cb86975d831785b3606a9&format=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setRetainInstance(true);
        startNewAsyncTask();
    }

    private void ItemClick() {
        Artist artist = (Artist) object;
        String name = artist.getName();

        Intent intent = new Intent(getActivity().getApplicationContext(),
                MenuActivity.class);
        intent.putExtra("value", String.valueOf(name));
        startActivity(intent);
    }

    private void startNewAsyncTask() {
        ParseTask asyncTask = new ParseTask(this);
        this.asyncTaskWeakRef = new WeakReference<ParseTask>(asyncTask);
        asyncTask.execute();
    }


    public String start() throws Exception {
        URLConnection connection = link.openConnection();
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));
        if ((line = bufferedReader.readLine()) != null) {
            return line;
        }
        return null;
    }

    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        private WeakReference<TopArtistsFragment> fragmentWeakRef;

        public ParseTask(TopArtistsFragment fragment) {
            this.fragmentWeakRef = new WeakReference<TopArtistsFragment>(fragment);
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
            pd.dismiss();

            if (!TextUtils.isEmpty(strJson)) {
                TopArtistResponse title = new Gson().fromJson(strJson, TopArtistResponse.class);

                mListView.setAdapter(new MyAdapter(title.getTopArtists().getArtist(), getActivity()));

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        object = MyAdapter.getItemList(position);
                        ItemClick();
                    }
                });
            }
        }
    }

    private static class MyAdapter extends BaseAdapter {

        private static List<Artist> mList;
        private Context mContext;

        private MyAdapter(List<Artist> list, Context context) {
            mList = list;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Artist getItem(int position) {
            return mList.get(position);
        }

        public static Artist getItemList(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = View.inflate(mContext, R.layout.item_top_artist, null);
            }

            ViewHolder vh = (ViewHolder) view.getTag();
            if (vh == null) {
                vh = new ViewHolder();
                vh.mTitle = (TextView) view.findViewById(R.id.title_top_artist);
                vh.mImage = (ImageView) view.findViewById(R.id.image_top_artist);

                view.setTag(vh);
            }

            Artist topArtist = getItem(position);
            vh.mTitle.setText(topArtist.getName());

            if (topArtist.getImage().size() >= 3) {
                String imageSrc = topArtist.getImage().get(3).getImgText();
                Picasso.with(mContext).load(imageSrc).into(vh.mImage);


            }

            return view;
        }



        private  class ViewHolder {
            private TextView mTitle;
            private ImageView mImage;
        }
    }

}