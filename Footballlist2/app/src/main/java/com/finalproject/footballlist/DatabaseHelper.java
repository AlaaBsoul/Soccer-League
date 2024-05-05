package com.finalproject.footballlist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FootballDatabase";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FootballGames (_id INTEGER PRIMARY KEY AUTOINCREMENT, Date TEXT, City TEXT, TeamA TEXT, TeamB TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades when the schema changes
    }

    public boolean deleteGame(long gameId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "_id=?";
        String[] selectionArgs = { String.valueOf(gameId) };

        int deletedRows = db.delete("FootballGames", selection, selectionArgs);

        if (deletedRows > 0) {
            // Game deleted successfully
        } else {
            // Failed to delete the game
        }

        db.close();
        return false;
    }

    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        if (db == null) {
            return games;
        }

        Cursor cursor = db.query("FootballGames", null, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex("_id");
                int dateColumnIndex = cursor.getColumnIndex("Date");
                int cityColumnIndex = cursor.getColumnIndex("City");
                int teamAColumnIndex = cursor.getColumnIndex("TeamA");
                int teamBColumnIndex = cursor.getColumnIndex("TeamB");

                do {
                    if (idColumnIndex >= 0 && dateColumnIndex >= 0 && cityColumnIndex >= 0 && teamAColumnIndex >= 0 && teamBColumnIndex >= 0) {
                        long id = cursor.getLong(idColumnIndex);
                        String date = cursor.getString(dateColumnIndex);
                        String city = cursor.getString(cityColumnIndex);
                        String teamA = cursor.getString(teamAColumnIndex);
                        String teamB = cursor.getString(teamBColumnIndex);

                        Game game = new Game(id, date, city, teamA, teamB);
                        games.add(game);
                    } else {
                        // Handle the case where a column index is -1
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            // Handle the case where the cursor is null (no results)
        }

        return games;
    }
}
