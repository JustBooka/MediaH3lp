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
import com.help.media.mediah3lp.EventActivity;
import com.help.media.mediah3lp.R;
import com.help.media.mediah3lp.models.artist.events.Event;
import com.help.media.mediah3lp.models.artist.events.EventsResponse;
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
 * Created by alexey.bukin on 13.01.2015.
 */
public class Artist_Events_Fragment extends Fragment {

    private ListView mListView;
    public String line;
    public String s;
    private URL link;
    private WeakReference<ParseTask> asyncTaskWeakRef;
    Object object;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_events, container, false);

        mListView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s = getArguments().getString("artist");
        try {
            try {
                link = new URL("http://ws.audioscrobbler.com/2.0/?method=artist.getevents&artist=" + URLEncoder.encode(s, "UTF-8") + "&api_key=fd81bf1ff00cb86975d831785b3606a9&format=json");
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

    private void ItemClick() {
        Event artist = (Event) object;
        String name = artist.getId();

        Intent intent = new Intent(getActivity().getApplicationContext(),
                EventActivity.class);
        intent.putExtra("value", String.valueOf(name));
        startActivity(intent);
    }

    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        private WeakReference<Artist_Events_Fragment> fragmentWeakRef;

        public ParseTask(Artist_Events_Fragment fragment) {
            this.fragmentWeakRef = new WeakReference<Artist_Events_Fragment>(fragment);
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
                EventsResponse title = new Gson().fromJson(strJson, EventsResponse.class);

                 mListView.setAdapter(new MyAdapter(title.getEvents().getEvent(), getActivity()));

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

        private static List<Event> mList;
        private Context mContext;

        private MyAdapter(List<Event> list, Context context) {
            mList = list;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public  Event getItem(int position) {
            return mList.get(position);
        }

        public static Event getItemList(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = View.inflate(mContext, R.layout.item_event, null);
            }

            ViewHolder vh = (ViewHolder) view.getTag();
            if (vh == null) {
                vh = new ViewHolder();
                vh.mTitle = (TextView) view.findViewById(R.id.title);
                vh.mImage = (ImageView) view.findViewById(R.id.image);
                vh.mCountry = (TextView) view.findViewById(R.id.country);
                vh.mCity = (TextView) view.findViewById(R.id.city);
                vh.mClub = (TextView) view.findViewById(R.id.name);
                vh.mDate = (TextView) view.findViewById(R.id.startDate);

                view.setTag(vh);
            }

            Event event = getItem(position);
            vh.mTitle.setText(event.getTitle());
            vh.mCountry.setText(event.getVenue().getLocation().getCountry() + ", ");
            vh.mCity.setText(event.getVenue().getLocation().getCity());
            vh.mClub.setText(event.getVenue().getName());
            vh.mDate.setText(event.getStartDate());

            if (event.getImage().size() >= 3) {
                String imageSrc = event.getImage().get(3).getImgText();
                Picasso.with(mContext).load(imageSrc).into(vh.mImage);
            } else {
                Picasso.with(mContext).load(R.drawable.nophoto).into(vh.mImage);
            }

            return view;
        }

        private static class ViewHolder {
            private TextView mTitle;
            private ImageView mImage;
            private TextView mCountry;
            private TextView mCity;
            private TextView mClub;
            private TextView mDate;
        }
    }
}