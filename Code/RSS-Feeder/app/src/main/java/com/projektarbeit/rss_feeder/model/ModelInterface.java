package com.projektarbeit.rss_feeder.model;

// 02.06.2017 | AE | Klasse erstellt

import com.projektarbeit.rss_feeder.control.Feed;
import com.projektarbeit.rss_feeder.control.Folder;

import java.util.ArrayList;
import java.util.List;

public interface ModelInterface {

    // Alles zum Feed

    void saveFeeds(List<Feed> feedList);

    List<Feed> loadAllFeeds();

    List<Feed> loadFeedsForFolder(int id);

    public void updateFeed(int id, boolean isRead);

    // Alles zum Folder

    void saveFolders(ArrayList<Folder> folderArrayList);

    void saveSingleFolder(Folder folder);

    List<Folder> loadFolders();
}
