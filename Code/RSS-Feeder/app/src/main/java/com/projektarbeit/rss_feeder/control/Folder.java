package com.projektarbeit.rss_feeder.control;

// 23.06.2017 | AE | Klasse erstellt

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Folder {

    private String folderName;
    private ArrayList<Feed> content;
    private String resource;
    private Date lastRequestTime;

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

    }

    public void refreshFolder() {

    }

    public void deleteFeed() {

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
}
