package com.finalproject.footballlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {
    private List<Game> games;
    private Context context;

    public GamesAdapter(List<Game> games, Context context) {
        this.games = games;
        this.context = context;
    }

    // Update the data in the adapter
    public void updateData(List<Game> newGames) {
        games.clear();
        games.addAll(newGames);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = games.get(position);
        holder.dateTextView.setText(game.getDate());
        holder.cityTextView.setText(game.getCity());
        holder.teamsTextView.setText(game.getTeamA() + " vs " + game.getTeamB());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                if (position!= RecyclerView.NO_POSITION) {
                    Game game = games.get(position);
                    DatabaseHelper dbHelper = new DatabaseHelper(context);
                    boolean isDeleted = dbHelper.deleteGame(game.getId());
                    if (isDeleted) {
                        games.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, games.size());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView cityTextView;
        public TextView teamsTextView;
        public Button deleteButton;

        public GameViewHolder(View view) {
            super(view);
            dateTextView = view.findViewById(R.id.textViewDate);
            cityTextView = view.findViewById(R.id.textViewCity);
            teamsTextView = view.findViewById(R.id.textViewTeams);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }
}
