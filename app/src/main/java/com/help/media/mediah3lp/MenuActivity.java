package com.help.media.mediah3lp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by User on 30.12.2014.
 */
public class MenuActivity extends Activity implements View.OnClickListener {
    public static final String API_KEY = "fd81bf1ff00cb86975d831785b3606a9";
    private static final String LOG_TAG = "my_log";
    Button btn_info, btn_albums, btn_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String s = extras.getString("value");
            TextView view = (TextView) findViewById(R.id.artist_name);
            view.setText(s);

            Button btn_info = (Button) findViewById(R.id.btn_info);
            Button btn_albums = (Button) findViewById(R.id.btn_albums);
            Button btn_events = (Button) findViewById(R.id.btn_events);


            btn_info.setOnClickListener(this);
            btn_albums.setOnClickListener(this);
            btn_events.setOnClickListener(this);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_info:
                Intent intent = new Intent(this, ArtistCardActivity.class);
                TextView tv = (TextView) findViewById(R.id.artist_name);
                String s = tv.getText().toString();
                intent.putExtra("value", String.valueOf(s));
                startActivity(intent);
                break;
            case R.id.btn_events:
                intent = new Intent(this, ArtistEventsActivity.class);
                tv = (TextView) findViewById(R.id.artist_name);
                s = tv.getText().toString();
                intent.putExtra("value", String.valueOf(s));
                startActivity(intent);
                break;
            case R.id.btn_albums:
                intent = new Intent(this, ArtistAlbumsActivity.class);
                tv = (TextView) findViewById(R.id.artist_name);
                s = tv.getText().toString();
                intent.putExtra("value", String.valueOf(s));
                startActivity(intent);
                break;
        }
    }
}
