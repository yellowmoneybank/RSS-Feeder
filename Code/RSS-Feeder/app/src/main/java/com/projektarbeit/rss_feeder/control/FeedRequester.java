package com.projektarbeit.rss_feeder.control;

import android.os.AsyncTask;

import com.projektarbeit.rss_feeder.util.UrlDateContainer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class FeedRequester extends AsyncTask<UrlDateContainer, Void, ArrayList<Feed>> {
    private ArrayList<Feed> feedList;
    private UrlDateContainer urlDateContainer;

    private ArrayList<Feed> requestFeed(URL url, ArrayList<Feed> content) {
        Parser parser = new Parser(url);
        ArrayList<Feed> feedList = parser.getItems();
        feedList = outsortDoubles(feedList, content);

        return feedList;
    }

    private ArrayList<Feed> outsortDoubles(ArrayList<Feed> requestedFeeds, ArrayList<Feed> content) {

        if (content == null || content.size() == 0) {

            return requestedFeeds;
        }


        ArrayList<Feed> returnList = new ArrayList<>();
        boolean exists;

        for (Feed newFeed : requestedFeeds) {

            exists = false;
            for (Feed existingFeed : content) {

                if (newFeed.getTitle().equals(existingFeed.getTitle())) {

                    exists = true;
                }
            }

            if (!exists) {

                returnList.add(newFeed);
            }
        }

        return returnList;

    }

    public ArrayList<Feed> requestFeed(URL url, Date lastReqest, ArrayList<Feed> content) throws IOException {
        ArrayList<Feed> feedList = requestFeed(url, content);

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
                feedList = requestFeed(urlDateContainers[0].getUrl(), urlDateContainers[0].getDate(), urlDateContainers[0].getFolderContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            feedList = requestFeed(urlDateContainers[0].getUrl(), urlDateContainers[0].getFolderContent());
        }
        return feedList;
    }
}
