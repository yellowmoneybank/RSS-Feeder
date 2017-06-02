package com.projektarbeit.rss_feeder.model;

// 01.06.2017 | AE | Klasse erstellt

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedOBJ_DBHelper extends SQLiteOpenHelper{

    public FeedOBJ_DBHelper(Context context) {
        super(context, "PLaTZHALTER", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
