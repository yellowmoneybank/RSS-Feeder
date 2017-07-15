package com.projektarbeit.rss_feeder.model;

// 02.06.2017 | AE | Klasse erstellt

import com.projektarbeit.rss_feeder.control.Feed;
import com.projektarbeit.rss_feeder.control.Folder;

import java.util.ArrayList;

public interface ModelInterface {

    // Alles zum Feed

    void saveFeeds(ArrayList<Feed> feedList);

    ArrayList<Feed> loadAllFeeds();

    ArrayList<Feed> loadFeedsForFolder(int id);

    void updateFeed(int id, boolean isRead);

    // Alles zum Folder

    void saveFolders(ArrayList<Folder> folderArrayList);

    void saveSingleFolder(Folder folder);

    ArrayList<Folder> loadFolders();

    void deleteFolder(int id);

    int getFolderIdByName(String name);
}
