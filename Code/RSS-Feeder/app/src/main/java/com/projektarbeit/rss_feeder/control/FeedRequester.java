package com.projektarbeit.rss_feeder.control;

import android.os.AsyncTask;

import com.projektarbeit.rss_feeder.util.UrlDateContainer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class FeedRequester extends AsyncTask<UrlDateContainer, Void, ArrayList<Feed>> {
    private ArrayList<Feed> feedList;
    UrlDateContainer urlDateContainer;

    private ArrayList<Feed> requestFeed(URL url) {
        Parser parser = new Parser(url);
        return parser.getItems();
    }

    public ArrayList<Feed> requestFeed(URL url, Date lastReqest) throws IOException {
        ArrayList<Feed> feedList = requestFeed(url);

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
        if (urlDateContainers[0].getDate() != null) {
            try {
                feedList = requestFeed(urlDateContainers[0].getUrl(), urlDateContainers[0].getDate());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            feedList = requestFeed(urlDateContainers[0].getUrl());
        }
        return feedList;
    }
}
