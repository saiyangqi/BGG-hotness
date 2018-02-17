package com.example.android.bgg_hotness.hotness;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.bgg_hotness.details.GameDetailsActivity;
import com.example.android.bgg_hotness.R;
import com.example.android.bgg_hotness.bean.BoardGame;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Created by Saiyang Qi on 11/10/17.
 * A Recycler Adapter for the recyclerView in {@link HotnessActivity};
 * inflate the single item view and create a viewHolder for each board game item;
 * when binding, display the text info, loads the game thumbnail from the given url using Picasso,
 * and attach a onClickItemListener for each item to trigger individual details page.
 */

public class HotnessAdapter extends RecyclerView.Adapter<HotnessAdapter.ItemViewHolder> {
    private List<BoardGame> boardGameList;
    public static final String GAME_ID_KEY = "gameId";

    public HotnessAdapter(List<BoardGame> boardGameList) {
        this.boardGameList = boardGameList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotnest_list_item,
                parent, false); // Inflate the single item view
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        BoardGame boardGame = boardGameList.get(position);
        String name = boardGame.getGameName();
        final int id = boardGame.getGameId();
        int rank = boardGame.getHotnessRank();
        int year = boardGame.getYearPublished();
        holder.nameTv.setText(name);
        holder.rankTv.setText(String.format(Locale.ENGLISH, "%d", rank));
        holder.yearTv.setText(String.format(Locale.ENGLISH, "%d", year));
        // Download and display the thumbnail
        Picasso.with(holder.listItem.getContext())
                .load(boardGame.getThumbnailUrl())
                .fit()
                .centerInside()
                .into(holder.thumbnailImageView);
        // Open the details page for list item click
        holder.listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HotnessActivity.currentItemPosition = holder.getAdapterPosition();
                Context context = holder.listItem.getContext();
                Intent intent = new Intent(context, GameDetailsActivity.class);
                intent.putExtra(GAME_ID_KEY, id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boardGameList.size();
    }

    // A single board game item viewHolder
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        View listItem;
        TextView rankTv;
        TextView nameTv;
        TextView yearTv;
        ImageView thumbnailImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            listItem = itemView.findViewById(R.id.list_item);
            rankTv = itemView.findViewById(R.id.item_rank_tv);
            nameTv = itemView.findViewById(R.id.item_title_tv);
            yearTv = itemView.findViewById(R.id.item_year_tv);
            thumbnailImageView = itemView.findViewById(R.id.item_thumbnail_image);
        }
    }
}
