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
        for (int i = 0; i < feedList.size(); i++) {
            for (int j = 0; j < content.size(); j++) {
                if (content.get(j) instanceof Feed){
                    Feed contentFeed = content.get(j);
                    if ((feedList.get(i).getTitle() != null) && (contentFeed.getTitle() != null)){
                        if (feedList.get(i).getTitle().equals(contentFeed.getTitle())) {
                            feedList.remove(i);
                        }
                    }
                }
            }
        }
        return feedList;
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
