package com.projektarbeit.rss_feeder.model;


// 02.06.2017 | AE | Klasse erstellt

import android.content.ContentValues;
import android.net.Uri;
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

        feedDataSource.createFeedOBJ(f.getTitle(), f.getDescription(), convertURIToSrtring(f.getLink()), convertTimeToString(f.getPublicationDate()),
                convertTimeToString(f.getReceiveDate()), convertTimeToString(f.getLastBuildTime()), convertBooleanToInt(f.isRead()), f.getFeedAsXML(),
                f.getDomainName(), 1);



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

    private String convertURIToSrtring(Uri u) {

        return u.toString();
    }

    private Uri convertStringToUri(String s) {

        return Uri.parse(s);
    }
}
