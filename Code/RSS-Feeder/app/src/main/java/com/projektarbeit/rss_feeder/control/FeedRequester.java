package com.projektarbeit.rss_feeder.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

public class FeedRequester {
    public ArrayList<Feed> requestFeed(URL url) throws IOException {
        String feedxml = getFeedxml(url);
        Parser parser = new Parser(feedxml);
        return parser.getItems();


    }

    public ArrayList<Feed> requestFeed(URL url, Date lastReqest) throws IOException {
        ArrayList<Feed> feedList = requestFeed(url);
        for (int i = 0; i < feedList.size(); i++) {
            // TODO wenn das Datum des Feeds älter ist als last Request time ist ,
            // remove() den Feed

        }
        return feedList;
    }
    private String getFeedxml(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        urlConnection.getInputStream()
                )
        );

        String inputLine;
        StringBuilder feedXml = new StringBuilder();
        while ((inputLine = in.readLine()) != null){
            feedXml.append(inputLine);
        }
        return feedXml.toString();
    }


}
