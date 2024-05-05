package com.finalproject.footballlist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    private RecyclerView searchResultsRecyclerView;
    private GamesAdapter gamesAdapter;
    private SQLiteDatabase database;
    private Spinner searchOptionSpinner;
    private EditText searchTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchOptionSpinner = findViewById(R.id.searchOptionSpinner);
        searchTextInput = findViewById(R.id.searchTextInput);  // Initialize the EditText

        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView);

        gamesAdapter = new GamesAdapter(new ArrayList<Game>(), this);
        searchResultsRecyclerView.setAdapter(gamesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        searchResultsRecyclerView.setLayoutManager(layoutManager);

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }
    public List<Game> fetchSearchResults(String selectedOption, String searchQuery) {
        List<Game> searchResults = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String selection = null;
            String[] selectionArgs = null;

            if ("Search by Date".equals(selectedOption)) {
                selection = "Date LIKE ?";
                selectionArgs = new String[]{"%" + searchQuery + "%"};
            } else if ("Search by Team".equals(selectedOption)) {
                selection = "TeamA LIKE ? OR TeamB LIKE ?";
                selectionArgs = new String[]{"%" + searchQuery + "%", "%" + searchQuery + "%"};
            }

            cursor = db.query("FootballGames", null, selection, selectionArgs, null, null, null);

            if (cursor != null) {
                int idColumnIndex = cursor.getColumnIndex("_id");
                int dateColumnIndex = cursor.getColumnIndex("Date");
                int cityColumnIndex = cursor.getColumnIndex("City");
                int teamAColumnIndex = cursor.getColumnIndex("TeamA");
                int teamBColumnIndex = cursor.getColumnIndex("TeamB");

                while (cursor.moveToNext()) {
                    long id = idColumnIndex != -1 ? cursor.getLong(idColumnIndex) : -1;
                    String date = dateColumnIndex != -1 ? cursor.getString(dateColumnIndex) : null;
                    String city = cityColumnIndex != -1 ? cursor.getString(cityColumnIndex) : null;
                    String teamA = teamAColumnIndex != -1 ? cursor.getString(teamAColumnIndex) : null;
                    String teamB = teamBColumnIndex != -1 ? cursor.getString(teamBColumnIndex) : null;

                    if (id != -1 && date != null && city != null && teamA != null && teamB != null) {
                        Game game = new Game(id, date, city, teamA, teamB);
                        searchResults.add(game);
                    }
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return searchResults;
    }


    private void performSearch() {
        String selectedOption = searchOptionSpinner.getSelectedItem().toString();
        String searchQuery = searchTextInput.getText().toString();

        List<Game> searchResults = fetchSearchResults(selectedOption, searchQuery);

        displaySearchResults(searchResults);
    }

    private void displaySearchResults(List<Game> searchResults) {
        gamesAdapter.updateData(searchResults);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
