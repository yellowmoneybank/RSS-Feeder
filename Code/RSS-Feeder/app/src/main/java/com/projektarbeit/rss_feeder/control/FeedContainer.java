package com.projektarbeit.rss_feeder.control;

// 27.06.2017 | AE | Klasse erstellt

import com.projektarbeit.rss_feeder.model.DBModel;

import java.util.ArrayList;
import java.util.List;

public class FeedContainer {

    private List<Folder> allFolders;

    private DBModel dbModel;

    private static FeedContainer feedContainer;

    private FeedContainer(DBModel dbModel) {

        this.dbModel = dbModel;
        if (dbModel != null) {

            allFolders = dbModel.loadFolders();

            for (Folder f : allFolders) {

                f.setDbModel(dbModel);
            }

        }
    }


    public List<Folder> getAllFolders() {
        return allFolders;
    }

    public void setAllFolders(List<Folder> allFolders) {
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
            new RefreshFolderThread(allFolders.get(i)).start();
        }
    }

    public void deleteFolder(int id) {

        if (dbModel != null) {

            dbModel.deleteFolder(id);
            allFolders = dbModel.loadFolders();
        }
    }

    public static FeedContainer getInstance(DBModel dbModel) {

        if (feedContainer == null) {

            feedContainer = new FeedContainer(dbModel);
            return feedContainer;
        } else {

            return feedContainer;
        }
    }

    public Folder getFolderByName(String foldername){
        for (Folder folder :
                allFolders) {
            if (folder.getFolderName().equals(foldername) ){
                return folder;
            }
        }
        throw new IllegalArgumentException("Folder nicht gefunden!");
    }


    private class RefreshFolderThread extends Thread {
        private Folder folder;

        public RefreshFolderThread(Folder folder) {
            this.folder = folder;
        }

        @Override
        public void run() {
            folder.refreshFolder(folder.getLastRequestTime());
        }
    }
}
