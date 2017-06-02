package com.projektarbeit.rss_feeder.model;

// 02.06.2017 | AE | Klasse erstellt

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FeedOBJ_DataSource {

    private static final String LOG_TAG = FeedOBJ_DataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private FeedOBJ_DBHelper dbHelper;

    public FeedOBJ_DataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new FeedOBJ_DBHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + getDatabase().getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
