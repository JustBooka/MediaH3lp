package com.help.media.mediah3lp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.help.media.mediah3lp.fragment.ArtisListFragment;
import com.help.media.mediah3lp.fragment.CityEventsFragment;
import com.help.media.mediah3lp.fragment.CityFestivalsFragment;
import com.help.media.mediah3lp.fragment.FragmentText;
import com.help.media.mediah3lp.fragment.TopArtistsFragment;
import com.vk.sdk.VKUIHelper;


public class ArtistActivity extends ActionBarActivity {

    private static final String LOG_TAG = "TAG";
    private ActionBarDrawerToggle toggle;
    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lv_navigation_drawer = (ListView) findViewById(R.id.lv_navigation_drawer);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);

        lv_navigation_drawer.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new String[]{getString(R.string.menu1),
                        getString(R.string.menu2),
//                        getString(R.string.menu3),
                        getString(R.string.menu4)
                }));


        getSupportActionBar().setTitle(getString(R.string.menu1));
        manager = getSupportFragmentManager();
        initArtistListFragment();

        lv_navigation_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
                if (position == 0) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container_resource, new ArtisListFragment());
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    getSupportActionBar().setTitle(getString(R.string.menu1));
                }
                else if (position == 1){
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container_resource, new CityEventsFragment());
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    getSupportActionBar().setTitle(getString(R.string.menu2));
                }
//                else if (position == 2){
//                    transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.container_resource, new CityFestivalsFragment());
//                    transaction.commit();
//                    drawerLayout.closeDrawers();
//                    getSupportActionBar().setTitle(getString(R.string.menu3));
//                }
                else if (position == 2){
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_resource, new TopArtistsFragment());
                transaction.commit();
                drawerLayout.closeDrawers();
                getSupportActionBar().setTitle(getString(R.string.menu4));
            }

            }
        });
    }





    private void initArtistListFragment() {
        transaction = manager.beginTransaction();
        transaction.add(R.id.container_resource, new ArtisListFragment());
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

}