package com.help.media.mediah3lp;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

/**
 * Created by Just on 12/26/2014.
 */
public class ArtistCardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_info_layout);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String s = extras.getString("value");
            TextView view = (TextView) findViewById(R.id.artist_name);
            view.setText(s);
        }
    }
}
