package com.projektarbeit.rss_feeder.util;


import com.projektarbeit.rss_feeder.control.NewFeedsReceived;

import java.net.URL;
import java.util.Date;

public class UrlDateContainer {
    private Date date;
    private URL url;
    private NewFeedsReceived self;

    public UrlDateContainer( URL url, NewFeedsReceived self, Date date) {
        this.date = date;
        this.url = url;
        this.self = self;
    }

    public UrlDateContainer(URL url, NewFeedsReceived self) {
        this.url = url;
        this.self = self;
    }

    public Date getDate() {
        return date;
    }

    public URL getUrl() {
        return url;
    }

    public NewFeedsReceived getSelf() {
        return self;
    }
}
