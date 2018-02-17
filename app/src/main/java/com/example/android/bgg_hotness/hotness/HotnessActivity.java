package com.example.android.bgg_hotness.hotness;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bgg_hotness.R;
import com.example.android.bgg_hotness.bean.BoardGame;
import com.example.android.bgg_hotness.utils.XmlDataUtils;
import com.example.android.bgg_hotness.utils.NetworkUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Saiyang Qi on 11/8/17.
 * The main activity of the app;
 * spawn a background thread using loader;
 * fetch board game Hotness ranking from BGG API;
 * display the result in a recyclerView.
 */

public class HotnessActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private static final String HOTNESS_QUERY_URL = "https://www.boardgamegeek.com/xmlapi2/hot?type=boardgame";

    RecyclerView hotnessListRecyclerView;
    ProgressBar loadingProgressBar;
    TextView errorMsgTv;

    private static List<BoardGame> boardGameList;
    static int currentItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotness);

        hotnessListRecyclerView = findViewById(R.id.hotness_list_recycler_view);
        loadingProgressBar = findViewById(R.id.hotness_loading_progress_bar);
        errorMsgTv = findViewById(R.id.hotness_error_msg_tv);

        if (boardGameList != null) {
            loadingProgressBar.setVisibility(View.GONE);
            setUpRecyclerView();
        } else {
            getLoaderManager().initLoader(0, null, this);
        }
    }

    private void setUpRecyclerView() {
        hotnessListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotnessListRecyclerView.setHasFixedSize(true);
        hotnessListRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        hotnessListRecyclerView.setAdapter(new HotnessAdapter(boardGameList));
        hotnessListRecyclerView.scrollToPosition(currentItemPosition);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new BggHotnessXmlLoader(this);
    }

    @Override
    // Upon loading finished, validate the result;
    // if empty or null, display the error; else, setup the recyclerView and display the result
    public void onLoadFinished(Loader<String> loader, String hotnessXmlData) {
        try {
            loadingProgressBar.setVisibility(View.GONE);
            if (hotnessXmlData == null || hotnessXmlData.length() == 0) {
                errorMsgTv.setVisibility(View.VISIBLE);
            }
            else {
                boardGameList = XmlDataUtils.parseHotnessXmlData(hotnessXmlData);
                setUpRecyclerView();
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

    // A loader that loads the hotness rank data on the background thread
    private static class BggHotnessXmlLoader extends AsyncTaskLoader<String> {

        public BggHotnessXmlLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public String loadInBackground() {
            String hotnessXmlData = null;
            try {
                hotnessXmlData = NetworkUtils.fetchData(HOTNESS_QUERY_URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return hotnessXmlData;
        }
    }

}
