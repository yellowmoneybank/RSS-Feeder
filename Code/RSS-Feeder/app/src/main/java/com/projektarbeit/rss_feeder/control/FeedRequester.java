package com.projektarbeit.rss_feeder.control;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

public class FeedRequester {
    public ArrayList<Feed> requestFeed(URL url) throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        Parser parser = new Parser(feed);
        return parser.getItems();
    }

    public ArrayList<Feed> requestFeed(URL url, Date lastReqest) throws IOException {
        ArrayList<Feed> feedList = null;
        try {
            feedList = requestFeed(url);
        } catch (FeedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < feedList.size(); i++) {
            if (feedList.get(i).getPublicationDate().before(lastReqest)) {
                feedList.remove(i);
            }
        }
        return feedList;
    }
}
