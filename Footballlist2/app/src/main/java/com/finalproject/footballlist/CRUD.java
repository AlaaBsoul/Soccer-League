package com.finalproject.footballlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CRUD extends AppCompatActivity {

    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;
    private EditText editTextid;
    private EditText editTextCity;
    private EditText editTextDate;
    private EditText editTextTeamA;
    private EditText editTextTeamB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        editTextid = findViewById(R.id.editTextid);
        editTextCity = findViewById(R.id.editTextCity);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTeamA = findViewById(R.id.editTextTeamA);
        editTextTeamB = findViewById(R.id.editTextTeamB);
        Button addGameButton = findViewById(R.id.addGameButton);

        Button updateGameButton = findViewById(R.id.updateGameButton);
        addGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editTextCity.getText().toString();
                String date = editTextDate.getText().toString();
                String teamA = editTextTeamA.getText().toString();
                String teamB = editTextTeamB.getText().toString();

                ContentValues values = new ContentValues();
                values.put("Date", date);
                values.put("City", city);
                values.put("TeamA", teamA);
                values.put("TeamB", teamB);

                long newRowId = database.insert("FootballGames", null, values);

                if (newRowId != -1) {
                    Toast.makeText(CRUD.this, "com.finalproject.footballlist.Game added successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CRUD.this, "Failed to add the game.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        updateGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the UID entered by the user
                String uid = editTextid.getText().toString();

                // Query the database to check if the game with the given UID exists
                Cursor cursor = database.query("FootballGames", null, "_id = ?", new String[]{uid}, null, null, null);

                if (cursor.moveToFirst()) {
                    // Game with the given UID exists, update its data
                    String city = editTextCity.getText().toString();
                    String date = editTextDate.getText().toString();
                    String teamA = editTextTeamA.getText().toString();
                    String teamB = editTextTeamB.getText().toString();

                    ContentValues values = new ContentValues();
                    values.put("Date", date);
                    values.put("City", city);
                    values.put("TeamA", teamA);
                    values.put("TeamB", teamB);

                    int rowsUpdated = database.update("FootballGames", values, "_id = ?", new String[]{uid});

                    if (rowsUpdated > 0) {
                        Toast.makeText(CRUD.this, "Game updated successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CRUD.this, "Failed to update the game.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CRUD.this, "Game with UID " + uid + " not found.", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }
}
