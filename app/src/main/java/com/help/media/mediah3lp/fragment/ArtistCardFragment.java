package com.help.media.mediah3lp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.help.media.mediah3lp.R;

/**
 * Created by alexey.bukin on 26.12.2014.
 */
public class ArtistCardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.artist_info_layout, container, false);
    }
}
