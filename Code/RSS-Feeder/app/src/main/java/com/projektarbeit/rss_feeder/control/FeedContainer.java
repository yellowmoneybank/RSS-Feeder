package com.projektarbeit.rss_feeder.control;

// 27.06.2017 | AE | Klasse erstellt

import android.content.Context;

import com.projektarbeit.rss_feeder.model.DBModel;

import java.util.ArrayList;
import java.util.List;

public class FeedContainer {

    private List<Folder> allFolders;

    private final Context context;

    public FeedContainer(Context c) {

        this.context = c;
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
            new RefreshFolderThread(allFolders.get(i)).run();
        }
    }
    public void deleteFolder(int id) {

        DBModel dbModel = new DBModel(context);

        dbModel.deleteFolder(id);
        allFolders = dbModel.loadFolders();
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
