package com.projektarbeit.rss_feeder.model;

// 23.06.2017 | AE | Klasse erstellt

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FolderOBJ_DBHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = FolderOBJ_DBHelper.class.getSimpleName();

    public  static final String TABLE_FOLDER = "folder";

    public  static final String COLUMN_ID = "_id";
    public  static final String COLUMN_NAME = "name";
    public  static final String COLUMN_LAST_REQUEST_TIME = "lastRequestTime";
    public  static final String COLUMN_RESOURCE = "resource";

    public static final String DB_NAME = "folder.db";
    public static final int DB_VERSION = 1;

    public static  String  SQL_CREATE = "CREATE TABLE " + TABLE_FOLDER +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_LAST_REQUEST_TIME + " TEXT, " +
            COLUMN_RESOURCE + " TEXT )";

    public static  String SQL_DELETE_FOLDER = "Delete From " + TABLE_FOLDER + " Where COLUMN_ID = ";

    public FolderOBJ_DBHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG,"DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt." );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
