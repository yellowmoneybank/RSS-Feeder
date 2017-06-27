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
        for (int i = 0; i < allFolders.size(); i++) {
            new RefreshFolderThread(allFolders.get(i)).run();
        }
    }
    public void deleteFolder(Folder folder) {
        allFolders.remove(folder);
    }
    private class RefreshFolderThread extends Thread{
        private Folder folder;

        public RefreshFolderThread(Folder folder){
            this.folder = folder;
        }
        @Override
        public void run() {
            folder.refreshFolder();
        }
    }
}
