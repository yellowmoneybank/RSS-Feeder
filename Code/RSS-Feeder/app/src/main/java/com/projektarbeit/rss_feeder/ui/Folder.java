package com.projektarbeit.rss_feeder.ui;

import com.projektarbeit.rss_feeder.control.Feed;

import java.net.URL;
import java.util.HashMap;

/**
 * Created by HoenigDa on 13.06.2017.
 */

public class Folder {
    private String folderName;
    private HashMap<Integer, Feed> content;
    private URL resource;

    //public Folder(String folderName, HashMap<Integer, Feed> content, URL resource) {
    //    this.folderName = folderName;
    //    this.content = content;
    //    this.resource = resource;
    //}


    public Folder(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
