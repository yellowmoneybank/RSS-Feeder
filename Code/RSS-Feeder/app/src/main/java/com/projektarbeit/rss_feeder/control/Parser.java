package com.projektarbeit.rss_feeder.control;

import android.util.Log;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

class Parser {

    private Document syndFeed;
    private ArrayList<Feed> items;

    public Parser(URL url) {
        try {
            items = new ArrayList<>();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url.toURI().toString());

            items = extractItems(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Feed> extractItems(Document xmlDom) {
        ArrayList<Feed> feedArrayList = new ArrayList<>();
        NodeList items = xmlDom.getElementsByTagName("item");
        if (items.getLength() == 0) {
            items = xmlDom.getElementsByTagName("entry");
        }

        feedArrayList = buildFeeds(items);
        /*
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
        */
        return feedArrayList;
    }

    private ArrayList<Feed> buildFeeds(NodeList items) {
        ArrayList<Feed> feedList = new ArrayList<>();
        for (int i = 0; i < items.getLength(); i++) {
            if (items.item(i) instanceof Element) {
                Element node = (Element) items.item(i);
                String title = "";
                String shortDescription = "";
                String description = "";
                String url = "";
                Date publicationDate = new Date(1337);
                Date lastBuildTime = new Date();
                String feedAsXML = "";
                String domainName = "";

                NodeList nodeTitle = node.getElementsByTagName("title");
                if (nodeTitle.getLength() > 0) {
                    title = nodeTitle.item(0).getTextContent();
                }

                NodeList nodeDescription = node.getElementsByTagName("description");
                if (nodeDescription.getLength() > 0) {
                    description = nodeDescription.item(0).getTextContent();
                } else {
                    nodeDescription = node.getElementsByTagName("summary");
                    if (nodeTitle.getLength() > 0) {
                        description = nodeDescription.item(0).getTextContent();
                    }
                }

                NodeList nodeLink = node.getElementsByTagName("link");
                if (nodeLink.getLength() > 0) {
                    url = nodeLink.item(0).getTextContent();
                    domainName = getDomainName(url);
                    if (url.isEmpty()) {
                        url = nodeLink.item(0).getAttributes().getNamedItem("href").getNodeValue();
                        domainName = getDomainName(url);
                    }
                }

                NodeList nodePublished = node.getElementsByTagName("published");
                if (nodePublished.getLength() > 0) {
                    publicationDate = new Date(nodePublished.item(0).getTextContent());
                } else {
                    nodePublished = node.getElementsByTagName("pubDate");
                    if (nodePublished.getLength() > 0) {
                        publicationDate = new Date(nodePublished.item(0).getTextContent());
                    }
                }

                NodeList nodeBuildTime = node.getElementsByTagName("updated");
                if (nodeBuildTime.getLength() > 0) {
                    lastBuildTime = new Date(nodeBuildTime.item(0).getTextContent());
                }

                feedAsXML = node.toString();

                title = html2text(title);
                shortDescription = html2text(shortDescription);
                description = html2text(description);

                Feed feed = new Feed(title, shortDescription, description, url, publicationDate, lastBuildTime, feedAsXML, domainName);
                feedList.add(feed);
            }
        }

        return feedList;
    }

    private String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    private String getDomainName(String url) {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public ArrayList<Feed> getItems() {
        return items;
    }
}
