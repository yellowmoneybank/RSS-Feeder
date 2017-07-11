package com.projektarbeit.rss_feeder.control;

import com.projektarbeit.rss_feeder.util.DateUtility;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class Parser {

    private SyndFeed syndFeed;
    private ArrayList<Feed> items;

    public Parser(SyndFeed syndFeed) {
        this.syndFeed = syndFeed;
        try {
            items = extractItems(syndFeed);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    private ArrayList<Feed> extractItems(SyndFeed syndFeed) throws Exception {
        System.out.println(syndFeed.getDocs());
        List<SyndEntry> entries = syndFeed.getEntries();
        ArrayList<Feed> feedArrayList = new ArrayList<>();
        for (SyndEntry entry :
                entries) {
            String title = "";
            if (entry.getTitle() != null) title = entry.getTitle();


            String description = "";
            if (entry.getContents() != null) description = entry.getContents().toString();

            String shortDescription = description;

            String url = "";
            if (entry.getLink() != null) url = entry.getLink();

            Date publicationDate = new Date();

            if(entry.getPublishedDate() != null) publicationDate = entry.getPublishedDate();


            Date lastBuildTime = new Date();
            if(entry.getUpdatedDate() != null) lastBuildTime = entry.getUpdatedDate();


            String domainName = "";
            if(entry.getLink() != null) domainName = new URL(entry.getUri()).getHost();

            String feedAsXML = "";
            if(entry.getSource() != null) feedAsXML = entry.getSource().toString();

            Feed feed = new Feed(title, shortDescription, description, url, publicationDate, lastBuildTime, feedAsXML, domainName);
            feedArrayList.add(feed);
        }
        return feedArrayList;
    }

    public ArrayList<Feed> getItems() {
        return items;
    }
}
