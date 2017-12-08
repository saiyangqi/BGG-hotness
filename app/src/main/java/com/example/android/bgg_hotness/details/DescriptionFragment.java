package com.example.android.bgg_hotness.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bgg_hotness.R;

/**
 * Created by Saiyang Qi on 11/11/17.
 * A fragment for the viewPager in {@link GameDetailsActivity};
 * display the description of the given board game item.
 */

public class DescriptionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_details_description, container, false);
        TextView descriptionTv = view.findViewById(R.id.description_tv);
        String description = getArguments().getString("description");
        descriptionTv.setText(description);
        return view;
    }
}
