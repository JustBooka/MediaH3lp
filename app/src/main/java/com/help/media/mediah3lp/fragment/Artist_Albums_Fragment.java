package com.help.media.mediah3lp.fragment;

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
import com.help.media.mediah3lp.activity.AlbumActivity;
import com.help.media.mediah3lp.R;
import com.help.media.mediah3lp.models.artist.albums.Album;
import com.help.media.mediah3lp.models.artist.albums.AlbumsResponse;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by User on 15.01.2015.
 */
public class Artist_Albums_Fragment extends Fragment {

    private ListView mListView;
    public String line;
    public String s;
    private URL link;
    private WeakReference<ParseTask> asyncTaskWeakRef;
    Object object;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_albums, container, false);
        mListView = (ListView) view.findViewById(R.id.albums_list);
        mListView.setEmptyView(view.findViewById(android.R.id.empty));
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s = getArguments().getString("artist");
        try {
            try {
                link = new URL("http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=" + URLEncoder.encode(s, "UTF-8") + "&api_key=fd81bf1ff00cb86975d831785b3606a9&format=json");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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
        Album a = (Album) object;
        String artist = a.getmArtist().getName();
        String album = a.getName();

        Intent intent = new Intent(getActivity().getApplicationContext(),
                AlbumActivity.class);
        intent.putExtra("artist", String.valueOf(artist));
        intent.putExtra("album", String.valueOf(album));
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

        private WeakReference<Artist_Albums_Fragment> fragmentWeakRef;


        public ParseTask(Artist_Albums_Fragment fragment) {
            this.fragmentWeakRef = new WeakReference<Artist_Albums_Fragment>(fragment);
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
                AlbumsResponse albums = new Gson().fromJson(strJson, AlbumsResponse.class);
                mListView.setAdapter(new MyAdapter(albums.getTopAlbums().getAlbum(), getActivity()));
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

        private static List<Album> mList;
        private Context mContext;

        private MyAdapter(List<Album> list, Context context) {
            mList = list;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Album getItem(int position) {
            return mList.get(position);
        }

        public static Album getItemList(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = View.inflate(mContext, R.layout.item_album, null);
            }

            ViewHolder vh = (ViewHolder) view.getTag();
            if (vh == null) {
                vh = new ViewHolder();
                vh.mTitle = (TextView) view.findViewById(R.id.title_album);
                vh.mImage = (ImageView) view.findViewById(R.id.image_album);
                vh.mArtist = (TextView) view.findViewById(R.id.artist_album);
                view.setTag(vh);
            }

            Album album = getItem(position);
            vh.mTitle.setText(album.getName());
            vh.mArtist.setText(album.getmArtist().getName());

            if (album.getImage().size() >= 3) {
                String imageSrc = album.getImage().get(3).getImgText();
                if (TextUtils.isEmpty(imageSrc)) {
                    Picasso.with(mContext).load(R.drawable.nophoto).into(vh.mImage);
                }else{
                    Picasso.with(mContext)
                            .load(imageSrc)
                            .placeholder(R.drawable.nophoto)
                            .into(vh.mImage);
                }
            }

            return view;
        }

        private static class ViewHolder {
            private TextView mTitle;
            private ImageView mImage;
            private TextView mArtist;
        }
    }


}
