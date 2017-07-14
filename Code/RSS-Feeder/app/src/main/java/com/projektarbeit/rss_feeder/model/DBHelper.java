package com.projektarbeit.rss_feeder.model;

// 01.06.2017 | AE | Klasse erstellt

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

    private static DBHelper dbHelper;

    private static final String LOG_TAG = DBHelper.class.getSimpleName();

    public static final String TABLE_FEED = "feeds";
    public  static final String TABLE_FOLDER = "folder";

    // Für den Primärschlüssel wir per Konvention der Bezeichner "_id" verwendet --> "_" nicht vergessen!
    public static final String FEED_COLUMN_ID = "id";
    public static final String FEED_COLUMN_TITLE = "title";
    public static final String FEED_COLUMN_DESCRIPTION = "description";
    public static final String FEED_COLUMN_SHORT_DESCRIPTION = "shortDescription";
    public static final String FEED_COLUMN_LINK = "link";
    public static final String FEED_COLUMN_PUBLICATION_DATE = "publicationDate";
    public static final String FEED_COLUMN_LAST_BUILD_DATE = "lastBuildDate";
    public static final String FEED_COLUMN_RECEIVE_DATE = "receiveDate";
    public static final String FEED_COLUMN_IS_READ = "isRead";
    public static final String FEED_COLUMN_FEED_AS_XML = "feedAsXML";
    public static final String FEED_COLUMN_DOMAIN_NAME = "domainName";
    public static final String FEED_COLUMN_FOLDER_ID = "folderId";
    public static final String FEED_COLUMN_DELETED = "deleted";

    public  static final String FOLDER_COLUMN_ID = "id";
    public  static final String FOLDER_COLUMN_NAME = "name";
    public  static final String FOLDER_COLUMN_LAST_REQUEST_TIME = "lastRequestTime";
    public  static final String FOLDER_COLUMN_RESOURCE = "resource";

    public static final String DB_NAME = "folder.db";

    public static final int DB_VERSION = 1;



    public  static String FEED_SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_FEED +
            "(" + FEED_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FEED_COLUMN_TITLE + " TEXT, " +
            FEED_COLUMN_DESCRIPTION + " TEXT, " +
            FEED_COLUMN_SHORT_DESCRIPTION + " TEXT, " +
            FEED_COLUMN_LINK + " TEXT, " +
            FEED_COLUMN_PUBLICATION_DATE + " TEXT, " +
            FEED_COLUMN_LAST_BUILD_DATE + " TEXT, " +
            FEED_COLUMN_RECEIVE_DATE + " TEXT, " +
            FEED_COLUMN_IS_READ + " INTEGER, " +
            FEED_COLUMN_FEED_AS_XML + " TEXT, " +
            FEED_COLUMN_FOLDER_ID + " INTEGER, " +
            FEED_COLUMN_DELETED + " INTEGER, " +
            FEED_COLUMN_DOMAIN_NAME + " TEXT )";

    public static  String FOLDER_SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_FOLDER +
            "(" + FOLDER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FOLDER_COLUMN_NAME + " TEXT, " +
            FOLDER_COLUMN_LAST_REQUEST_TIME + " TEXT, " +
            FOLDER_COLUMN_RESOURCE + " TEXT )";


    public static String SQL_UPDATE_FEED = "Update " + TABLE_FEED + " set " + FEED_COLUMN_IS_READ + " = ";

    public static String SQL_WHERE_FEED = " Where " + FEED_COLUMN_ID + " = ";

    public static String SQL_DELETE_FOLDER = "Delete From " + TABLE_FOLDER + " Where " + FEED_COLUMN_ID + " = ";

    public static String SQL_DELETE_FEEDS_OF_FOLDER = "Delete From " + TABLE_FEED + " Where " + FEED_COLUMN_FOLDER_ID + " = ";

    public static String SQL_DELETE_FEED = "Update " + TABLE_FEED + " set " + FEED_COLUMN_DELETED + " = ";

    public static String SQL_SELECT_FOLDER_ID_BY_NAME = "SELECT * From " + TABLE_FOLDER + " Where " + FOLDER_COLUMN_NAME + " = ?";

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + FEED_SQL_CREATE + " angelegt.");
            db.execSQL(FOLDER_SQL_CREATE);
            db.execSQL(FEED_SQL_CREATE);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static DBHelper getInstance(Context context) {

        if (dbHelper == null) {

            dbHelper = new DBHelper(context);
            return dbHelper;

        } else {

            return  dbHelper;
        }
    }
}
