package com.projektarbeit.rss_feeder.util;

import com.projektarbeit.rss_feeder.control.Feed;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class UrlDateContainer {
    private Date date;
    private URL url;
    private  ArrayList<Feed> content;


    public UrlDateContainer(URL url, Date date, ArrayList<Feed> folderContent) {
        this.date = date;
        this.url = url;
        this.content = folderContent;
    }

    public UrlDateContainer(URL url) {
        this.url = url;
    }

    public UrlDateContainer(URL url, ArrayList<Feed> content) {

        this.url = url;
        this.content = content;
    }
    public UrlDateContainer(URL url, Date date) {
        this.date = date;
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public URL getUrl() {
        return url;
    }

    public ArrayList<Feed> getFolderContent() {
        return content;
    }
}
