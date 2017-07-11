package com.projektarbeit.rss_feeder.util;

import java.net.URL;
import java.util.Date;

public class UrlDateContainer {
    private Date date;
    private URL url;


    public UrlDateContainer( URL url, Date date) {
        this.date = date;
        this.url = url;
    }

    public UrlDateContainer(URL url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public URL getUrl() {
        return url;
    }

}
