package com.projektarbeit.rss_feeder.model;


// 02.06.2017 | AE | Klasse erstellt

import android.content.Context;
import android.net.Uri;

import com.projektarbeit.rss_feeder.control.Feed;
import com.projektarbeit.rss_feeder.control.Folder;
import com.projektarbeit.rss_feeder.util.DateUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DBModel implements ModelInterface {

    public static  final String LOG_TAG = DBModel.class.getSimpleName();

    private FeedOBJ_DataSource feedDataSource;

    private FolderOBJ_DataSource folderDataSource;

    public DBModel(Context c) {

        feedDataSource = new FeedOBJ_DataSource(c);
        folderDataSource = new FolderOBJ_DataSource(c);
    }

    @Override
    public void saveFeeds(List<Feed> feedList) {

        for (Feed feed : feedList) {
            saveOneFeed(feed);
        }
    }

    @Override
    public List<Feed> loadAllFeeds() {
        return loadFeeds(0);

    }

    @Override
    public List<Feed> loadFeedsForFolder(int id) {
        return loadFeeds(id);
    }

    @Override
    public void saveFolders(ArrayList<Folder> folderList) {

        for (Folder folder :folderList) {

            saveOneFolder(folder);        }
    }

    @Override
    public void saveSingleFolder(Folder folder) {
        saveOneFolder(folder);
    }

    @Override
    public List<Folder> loadFolders() {

        List<FolderOBJ> folderOBJList = folderDataSource.getAllFolderOBJs();
        List<Folder> folderList = new ArrayList<>();

        for (FolderOBJ fOBJ : folderOBJList) {

            ArrayList<FeedOBJ> content = (ArrayList<FeedOBJ>) feedDataSource.getAllFeedObjs(fOBJ.getId());
            Folder f = new Folder(fOBJ.getName(), content, fOBJ.getResource(), convertStringToTime(fOBJ.getLastRequestTime()));
            folderList.add(f);
        }

        return folderList;
    }

    @Override
    public void deleteFolder(int id) {
        folderDataSource.deleteFolder(id);
    }

    // Methode zum Updaten eines Feeds, um den Boolean isRead zu aktualisieren

    @Override
    public void updateFeed(int id, boolean isRead) {

        feedDataSource.updateFeed(id, isRead);
    }

    /*
     Spezial-Methoden
      */


    // Methode um Feeds zu laden

    private List<Feed> loadFeeds(int id) {
        List<FeedOBJ> feedOBJList = feedDataSource.getAllFeedObjs(id);
        List <Feed> fList = new ArrayList<>();

        for (FeedOBJ fOBJ : feedOBJList) {
            Feed f = new Feed(fOBJ.getTitle(), fOBJ.getShortDescription(), fOBJ.getDescritpion(), fOBJ.getLink(),
                    convertStringToTime(fOBJ.getPublicationDate()), convertStringToTime(fOBJ.getLastBuildDate()),
                    fOBJ.getFeedAsXML(), fOBJ.getDomainName(),fOBJ.getId(), fOBJ.getFolder());

            fList.add(f);
        }

        return fList;

    }



    // Methoden zum einzelnen Speichern des jeweiligen Objektes inklusive Konvertierung

    private void saveOneFeed(Feed f){

        feedDataSource.createFeedOBJ(f.getTitle(), f.getShortDescription(), f.getDescription(), f.getUrl(), convertTimeToString(f.getPublicationDate()),
                convertTimeToString(f.getReceiveDate()), convertTimeToString(f.getLastBuildTime()), convertBooleanToInt(f.isRead()), f.getFeedAsXML(),
                f.getDomainName(), f.getFolderID());
    }

    private void saveOneFolder(Folder f) {

        folderDataSource.createFolderOBJ(f.getFolderName(), convertTimeToString(f.getLastRequestTime()), f.getResource());
    }

    // Konverter-Methoden

    private String convertTimeToString(Date date) {

        return DateUtility.convertTimeToString(date);
    }

    private Date convertStringToTime(String dtString) {

        return DateUtility.convertStringToTime(dtString);
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

    private String convertURIToString(Uri u) {

        return u.toString();
    }

    private Uri convertStringToUri(String s) {

        return Uri.parse(s);
    }
}
