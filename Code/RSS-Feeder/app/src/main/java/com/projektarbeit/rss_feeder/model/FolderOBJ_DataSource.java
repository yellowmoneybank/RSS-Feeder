package com.projektarbeit.rss_feeder.model;

// 23.06.2017 | AE | Klasse erstellt

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FolderOBJ_DataSource {

    private static final String LOG_TAG = FeedOBJ_DataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private FolderOBJ_DBHelper dbHelper;

    private String[] columns = {
            FolderOBJ_DBHelper.COLUMN_ID,
            FolderOBJ_DBHelper.COLUMN_NAME,
            FolderOBJ_DBHelper.COLUMN_LAST_REQUEST_TIME,
            FolderOBJ_DBHelper.COLUMN_RESOURCE
    };

    public void FolderBJ_DataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new FolderOBJ_DBHelper(context);
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

    public FolderOBJ createFolderOBJ(String name, String lastRequestTime, String resource) {

        ContentValues values = new ContentValues();
        values.put(FolderOBJ_DBHelper.COLUMN_NAME, name);
        values.put(FolderOBJ_DBHelper.COLUMN_LAST_REQUEST_TIME, lastRequestTime);
        values.put(FolderOBJ_DBHelper.COLUMN_RESOURCE, resource);

        long insertId = database.insert(FolderOBJ_DBHelper.TABLE_FOLDER, null, values);
        Cursor cursor = database.query(FolderOBJ_DBHelper.TABLE_FOLDER, columns,
                FolderOBJ_DBHelper.COLUMN_ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        FolderOBJ folderOBJ = cursorToFolderOBJ(cursor);
        cursor.close();

        return folderOBJ;
    }

    private FolderOBJ cursorToFolderOBJ(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(FolderOBJ_DBHelper.COLUMN_ID);
        int idName = cursor.getColumnIndex(FolderOBJ_DBHelper.COLUMN_NAME);
        int idLastRequestTime = cursor.getColumnIndex(FolderOBJ_DBHelper.COLUMN_LAST_REQUEST_TIME);
        int idResource = cursor.getColumnIndex(FolderOBJ_DBHelper.COLUMN_RESOURCE);

        int id = cursor.getInt(idIndex);
        String name = cursor.getString(idName);
        String lastRequestTime = cursor.getString(idLastRequestTime);
        String resource = cursor.getString(idResource);

        FolderOBJ folderOBJ = new FolderOBJ(id, name, lastRequestTime, resource);

        return folderOBJ;
    }

    public List<FolderOBJ> getAllFolderOBJs() {

        List<FolderOBJ> folderOBJList = new ArrayList<>();
        Cursor cursor = database.query(FolderOBJ_DBHelper.TABLE_FOLDER, columns, null, null, null, null, null);

        cursor.moveToFirst();
        FolderOBJ folderOBJ;

        while (!cursor.isAfterLast()) {
            folderOBJ = cursorToFolderOBJ(cursor);
            folderOBJList.add(folderOBJ);
            Log.d(LOG_TAG, "ID: " + folderOBJ.getId() + "Inhalt: " + folderOBJ.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return folderOBJList;
    }
}
