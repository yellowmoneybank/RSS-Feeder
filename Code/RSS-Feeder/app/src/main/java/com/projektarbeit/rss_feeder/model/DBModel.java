package com.projektarbeit.rss_feeder.model;


// 02.06.2017 | AE | Klasse erstellt

import android.net.Uri;

import com.projektarbeit.rss_feeder.control.Feed;
import com.projektarbeit.rss_feeder.control.Folder;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;


public class DBModel implements ModelInterface {

    public static  final String LOG_TAG = DBModel.class.getSimpleName();

    private FeedOBJ_DataSource feedDataSource;

    private FolderOBJ_DataSource folderDataSource;

    @Override
    public void saveFeeds(List<Feed> feedList) {

        for (Feed feed : feedList) {
            saveOneFeed(feed);
        }
    }

    @Override
    public List<Feed> loadFeeds() {
        List<FeedOBJ> feedOBJList = feedDataSource.getAllFeedObjs();
        List <Feed> fList = new ArrayList<>();

        for (FeedOBJ fOBJ : feedOBJList) {
            Feed f = new Feed(fOBJ.getTitle(), fOBJ.getDescritpion(), convertStringToUri(fOBJ.getLink()),
                    convertStringToTime(fOBJ.getPublicationDate()), convertStringToTime(fOBJ.getLastBuildDate()),
                    fOBJ.getFeedAsXML(), fOBJ.getDomainName(),fOBJ.getId(), fOBJ.getFolder());

            fList.add(f);
        }

        return fList;

    }

    @Override
    public void saveFolders(ArrayList<Folder> folderList) {

        for (Folder folder :folderList) {

            saveOneFolder(folder);        }
    }

    @Override
    public void loadFolders() {

        List<FolderOBJ> folderOBJList = folderDataSource.getAllFolderOBJs();
        List<Folder> folderList = new ArrayList<>();

        for (FolderOBJ fOBJ = folderOBJList) {

            ArrayList<FeedOBJ> content = (ArrayList<FeedOBJ>) feedDataSource.getAllFeedObjs(fOBJ.getId());
            Folder f = new Folder(fOBJ.getName(), )
        }

    }



    private void saveOneFeed(Feed f){

        feedDataSource.createFeedOBJ(f.getTitle(), f.getDescription(), convertURIToString(f.getLink()), convertTimeToString(f.getPublicationDate()),
                convertTimeToString(f.getReceiveDate()), convertTimeToString(f.getLastBuildTime()), convertBooleanToInt(f.isRead()), f.getFeedAsXML(),
                f.getDomainName(), f.getFolderID());
    }

    private void saveOneFolder(Folder f) {

        folderDataSource.createFolderOBJ(f.getFolderName(), convertTimeToString(f.getLastRequestTime()), convertURIToString(f.getResource()));
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

    private String convertURIToString(Uri u) {

        return u.toString();
    }

    private Uri convertStringToUri(String s) {

        return Uri.parse(s);
    }
}
