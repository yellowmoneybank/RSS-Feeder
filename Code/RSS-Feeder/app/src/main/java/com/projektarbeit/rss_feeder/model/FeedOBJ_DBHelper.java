package com.projektarbeit.rss_feeder.model;

// 01.06.2017 | AE | Klasse erstellt

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FeedOBJ_DBHelper extends SQLiteOpenHelper{


    private static final String LOG_TAG = FeedOBJ_DBHelper.class.getSimpleName();

    public static final String TABLE_FEED = "feeds";

    // Für den Primärschlüssel wir per Konvention der Bezeichner "_id" verwendet --> "_" nicht vergessen!
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE= "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_SHORT_DESCRIPTION = "shortDescription";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_PUBLICATION_DATE= "publicationDate";
    public static final String COLUMN_LAST_BUILD_DATE = "link";
    public static final String COLUMN_RECEIVE_DATE= "receiveDate";
    public static final String COLUMN_IS_READ = "isRead";
    public static final String COLUMN_FEED_AS_XML = "feedAsXML";
    public static final String COLUMN_DOMAIN_NAME = "domainName";
    public static final String COLUMN_FOLDER_ID = "folderId";

    public static final String DB_NAME = "shopping_list.db";
    public static final int DB_VERSION = 1;



    public  static String SQL_CREATE = "CREATE TABLE " + TABLE_FEED +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT NOT NULL, " +
            COLUMN_DESCRIPTION + " TEXT " +
            COLUMN_SHORT_DESCRIPTION + " TEXT" +
            COLUMN_LINK + " TEXT NOT NULL, " +
            COLUMN_PUBLICATION_DATE + " TEXT NOT NULL, " +
            COLUMN_LAST_BUILD_DATE + " TEXT NOT NULL, " +
            COLUMN_RECEIVE_DATE + " TEXT NOT NULL, " +
            COLUMN_IS_READ + " INTEGER, " +
            COLUMN_FEED_AS_XML + " TEXT NOT NULL, " +
            COLUMN_DOMAIN_NAME + " TEXT NOT NULL )";

    public static String SQL_UPDATE_FEED = "Update " + TABLE_FEED + " set " + COLUMN_IS_READ + " = ";

    public static String SQL_WHERE = " Where " + COLUMN_ID + " = ";

    public FeedOBJ_DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");

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
