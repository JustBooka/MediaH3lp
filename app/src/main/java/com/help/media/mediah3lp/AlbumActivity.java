package com.help.media.mediah3lp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.help.media.mediah3lp.models.artist.album.AlbumResponse;
import com.help.media.mediah3lp.models.artist.album.Track;
import com.help.media.mediah3lp.models.artist.album.Tracks;
import com.help.media.mediah3lp.models.artist.albums.Album;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by alexey.bukin on 16.01.2015.
 */
public class AlbumActivity extends Activity {

    public static final String API_KEY = "fd81bf1ff00cb86975d831785b3606a9";
    ProgressDialog pd = null;
    public String artist;
    public String album;
    private URL link;

    private TextView mName;
    private ImageView mImage;
    private TextView mArtist;
    private TextView mRealeseDate;
    private TextView mSummary;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album);

        mName = (TextView) findViewById(R.id.tv_album_name);
        mImage = (ImageView) findViewById(R.id.iv_album_image);
        mArtist = (TextView) findViewById(R.id.tv_album_artist);
        mRealeseDate = (TextView) findViewById(R.id.tv_album_releacedate);
        mSummary = (TextView)  findViewById(R.id.tv_album_summary);
        mListView = (ListView) findViewById(R.id.lv_album_tracks);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            album = extras.getString("album");
            artist = extras.getString("artist");

            try {
                link = new URL("http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=390fe4197d3e30072d51539eb0258243&artist=" + URLEncoder.encode(artist, "UTF-8") + "&album=" + URLEncoder.encode(album, "UTF-8") + "&format=json");
//                link = new URL("http://ws.audioscrobbler.com/2.0/?method=event.getinfo&event=" + URLEncoder.encode(s, "UTF-8") + "&api_key=" + API_KEY + "&format=json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        this.pd = ProgressDialog.show(this, "",
                getString(R.string.loading), true, false);

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

            if (!TextUtils.isEmpty(strJson)) {
                AlbumResponse album = new Gson().fromJson(strJson, AlbumResponse.class);


                mName.setText(album.getAlbum().getName());
                mArtist.setText(album.getAlbum().getArtist());
                mRealeseDate.setText(album.getAlbum().getReleaseDate());
                mSummary.setText(Html.fromHtml(album.getAlbum().getWiki().getSummary()));

                if (album.getAlbum().getImage().size() >= 3) {
                    String imageSrc = album.getAlbum().getImage().get(3).getImgText();
                    if (TextUtils.isEmpty(imageSrc)) {
                        Picasso.with(getApplicationContext()).load(R.drawable.nophoto).into(mImage);
                    } else {
                        Picasso.with(getApplicationContext()).load(imageSrc).into(mImage);
                    }
                }
                pd.dismiss();

                mListView.setAdapter(new MyAdapter(album.getAlbum().getTracks().getTrack(), getApplicationContext()));
            }

        }
    }


    private static class MyAdapter extends BaseAdapter {

        private static List<Track> mList;
        private Context mContext;

        private MyAdapter(List<Track> list, Context context) {
            mList =  list;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Track getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = View.inflate(mContext, R.layout.album_tracks, null);
            }

            ViewHolder vh = (ViewHolder) view.getTag();
            if (vh == null) {
                vh = new ViewHolder();
                vh.mTrack = (TextView) view.findViewById(R.id.tv_album_track);
                view.setTag(vh);
            }

            Track track = getItem(position);
            vh.mTrack.setText(track.getName());



            return view;
        }

        private static class ViewHolder {
            private TextView mTrack;
        }
    }
}
