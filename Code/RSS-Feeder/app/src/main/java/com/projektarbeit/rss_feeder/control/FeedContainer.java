package com.projektarbeit.rss_feeder.control;

// 27.06.2017 | AE | Klasse erstellt

import java.util.ArrayList;

public class FeedContainer {

    private ArrayList<Folder> allFolders;

    public ArrayList<Folder> getAllFolders() {
        return allFolders;
    }

    public void setAllFolders(ArrayList<Folder> allFolders) {
        this.allFolders = allFolders;
    }

    public ArrayList<Feed> getUnreadFeeds() {

        ArrayList<Feed> unreadFeedList = new ArrayList<Feed>();

        for (Folder folder : allFolders) {

            unreadFeedList.addAll(folder.getUnreadFeeds());
        }

        return unreadFeedList;
    }

    public void refreshAllFolders() {

    }

    public void deleteFolder() {

    }
}
