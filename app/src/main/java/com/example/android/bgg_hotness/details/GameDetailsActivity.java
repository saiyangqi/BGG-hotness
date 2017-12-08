package com.example.android.bgg_hotness.details;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bgg_hotness.R;
import com.example.android.bgg_hotness.bean.BoardGame;
import com.example.android.bgg_hotness.utils.NetworkUtils;
import com.example.android.bgg_hotness.utils.XmlDataUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Saiyang Qi on 11/11/17.
 * An activity that displays the details of a single board game;
 * only get triggered by {@link com.example.android.bgg_hotness.hotness.HotnessActivity} on item clicks;
 * get the game id from the intent, and use a loader and the id to fetch the game details;
 * Use a tabLayout, a view pager, and two fragments {@link OverviewFragment} {@link DescriptionFragment}
 * to display the overview and the description of the game.
 */

public class GameDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    public static final String GAME_DETAILS_QUERY_BASE_URL = "https://www.boardgamegeek.com/xmlapi2/thing?";
    public static final String GAME_ID_KEY = "gameId";

    private SectionsPagerAdapter sectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView errorMsgTv;
    private ProgressBar progressBar;

    private int gameId;
    private BoardGame boardGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);
        errorMsgTv = findViewById(R.id.details_error_msg_tv);
        progressBar = findViewById(R.id.details_loading_progress_bar);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        gameId = getIntent().getIntExtra(GAME_ID_KEY, 0);

        getLoaderManager().initLoader(1, null, this);
    }

    // Called when the loading is finished;
    // attaches the adapter and the listeners to the viewpager and the tabLayout
    private void setUpViewPager() {
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new BoardGameDetailsLoader(this, gameId);
    }

    @Override
    // Upon loading finished, validate the result;
    // if null or empty, display the error message;
    // else, parse the xml data and set up the view pager
    public void onLoadFinished(Loader<String> loader, String gameDetailsXmlData) {
        try {
            progressBar.setVisibility(View.GONE);
            if (gameDetailsXmlData == null || gameDetailsXmlData.length() == 0) {
                errorMsgTv.setVisibility(View.VISIBLE);
            }
            else {
                boardGame = XmlDataUtils.parseSingleBoardGameData(gameDetailsXmlData);
                setUpViewPager();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    // A loader that loads the board game details in background with the given id
    private static class BoardGameDetailsLoader extends AsyncTaskLoader<String> {
        int gameId;

        public BoardGameDetailsLoader(Context context, int gameId) {
            super(context);
            this.gameId = gameId;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public String loadInBackground() {
            if (gameId == 0)
                return null;
            String queryUrl = GAME_DETAILS_QUERY_BASE_URL + "id=" + gameId + "&stats=1";
            String gameDetailsXmlData = null;
            try {
                gameDetailsXmlData = NetworkUtils.fetchData(queryUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gameDetailsXmlData;
        }
    }

    // A view page adapter that constructs the fragment according to the given id
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page
            Bundle bundle;
            switch (position) {
                case 0:
                    Fragment overviewFragment = new OverviewFragment();
                    bundle = prepareOverViewBundle();
                    overviewFragment.setArguments(bundle);
                    return overviewFragment;
                case 1:
                    Fragment descriptionFragment = new DescriptionFragment();
                    bundle = prepareDescriptionBundle();
                    descriptionFragment.setArguments(bundle);
                    return descriptionFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        // Prepare the bundle to be passed to the overview fragment
        private Bundle prepareOverViewBundle() {
            Bundle bundle = new Bundle();
            bundle.putParcelable("boardGame", boardGame);
            return bundle;
        }

        // Prepare the bundle to be passed to the description fragment
        private Bundle prepareDescriptionBundle() {
            Bundle bundle = new Bundle();
            bundle.putString("description", boardGame.getDescription());
            return bundle;
        }
    }
}
