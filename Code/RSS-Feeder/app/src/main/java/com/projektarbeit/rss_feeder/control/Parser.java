package com.projektarbeit.rss_feeder.control;

import com.projektarbeit.rss_feeder.util.DateUtility;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class Parser {

    private String feedXML;
    private ArrayList<Feed> items;

    public Parser(String feedXML) {
        this.feedXML = feedXML;
        try {
            items = extractItems(feedXML);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Feed> extractItems(String feedXML) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(feedXML);
        doc.getDocumentElement().normalize();

        NodeList itemList = doc.getElementsByTagName("item");
        NodeList domain  = doc.getElementsByTagName("link");
        String domainString = domain.item(0).getNodeValue();

        ArrayList<Feed> itemsArrayList = new ArrayList<Feed>();
        for (int i = 0; i < itemList.getLength(); i++) {
            Node node = itemList.item(i);
            Feed feed = generateFeedFromNode(node, domainString);
            itemsArrayList.add(feed);

        }


        return itemsArrayList;
    }

    private Feed generateFeedFromNode(Node node, String domainString) {
        NodeList childs = node.getChildNodes();
        String feedTitle = null;
        String feedShortDescription = null;
        String feedDescription = null;
        String feedUrl = null;
        Date feedPublicationDate  = null;
        Date feedLastBuildDate = null;
        String feedAsXML = node.toString();

        for (int i = 0; i < childs.getLength(); i++) {
            switch (childs.item(i).getNodeName()) {
                case "title":
                    feedTitle = childs.item(i).getNodeValue();
                    break;
                case "shortDescription":
                    feedShortDescription = childs.item(i).getNodeValue();
                    break;
                case "description":
                    feedDescription = childs.item(i).getNodeValue();
                    break;
                case "link":
                    feedUrl = childs.item(i).getNodeValue();
                    break;
                case "pubDate":
                    feedPublicationDate = DateUtility.convertStringToTime(childs.item(i).getNodeValue());
                    break;
                case "lastBuildDate":
                    feedLastBuildDate = DateUtility.convertStringToTime(childs.item(i).getNodeValue());
                    break;
            }
        }
        return new Feed(feedTitle,feedShortDescription, feedDescription, feedUrl, feedPublicationDate, feedLastBuildDate, feedAsXML, domainString );
    }


    public ArrayList<Feed> getItems() {
        return items;
    }
}
