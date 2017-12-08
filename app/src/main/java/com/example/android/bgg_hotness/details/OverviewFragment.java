package com.example.android.bgg_hotness.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bgg_hotness.R;
import com.example.android.bgg_hotness.bean.BoardGame;
import com.squareup.picasso.Picasso;

/**
 * Created by Saiyang Qi on 11/11/17.
 * A fragment for the viewPager in {@link GameDetailsActivity};
 * display the overview of the given board game item.
 */

public class OverviewFragment extends Fragment {
    private BoardGame boardGame;

    private ImageView gameImageView;
    private TextView gameNameTv;
    private TextView gameYearTv;
    private TextView geekRatingTv;
    private TextView avgRatingTv;
    private TextView playerCountTv;
    private TextView playTimeTv;
    private TextView minAgeTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_details_overview, container, false);
        boardGame = getArguments().getParcelable("boardGame");

        gameImageView = view.findViewById(R.id.overview_game_image_view);
        gameNameTv = view.findViewById(R.id.overview_game_name_tv);
        gameYearTv = view.findViewById(R.id.overview_game_year_tv);
        geekRatingTv = view.findViewById(R.id.overview_geekRating_tv);
        avgRatingTv = view.findViewById(R.id.overview_avgRating_tv);
        playerCountTv = view.findViewById(R.id.overview_playerCount_tv);
        playTimeTv = view.findViewById(R.id.overview_playtime_tv);
        minAgeTv = view.findViewById(R.id.overview_minAge_tv);

        DisplayData(view);
        return view;
    }

    private void DisplayData(View view) {
        Picasso.with(view.getContext())
                .load(boardGame.getImageUrl())
                .fit()
                .centerCrop()
                .into(gameImageView);
        String gameName = boardGame.getGameName() + "";
        gameNameTv.setText(gameName);
        String yearPublished = boardGame.getYearPublished() + "";
        gameYearTv.setText(yearPublished);
        geekRatingTv.setText(getString(R.string.geek_rating, boardGame.getGeekRating()));
        displayAvgRating();
        displayPlayerCount();
        displayPlayTime();
        minAgeTv.setText(getString(R.string.min_age, boardGame.getMinAge()));
    }

    private void displayAvgRating() {
        double avgRating = boardGame.getCommunityAvgRating();
        if (avgRating == 0) {
            avgRatingTv.setText(getString(R.string.avg_rating_null));
        }
        else {
            avgRatingTv.setText(getString(R.string.avg_rating,
                    boardGame.getCommunityAvgRating(), boardGame.getNumOfRaters()));
        }
    }

    private void displayPlayerCount() {
        int minPlayerCount = boardGame.getMinPlayers();
        int maxPlayerCount = boardGame.getMaxPlayers();
        if (minPlayerCount == maxPlayerCount) {
            if (minPlayerCount == 1) {
                playerCountTv.setText(getString(R.string.single_player));
            }
            else {
                playerCountTv.setText(getString(R.string.fixed_player_count, minPlayerCount));
            }
        }
        else {
            playerCountTv.setText(getString(R.string.multiple_player_count, minPlayerCount, maxPlayerCount));
        }
    }

    private void displayPlayTime() {
        int minPlayTime = boardGame.getMinPlayTime();
        int maxPlayTime = boardGame.getMaxPlayTime();
        if (minPlayTime == maxPlayTime) {
            playTimeTv.setText(getString(R.string.fixed_playtime, minPlayTime));
        }
        else {
            playTimeTv.setText(getString(R.string.range_playtime, minPlayTime, maxPlayTime));
        }
    }
}
