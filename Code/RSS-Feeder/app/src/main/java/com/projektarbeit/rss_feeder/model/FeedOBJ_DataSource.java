package com.projektarbeit.rss_feeder.model;

// 02.06.2017 | AE | Klasse erstellt

import android.database.sqlite.SQLiteDatabase;

public class FeedOBJ_DataSource {

    private SQLiteDatabase database;
    private FeedOBJ_DBHelper dbHelper;

    public FeedOBJ_DataSource(Context context) {
        dbHelper = new FeedOBJ_DBHelper(context);
    }
}
