package com.projektarbeit.rss_feeder.model;

// 02.06.2017 | AE | Klasse erstellt

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FeedOBJ_DataSource {

    private static final String LOG_TAG = FeedOBJ_DataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private String[] columns = {
            DBHelper.FEED_COLUMN_ID,
            DBHelper.FEED_COLUMN_TITLE,
            DBHelper.FEED_COLUMN_SHORT_DESCRIPTION,
            DBHelper.FEED_COLUMN_DESCRIPTION,
            DBHelper.FEED_COLUMN_LINK,
            DBHelper.FEED_COLUMN_PUBLICATION_DATE,
            DBHelper.FEED_COLUMN_LAST_BUILD_DATE,
            DBHelper.FEED_COLUMN_RECEIVE_DATE,
            DBHelper.FEED_COLUMN_IS_READ,
            DBHelper.FEED_COLUMN_FEED_AS_XML,
            DBHelper.FEED_COLUMN_DOMAIN_NAME,
            DBHelper.FEED_COLUMN_FOLDER_ID
    };


    public FeedOBJ_DataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = DBHelper.getInstance(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public FeedOBJ createFeedOBJ(String title, String shortDescription, String descritpion, String link, String lastBuildDate, String publicationDate, String receiveDate, int isRead, String feedAsXML, String domainName, int folder) {


        // Befüllen des ContenValues-Objektes -->  Dieses repräsentiert einen Datensatz
        ContentValues values = new ContentValues();
        values.put(DBHelper.FEED_COLUMN_TITLE, title);
        values.put(DBHelper.FEED_COLUMN_SHORT_DESCRIPTION, shortDescription);
        values.put(DBHelper.FEED_COLUMN_DESCRIPTION, descritpion);
        values.put(DBHelper.FEED_COLUMN_LINK, link);
        values.put(DBHelper.FEED_COLUMN_PUBLICATION_DATE, publicationDate);
        values.put(DBHelper.FEED_COLUMN_LAST_BUILD_DATE, lastBuildDate);
        values.put(DBHelper.FEED_COLUMN_RECEIVE_DATE, receiveDate);
        values.put(DBHelper.FEED_COLUMN_IS_READ, isRead);
        values.put(DBHelper.FEED_COLUMN_FEED_AS_XML,feedAsXML);
        values.put(DBHelper.FEED_COLUMN_DOMAIN_NAME,domainName);
        values.put(DBHelper.FEED_COLUMN_FOLDER_ID, folder);

        // Einfügen des Datensatzes --> INSERT
        long insertId  = database.insert(DBHelper.TABLE_FEED, null, values);

        Cursor cursor = database.query(DBHelper.TABLE_FEED,
                columns, DBHelper.FEED_COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        FeedOBJ feedOBJ = cursorToFeedObj(cursor);
        cursor.close();

        return feedOBJ;
    }

    private FeedOBJ cursorToFeedObj(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(DBHelper.FEED_COLUMN_ID);
        int idTitle = cursor.getColumnIndex(DBHelper.FEED_COLUMN_TITLE);
        int idShortDescription = cursor.getColumnIndex(DBHelper.FEED_COLUMN_SHORT_DESCRIPTION);
        int idDescription = cursor.getColumnIndex(DBHelper.FEED_COLUMN_DESCRIPTION);
        int idLink = cursor.getColumnIndex(DBHelper.FEED_COLUMN_LINK);
        int idPublicationDate = cursor.getColumnIndex(DBHelper.FEED_COLUMN_PUBLICATION_DATE);
        int idLastBuildDate = cursor.getColumnIndex(DBHelper.FEED_COLUMN_LAST_BUILD_DATE);
        int idReceiveDate = cursor.getColumnIndex(DBHelper.FEED_COLUMN_RECEIVE_DATE);
        int idIsRead = cursor.getColumnIndex(DBHelper.FEED_COLUMN_IS_READ);
        int idFeedAsXML = cursor.getColumnIndex(DBHelper.FEED_COLUMN_FEED_AS_XML);
        int idDomainName = cursor.getColumnIndex(DBHelper.FEED_COLUMN_DOMAIN_NAME);
        int idFolder = cursor.getColumnIndex(DBHelper.FEED_COLUMN_FOLDER_ID);

        int id = cursor.getInt(idIndex);
        String title = cursor.getString(idTitle);
        String description = cursor.getString(idDescription);
        String shortDescription = cursor.getString(idShortDescription);
        String link = cursor.getString(idLink);
        String publicationDate = cursor.getString(idPublicationDate);
        String lastBuildDate = cursor.getString(idLastBuildDate);
        String receiveDate = cursor.getString(idReceiveDate);
        int isRead = cursor.getInt(idIsRead);
        String feedAsXL = cursor.getString(idFeedAsXML);
        String domainName = cursor.getString(idDomainName);
        int folderId = cursor.getInt(idFolder);

        FeedOBJ feedObj = new FeedOBJ(id,title, shortDescription, description, link, publicationDate, lastBuildDate, receiveDate, isRead, feedAsXL, domainName, folderId);

        return  feedObj;
    }

    public List<FeedOBJ> getAllFeedObjs() {

        return getAllFeedObjs(0);
    }


    public List<FeedOBJ> getAllFeedObjs(int folderID) {

        List<FeedOBJ> feedOBJList = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.TABLE_FEED, columns, null, null, null, null, null);

        cursor.moveToFirst();
        FeedOBJ feedOBJ;

        while (!cursor.isAfterLast()) {
            feedOBJ = cursorToFeedObj(cursor);

            if (folderID == 0 || feedOBJ.getFolder() == folderID) {

                feedOBJList.add(feedOBJ);
            }

            Log.d(LOG_TAG, "ID: "+ feedOBJ.getId() + "Inhalt: " + feedOBJ.toString());
            cursor.moveToNext();
        }
        cursor.close();

        return  feedOBJList;
    }

    public void updateFeed(int id, boolean isRead) {

        database.execSQL(DBHelper.SQL_UPDATE_FEED + isRead + DBHelper.SQL_WHERE_FEED + id);
    }
}
