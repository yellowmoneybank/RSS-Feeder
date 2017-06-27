package com.projektarbeit.rss_feeder.model;

// 02.06.2017 | AE | Klasse erstellt

import com.projektarbeit.rss_feeder.control.Feed;
import com.projektarbeit.rss_feeder.control.Folder;

import java.util.ArrayList;
import java.util.List;

public interface ModelInterface {

    void saveFeeds(List<Feed> feedList);

    List<Feed> loadFeeds();

    void saveFolders(ArrayList<Folder> folderArrayList);

    List<Folder> loadFolders();

    public void updateFeed(int id, boolean isRead);
}
