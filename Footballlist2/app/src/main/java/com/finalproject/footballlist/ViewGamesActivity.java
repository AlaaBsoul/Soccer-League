package com.finalproject.footballlist;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.finalproject.footballlist.Game;

import java.util.List;

public class ViewGamesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GamesAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_games);

        dbHelper = new DatabaseHelper(this);
        List<Game> games = dbHelper.getAllGames();

        recyclerView = findViewById(R.id.gamesRecyclerView);
        adapter = new GamesAdapter(games, this); // Initialize the adapter with the list of games
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
