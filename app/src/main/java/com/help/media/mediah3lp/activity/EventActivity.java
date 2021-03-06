package com.help.media.mediah3lp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.help.media.mediah3lp.R;
import com.help.media.mediah3lp.models.artist.event.EventResponse;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by User on 15.01.2015.
 */
public class EventActivity  extends Activity{

    public static final String API_KEY = "fd81bf1ff00cb86975d831785b3606a9";
    ProgressDialog pd = null;
    public String s;
    private URL link;

    private TextView mTitle;
    private ImageView mImage;
    private TextView mCountry;
    private TextView  mCity;
    private TextView mClub;
    private TextView  mDate;
    private TextView  mPhoneNumber;
    private TextView mStreet;
    private TextView mWebSite;


//    private TextView mTitle = (TextView) findViewById(R.id.tv_title);
//    private ImageView mImage = (ImageView) findViewById(R.id.iv_event);
//    private TextView mCountry = (TextView) findViewById(R.id.tv_country);
//    private TextView  mCity = (TextView) findViewById(R.id.tv_city);
//    private TextView mClub = (TextView) findViewById(R.id.tv_name);
//    private TextView  mDate = (TextView) findViewById(R.id.tv_startDate);
//    private TextView  mPhoneNumber = (TextView) findViewById(R.id.tv_phonenumber);
//    private TextView mStreet = (TextView) findViewById(R.id.tv_street);
//    private TextView mWebSite = (TextView) findViewById(R.id.tv_website);







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
//        mListView = (ListView) findViewById(R.id.lv_event);

     mTitle = (TextView) findViewById(R.id.tv_event_title);
     mImage = (ImageView) findViewById(R.id.iv_event_image);
     mCountry = (TextView) findViewById(R.id.tv_event_country);
     mCity = (TextView) findViewById(R.id.tv_event_city);
     mClub = (TextView) findViewById(R.id.tv_event_name);
     mDate = (TextView) findViewById(R.id.tv_event_startDate);
     mPhoneNumber = (TextView) findViewById(R.id.tv_event_phonenumber);
     mStreet = (TextView) findViewById(R.id.tv_event_street);
     mWebSite = (TextView) findViewById(R.id.tv_event_website);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            s = extras.getString("value");
            setTitle(s);

            try {
                link = new URL("http://ws.audioscrobbler.com/2.0/?method=event.getinfo&event=" + URLEncoder.encode(s, "UTF-8") + "&api_key=" + API_KEY + "&format=json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            getActionBar().setTitle(s);
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
                EventResponse title = new Gson().fromJson(strJson, EventResponse.class);




                mTitle.setText(title.getEvent().getTitle());
                mCountry.setText(title.getEvent().getVenue().getLocation().getCountry() + ", ");
                mCity.setText(title.getEvent().getVenue().getLocation().getCity());
                mClub.setText(title.getEvent().getVenue().getName());
                String y=title.getEvent().getStartDate();
                String x=y.replaceAll("\\p{Z}","");
                if (!TextUtils.isEmpty(x)) {
                    mDate.setText(x);
                }else{
                    mDate.setVisibility(View.GONE);
                }
                mPhoneNumber.setText(title.getEvent().getVenue().getPhonenumber());
                mWebSite.setText(title.getEvent().getWebSite());
                mStreet.setText(title.getEvent().getVenue().getLocation().getStreet());

                if (title.getEvent().getImage().size() >= 3) {
                    String imageSrc = title.getEvent().getImage().get(3).getImgText();
                    if (TextUtils.isEmpty(imageSrc)) {
                        Picasso.with(getApplicationContext()).load(R.drawable.nophoto).into(mImage);
                    }else{
                        Picasso.with(getApplicationContext())
                                .load(imageSrc)
                                .placeholder(R.drawable.nophoto)
                                .into(mImage);
                    }
                }
                pd.dismiss();
            }
        }
    }

//    private static class MyAdapter extends BaseAdapter {
//
//        private List<Event> mList;
//        private Context mContext;
//        private MyAdapter(List<Event> list, Context context) {
//            mList = list;
//            mContext = context;
//        }
//
//        @Override
//        public int getCount() {
//            return mList.size();
//        }
//
//        @Override
//        public Event getItem(int position) {
//            return mList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View view, ViewGroup parent) {
//            if (view == null) {
//                view = View.inflate(mContext, R.layout.current_event, null);
//            }
//
//            ViewHolder vh = (ViewHolder) view.getTag();
//            if (vh == null) {
//                vh = new ViewHolder();
//                vh.mTitle = (TextView) view.findViewById(R.id.tv_title);
//                vh.mImage = (ImageView) view.findViewById(R.id.iv_event);
//                vh.mCountry = (TextView) view.findViewById(R.id.tv_country);
//                vh.mCity = (TextView) view.findViewById(R.id.tv_city);
//                vh.mClub = (TextView) view.findViewById(R.id.tv_name);
//                vh.mDate = (TextView) view.findViewById(R.id.tv_startDate);
//                vh.mPhoneNumber = (TextView) view.findViewById(R.id.tv_phonenumber);
////                vh.mWebSite = (TextView) view.findViewById(R.id.tv_website);
//                vh.mStreet = (TextView) view.findViewById(R.id.tv_street);
//
//                view.setTag(vh);
//            }
//
//            Event event = getItem(position);
//            vh.mTitle.setText(event.getTitle());
//            vh.mCountry.setText(event.getVenue().getLocation().getCountry() + ", ");
//            vh.mCity.setText(event.getVenue().getLocation().getCity());
//            vh.mClub.setText(event.getVenue().getName());
//            vh.mDate.setText(event.getStartDate());
//            vh.mPhoneNumber.setText(event.getVenue().getPhonenumber());
////            vh.mWebSite.setText(event.getWebSite());
//            vh.mStreet.setText(event.getVenue().getLocation().getStreet());
//
//            if (event.getImage().size() >= 3) {
//                String imageSrc = event.getImage().get(3).getImgText();
//                Picasso.with(mContext).load(imageSrc).into(vh.mImage);
//            }
//
//            return view;
//        }
//
//        private static class ViewHolder {
//            private TextView mTitle;
//            private ImageView mImage;
//            private TextView mCountry;
//            private TextView mCity;
//            private TextView mClub;
//            private TextView mDate;
//            private TextView mPhoneNumber;
//            private TextView mStreet;
//            private TextView mWebSite;
//
//        }
//    }
}

