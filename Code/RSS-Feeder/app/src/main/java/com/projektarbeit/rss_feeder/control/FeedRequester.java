package com.projektarbeit.rss_feeder.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class FeedRequester {
    public static Feed requestFeed(URL url) throws IOException {
        String feedxml = getFeedxml(url);
        return Parser.parse(feedxml);


    }

    private static String getFeedxml(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        String newLine = System.getProperty("line.separator");
        while ((inputLine = in.readLine()) != null)
        {
            response.append(inputLine).append(newLine);
        }

        in.close();

        return response.toString();

    }

    public static Feed requestFeed(URL url, Date lastReqest) {
        // Todo
    }
}
