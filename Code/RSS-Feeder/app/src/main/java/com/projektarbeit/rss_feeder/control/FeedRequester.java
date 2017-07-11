package com.projektarbeit.rss_feeder.control;

import android.os.AsyncTask;

import com.projektarbeit.rss_feeder.util.UrlDateContainer;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;

public class FeedRequester extends AsyncTask<UrlDateContainer, Void, ArrayList<Feed>>{
    private ArrayList<Feed> feedList;
    UrlDateContainer urlDateContainer;

    private ArrayList<Feed> requestFeed(URL url) throws IOException, FeedException {
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

    @Override
    protected ArrayList<Feed> doInBackground(UrlDateContainer... urlDateContainers) {
        urlDateContainer = urlDateContainers[0];
        ArrayList<Feed> feedList = new ArrayList<Feed>();
        if (urlDateContainers[0].getDate() != null){
            try {
                feedList = requestFeed(urlDateContainers[0].getUrl(), urlDateContainers[0].getDate());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                feedList = requestFeed(urlDateContainers[0].getUrl());
            } catch (IOException | FeedException e) {
                e.printStackTrace();
            }
        }
        return feedList;
    }

    @Override
    protected void onPostExecute(ArrayList<Feed> feeds) {
        urlDateContainer.getSelf().newFeedsreceived(feeds);
    }
}
