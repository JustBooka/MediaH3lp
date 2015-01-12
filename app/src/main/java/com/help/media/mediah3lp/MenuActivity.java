package com.help.media.mediah3lp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.help.media.mediah3lp.fragment.ArtistEventsFragment;
import com.help.media.mediah3lp.fragment.ArtistInfoFragment;
import com.help.media.mediah3lp.fragment.FragmentText;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by User on 30.12.2014.
 */

public class MenuActivity extends ActionBarActivity implements MaterialTabListener {

    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;
    public String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        s = extras.getString("value");
        setTitle(s);
        }

        Bundle args = new Bundle();
        args.putString("artist", s);


        Toolbar toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        pager = (ViewPager) this.findViewById(R.id.pager );

        // init view pager
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }

    }


    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[] {
                getString(R.string.btn_info),
                getString(R.string.btn_events),
                getString(R.string.btn_albums)
        };
        Context context;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
//            return new FragmentText();
            switch (position) {

                // Open FragmentTab1.java
                case 0:
                    ArtistInfoFragment fragmentTab1 = new ArtistInfoFragment();
                    return fragmentTab1;

                // Open FragmentTab2.java
                case 1:
                    ArtistEventsFragment fragmentTab2 = new ArtistEventsFragment();
                    return fragmentTab2;

                // Open FragmentTab3.java
                case 2:
                    return new FragmentText();
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }
}

//public class MenuActivity extends Activity implements View.OnClickListener {
//    public static final String API_KEY = "fd81bf1ff00cb86975d831785b3606a9";
//    private static final String LOG_TAG = "my_log";
//    Button btn_info, btn_albums, btn_events;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.menu_activity);
//
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            final String s = extras.getString("value");
//            TextView view = (TextView) findViewById(R.id.artist_name);
//            view.setText(s);
//
//            Button btn_info = (Button) findViewById(R.id.btn_info);
//            Button btn_albums = (Button) findViewById(R.id.btn_albums);
//            Button btn_events = (Button) findViewById(R.id.btn_events);
//
//
//            btn_info.setOnClickListener(this);
//            btn_albums.setOnClickListener(this);
//            btn_events.setOnClickListener(this);
//        }
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_info:
//                Intent intent = new Intent(this, ArtistCardActivity.class);
//                TextView tv = (TextView) findViewById(R.id.artist_name);
//                String s = tv.getText().toString();
//                intent.putExtra("value", String.valueOf(s));
//                startActivity(intent);
//                break;
//            case R.id.btn_events:
//                intent = new Intent(this, ArtistEventsActivity.class);
//                tv = (TextView) findViewById(R.id.artist_name);
//                s = tv.getText().toString();
//                intent.putExtra("value", String.valueOf(s));
//                startActivity(intent);
//                break;
//            case R.id.btn_albums:
//                intent = new Intent(this, ArtistAlbumsActivity.class);
//                tv = (TextView) findViewById(R.id.artist_name);
//                s = tv.getText().toString();
//                intent.putExtra("value", String.valueOf(s));
//                startActivity(intent);
//                break;
//        }
//    }
//}
