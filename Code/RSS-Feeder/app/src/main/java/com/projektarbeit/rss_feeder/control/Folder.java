package com.projektarbeit.rss_feeder.control;

// 23.06.2017 | AE | Klasse erstellt

import com.projektarbeit.rss_feeder.model.DBModel;
import com.projektarbeit.rss_feeder.ui.MainActivity;
import com.projektarbeit.rss_feeder.util.UrlDateContainer;
import com.rometools.rome.io.FeedException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Folder implements NewFeedsReceived{

    private String folderName;
    private ArrayList<Feed> content;
    private String resource;
    private Date lastRequestTime;
    private DBModel dbModel = null;
    private int folderID;

    public Folder(String folderName, ArrayList content, String resource, Date lastRequestTime) {

        this.folderName = folderName;
        this.content = content;
        this.resource = resource;
        this.lastRequestTime = lastRequestTime;
    }

    public List<Feed> getUnreadFeeds() {

        List<Feed> unreadFeeds = new ArrayList<Feed>();
        for (Feed f : content) {
            if (!f.isRead()) {
                unreadFeeds.add(f);
            }
        }

        return unreadFeeds;
    }

    public void refreshFolder(Date lastRequest) {

        if(content.isEmpty()){
            refreshFolder();
            return;
        }
        FeedRequester feedRequester  = new FeedRequester();
        try {
            UrlDateContainer urlDateContainer = new UrlDateContainer(new URL(resource), this, lastRequest);
            feedRequester.execute(urlDateContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshFolder() {
        FeedRequester feedRequester  = new FeedRequester();
        try {
            UrlDateContainer urlDateContainer = new UrlDateContainer(new URL(resource), this);
            feedRequester.execute(urlDateContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFeed(Feed feed) {
        content.remove(feed);
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public ArrayList<Feed> getContent() {
        return content;
    }

    public void setContent(ArrayList<Feed> content) {
        this.content = content;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Date getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(Date lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }


    public void setDbModel(DBModel dbModel) {
        this.dbModel = dbModel;
    }


    @Override
    public void newFeedsreceived(ArrayList<Feed> feeds) {
        content = feeds;
        if (dbModel != null) {

            dbModel.saveFeeds(feeds);
            dbModel.loadAllFeeds();
        }

    }

    public void setFolderID() {
        folderID = dbModel.getFolderIdByName(this.getFolderName());
    }
}
