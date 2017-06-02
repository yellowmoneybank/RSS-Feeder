package com.projektarbeit.rss_feeder.model;


// 02.06.2017 | AE | Klasse erstellt

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.projektarbeit.rss_feeder.control.Feed;

import org.joda.time.LocalDateTime;


public class DBModel implements ModelInterface {

    public static  final String LOG_TAG = DBModel.class.getSimpleName();

    private FeedOBJ_DataSource feedDataSource;



    @Override
    public void save() {

    }

    @Override
    public void load() {

    }

    private void saveFeeds(){

    }

    private void loadFeeds() {

    }

    private void saveFolders(){

    }

    private void loadFolders() {

    }


    private void saveOneFeed(Feed f){
        // Öffnen der Datenquelle
        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        feedDataSource.open();

        // Befüllen des ContenValues-Objektes -->  Dieses repräsentiert einen Datensatz
        ContentValues values = new ContentValues();
        values.put(FeedOBJ_DBHelper.COLUMN_TITLE, f.);
        values.put(FeedOBJ_DBHelper.COLUMN_DESCRIPTION, f.);
        values.put(FeedOBJ_DBHelper.COLUMN_LINK, f.);
        values.put(FeedOBJ_DBHelper.COLUMN_PUBLICATION_DATE, f.);
        values.put(FeedOBJ_DBHelper.COLUMN_LAST_BUILD_DATE, f.);
        values.put(FeedOBJ_DBHelper.COLUMN_RECEIVE_DATE, f.);
        values.put(FeedOBJ_DBHelper.COLUMN_IS_READ, f.);
        values.put(FeedOBJ_DBHelper.COLUMN_FEED_AS_XML, f.);
        values.put(FeedOBJ_DBHelper.COLUMN_DOMAIN_NAME, f.);

        // Einfügen des Datensatzes --> INSERT

        long id  = feedDataSource.getDatabase().insert(FeedOBJ_DBHelper.TABLE_FEED, null, values);

        // Schließen der Datenquelle
        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        feedDataSource.close();
    }

    private String convertTimeToString(LocalDateTime dT) {
        return dT.toString();
    }

    private LocalDateTime convertStringToTime(String dtString) {
        return LocalDateTime.parse(dtString);
    }

    private int convertBooleanToInt(boolean b) {

        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean convertIntToBoolean(int i) {

        switch (i) {
            case 0:
                return false;
            case 1:
                return  true;
            default:
                throw new IllegalArgumentException("Im Feld isRead dürfen nur die Integer-Werte 0 oder 1 vorkommen");
        }
    }
}
