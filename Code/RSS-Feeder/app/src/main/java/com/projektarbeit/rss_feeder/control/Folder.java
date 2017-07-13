package com.projektarbeit.rss_feeder.control;

// 23.06.2017 | AE | Klasse erstellt

import com.projektarbeit.rss_feeder.model.DBModel;
import com.projektarbeit.rss_feeder.util.UrlDateContainer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Folder {

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

    public Folder(String folderName, ArrayList content, String resource, Date lastRequestTime, int folderID) {

        this(folderName, content, resource, lastRequestTime);
        this.folderID = folderID;
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
            UrlDateContainer urlDateContainer = new UrlDateContainer(new URL(resource), lastRequest, content);
            ArrayList<Feed> receivedFeedList = null;
            try {
                receivedFeedList = feedRequester.execute(urlDateContainer).get();
                receivedFeedList = addFolderID(receivedFeedList);
                saveReceivedFeeds(receivedFeedList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshFolder() {
        FeedRequester feedRequester  = new FeedRequester();
        try {
            UrlDateContainer urlDateContainer = new UrlDateContainer(new URL(resource), content);
            try {
                ArrayList<Feed> receivedFeedList = feedRequester.execute(urlDateContainer).get();
                receivedFeedList = addFolderID(receivedFeedList);
                saveReceivedFeeds(receivedFeedList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Feed> addFolderID(ArrayList<Feed> initiallyFeedList) {

        ArrayList<Feed> resultFeedList = new ArrayList<Feed>();
        int folderID = dbModel.getFolderIdByName(this.getFolderName());

        if (initiallyFeedList == null) {
            return resultFeedList;
        }

        for (Feed f : initiallyFeedList) {
            if (f != null) {

                f.setFolderID(folderID);
                resultFeedList.add(f);
            }
        }

        return resultFeedList;
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

    public void saveReceivedFeeds(ArrayList<Feed> feeds) {
        ArrayList<Feed> newFeeds = new ArrayList<Feed>();
        for (Feed feed :
                feeds) {
            if (feed.getReceiveDate().after(this.getLastRequestTime())){
                newFeeds.add(feed);
            }
        }
        if (dbModel != null && !newFeeds.isEmpty()) {

            dbModel.saveFeeds(newFeeds);
            content = dbModel.loadFeedsForFolder(dbModel.getFolderIdByName(this.getFolderName()));
        }
    }

    public void setFolderID() {
        folderID = dbModel.getFolderIdByName(this.getFolderName());
    }

    public int getFolderID() {
        return folderID;
    }
}
